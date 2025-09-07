package org.sampong.springLearning.users.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.sampong.springLearning.share.enumerate.RoleStatus;
import org.sampong.springLearning.share.exception.CustomException;
import org.sampong.springLearning.share.utils.JwtUtil;
import org.sampong.springLearning.users.controller.dto.request.CreateUserRequest;
import org.sampong.springLearning.users.controller.dto.request.JwtRequest;
import org.sampong.springLearning.users.controller.dto.response.JwtResponse;
import org.sampong.springLearning.users.model.UserDetail;
import org.sampong.springLearning.users.model.Users;
import org.sampong.springLearning.users.repository.UserDetailRepository;
import org.sampong.springLearning.users.repository.UserRepository;
import org.sampong.springLearning.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository repository;
    private final UserDetailRepository userDetailRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Users createUser(CreateUserRequest user) {
        var users = new Users();
        users.setUsername(user.getFirstName()+"_"+user.getLastName());
        users.setPassword(passwordEncoder.encode(user.getPassword()));
        users.setEmail(user.getEmail());
        users.setRoleStatus(user.getRoles());
        users.setEnabled(true);
        users.setAccountNonExpired(true);
        users.setAccountNonLocked(true);
        users.setCredentialsNonExpired(true);
        var existUsername = repository.existsByUsernameAndStatusTrue(users.getUsername());
        var existEmail = repository.existsByEmailAndStatusTrue(users.getEmail());
        if (existUsername || existEmail) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Username or email already exists!");
        }
        var result = repository.save(users);
        var detail = new UserDetail();
        detail.setUser(result);
        detail.setFamilyName(user.getFirstName());
        detail.setGivenName(user.getLastName());
        detail.setPhoneNumber(user.getPhoneNumber());
        var saveDetail = userDetailRepository.save(detail);

        result.setUserDetail(saveDetail);
        return result;
    }

    @Override
    public JwtResponse login(JwtRequest req) throws JsonProcessingException {
        String usernameOrEmail = req.getUsername();
//        String password = req.getPassword();

        // Try to find user by username or email
        var userOpt = Optional.ofNullable(repository.findByUsername(usernameOrEmail))
                .or(() -> Optional.ofNullable(repository.findByEmail(usernameOrEmail)));

        var user = userOpt.orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "User not found: " + usernameOrEmail));

        // ✅ Generate JWT
        return jwtUtil.generateToken(user);
    }

    @Override
    public JwtResponse register(CreateUserRequest req) throws JsonProcessingException {
        var createdUser = createUser(req);
        return jwtUtil.generateToken(createdUser);
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<RoleStatus> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }
}
