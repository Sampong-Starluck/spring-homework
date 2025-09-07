package org.sampong.springLearning.users.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.sampong.springLearning.share.configuration.MapperConfiguration;
import org.sampong.springLearning.users.controller.dto.response.UserResponse;
import org.sampong.springLearning.users.model.Users;

@Mapper(config = MapperConfiguration.class)
public interface UserRestMapper {
    @Mappings({
            @Mapping(target = "familyName", source = "userDetail.familyName"),
            @Mapping(target = "givenName", source = "userDetail.givenName"),
            @Mapping(target = "phoneNumber", source = "phone")
    })
    UserResponse from(Users user);
}
