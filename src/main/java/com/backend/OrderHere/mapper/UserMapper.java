package com.backend.OrderHere.mapper;

import com.backend.OrderHere.dto.User.UserSignUpResponseDTO;
import com.backend.OrderHere.dto.UserProfileUpdateDTO;
import com.backend.OrderHere.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
  void updateUserFromUserProfileUpdateDTO(UserProfileUpdateDTO dto, @MappingTarget User user);

  UserProfileUpdateDTO userToUserProfileUpdateDTO(User updatedUser);

  UserSignUpResponseDTO userToUserSignUpResponseDTO(User createdUser);

}
