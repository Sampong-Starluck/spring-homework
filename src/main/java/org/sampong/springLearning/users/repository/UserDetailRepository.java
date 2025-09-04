package org.sampong.springLearning.users.repository;

import org.sampong.springLearning.share.base.BaseRepository;
import org.sampong.springLearning.users.model.UserDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends BaseRepository<UserDetail> {
}