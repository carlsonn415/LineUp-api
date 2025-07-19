package com.example.lineup_api.mappers;

import com.example.lineup_api.dtos.*;
import com.example.lineup_api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void changeUsername(ChangeUsernameRequest request, @MappingTarget User user);
    void changeEmail(ChangeEmailRequest request, @MappingTarget User user);
    @Mapping(source = "newPassword", target = "password")
    void changePassword(ChangePasswordRequest request, @MappingTarget User user);
}
