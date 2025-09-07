package org.sampong.springLearning.users.service;

import org.sampong.springLearning.share.base.BaseService;
import org.sampong.springLearning.users.model.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailService extends BaseService<UserDetail> {
}