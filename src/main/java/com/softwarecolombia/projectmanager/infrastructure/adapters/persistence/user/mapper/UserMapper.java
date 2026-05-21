package com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.user.mapper;

import com.softwarecolombia.projectmanager.domain.user.model.AppUser;
import com.softwarecolombia.projectmanager.infrastructure.adapters.persistence.user.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AppUser toDomain(UserEntity userEntity);
    UserEntity toEntity(AppUser userApp);
}
