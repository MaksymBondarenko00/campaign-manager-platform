package com.cpm.authservice.user;


import com.cpm.authservice.system.auth.dto.RegisterRequest;
import com.cpm.authservice.user.dto.UserDto;
import com.cpm.authservice.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "enabled", expression = "java(false)")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User toEntity(RegisterRequest request);

    UserResponse toUserResponse(User user);
}

