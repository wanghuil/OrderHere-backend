package com.backend.OrderHere.service;

import com.backend.OrderHere.dto.User.UserSignUpRequestDTO;
import com.backend.OrderHere.dto.User.UserSignUpResponseDTO;
import com.backend.OrderHere.dto.UserProfileUpdateDTO;
import com.backend.OrderHere.exception.DataIntegrityException;
import com.backend.OrderHere.exception.ResourceNotFoundException;
import com.backend.OrderHere.mapper.UserMapper;
import com.backend.OrderHere.model.User;
import com.backend.OrderHere.model.enums.UserRole;
import com.backend.OrderHere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  //Declare Constant value for initialize user
  private final static int INIT_REWARD_POINT = 0;
  private final static String INIT_AVATAR_URL = "SOME_DEFAULT_URL";


  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final TokenService tokenService;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper, TokenService tokenService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.tokenService = tokenService;
  }

  public UserProfileUpdateDTO updateUserProfile(Integer userId, UserProfileUpdateDTO dto) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    try {
      userMapper.updateUserFromUserProfileUpdateDTO(dto, user);
      User updatedUser = userRepository.save(user);

      return userMapper.userToUserProfileUpdateDTO(updatedUser);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException("Username already exists");
    } catch (Exception e) {
      throw new RuntimeException("Something went wrong");
    }
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(
            ()-> new ResourceNotFoundException("User not found"));
  }

  public boolean resetPassword(String email, String code, String newPassword) {

    //retrieve user details
    User user = userRepository.findByEmail(email).orElseThrow(
            ()-> new ResourceNotFoundException("User not found"));

    //Create BCryptPassword
    String hashedPassword = encoder.encode(newPassword);

    if (tokenService.isCodeValid(code) ) {
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return true; // reset success
    }
    return false; // reset fail
  }

  public UserSignUpResponseDTO createUser(UserSignUpRequestDTO userSignUpRequestDTO) {

    //Create BCryptPassword
    String hashedPassword = encoder.encode(userSignUpRequestDTO.getPassword());

    //create user
    User user= User.builder()
            .username(userSignUpRequestDTO.getUserName())
            .firstname(userSignUpRequestDTO.getFirstName())
            .lastname(userSignUpRequestDTO.getLastName())
            .email(userSignUpRequestDTO.getEmail())
            .password(hashedPassword)
            .point(INIT_REWARD_POINT)
            .avatarUrl(INIT_AVATAR_URL)
            .userRole(UserRole.customer)
            .build();

    //create user and map user to response form
    User createdUser = userRepository.save(user);

    return userMapper.userToUserSignUpResponseDTO(createdUser);
  }
}

