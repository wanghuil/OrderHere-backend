package com.backend.OrderHere.controller.v1;

import com.backend.OrderHere.dto.User.ResetPasswordDTO;
import com.backend.OrderHere.dto.User.UserForgetPasswordRequestDTO;
import com.backend.OrderHere.dto.User.UserSignUpRequestDTO;
import com.backend.OrderHere.dto.User.UserSignUpResponseDTO;
import com.backend.OrderHere.dto.UserProfileUpdateDTO;
import com.backend.OrderHere.model.User;
import com.backend.OrderHere.service.EmailService;
import com.backend.OrderHere.service.TokenService;
import com.backend.OrderHere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/public/users")
public class UserController {

  private final UserService userService;
  private final EmailService emailService;
  private final TokenService tokenService;

  @Autowired
  public UserController(UserService userService, EmailService emailService, TokenService tokenService) {
    this.userService = userService;
    this.emailService = emailService;
    this.tokenService = tokenService;
  }
  @PutMapping("/{userId}/profile")
  public ResponseEntity<UserProfileUpdateDTO> updateProfile(@PathVariable Integer userId, @RequestBody UserProfileUpdateDTO dto) {
    UserProfileUpdateDTO updatedUserProfile = userService.updateUserProfile(userId, dto);
    return new ResponseEntity<>(updatedUserProfile, HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<UserSignUpResponseDTO> userSignUp(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO){
    UserSignUpResponseDTO user = userService.createUser(userSignUpRequestDTO);
    return new ResponseEntity<UserSignUpResponseDTO>(user, HttpStatus.OK);
  }

  @PostMapping("/forget-password")
  public ResponseEntity<String> forgotPassword(@RequestBody UserForgetPasswordRequestDTO userForgetPasswordRequestDTO) {

    // check whether user email exist
    User user = userService.findByEmail(userForgetPasswordRequestDTO.getEmail());

    // generate 6-digit code
    String code = tokenService.generateCode();

    try {
      // send token to user email
      emailService.sendEmailWithCode(userForgetPasswordRequestDTO.getEmail(), code);
    } catch (MessagingException e) {
      return new ResponseEntity<>("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>("Verification code sent successfully", HttpStatus.OK);
  }

  @PostMapping("/reset")
  public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {

    boolean resetSuccessful = userService.resetPassword(
            resetPasswordDTO.getEmail(),
            resetPasswordDTO.getCode(),
            resetPasswordDTO.getNewPassword()
    );

    if (resetSuccessful) {
      return new ResponseEntity<>("Password reset successful.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Password reset failed.", HttpStatus.BAD_REQUEST);
    }
  }
}
