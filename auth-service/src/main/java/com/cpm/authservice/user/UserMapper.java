package com.cpm.authservice.user;


import com.cpm.authservice.system.auth.dto.RegisterRequest;
import com.cpm.authservice.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "enabled", expression = "java(false)")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User toEntity(RegisterRequest request);

    @Mapping(target = "id", source = "user.id")
    UserDto toDto(User user);
}

