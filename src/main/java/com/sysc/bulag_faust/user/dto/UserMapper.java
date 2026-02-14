package com.sysc.bulag_faust.user.dto;

import org.mapstruct.Mapper;

import com.sysc.bulag_faust.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponse toResponse(User user);

}
