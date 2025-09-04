package org.sampong.springLearning.users.service.implement;

import org.sampong.springLearning.users.model.UserDetail;
import org.sampong.springLearning.users.service.UserDetailService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImp implements UserDetailService {
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
