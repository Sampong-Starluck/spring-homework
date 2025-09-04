package org.sampong.springLearning.users.repository;

import org.sampong.springLearning.share.base.BaseRepository;
import org.sampong.springLearning.users.model.UserDetail;
import org.sampong.springLearning.users.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<Users> {

    @Query(value = "select u from Users u where u.username = :username and u.status = true")
    Users findByUsername(String username);
    @Query(value = "select u from Users u where u.email = :email and u.status = true")
    Users findByEmail(String email);

    Boolean existsByUsernameAndStatusTrue(String username);
    Boolean existsByEmailAndStatusTrue(String email);
}