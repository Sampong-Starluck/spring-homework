package org.sampong.springLearning.users.service.implement;

import lombok.RequiredArgsConstructor;
import org.sampong.springLearning.share.enumerate.RoleStatus;
import org.sampong.springLearning.share.exception.CustomException;
import org.sampong.springLearning.share.utils.JwtUtil;
import org.sampong.springLearning.users.model.UserDetail;
import org.sampong.springLearning.users.repository.UserDetailRepository;
import org.sampong.springLearning.users.repository.UserRepository;
import org.sampong.springLearning.users.service.UserDetailService;
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
public class UserDetailServiceImp implements UserDetailService {

    private final UserRepository repository;
    private final UserDetailRepository userDetailRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDetail> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public UserDetail addNew(UserDetail userDetail) {
        return null;
    }

    @Override
    public UserDetail updateObj(UserDetail userDetail) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
