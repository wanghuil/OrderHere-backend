package com.backend.OrderHere.dto;

import lombok.Data;

@Data
public class UserProfileUpdateDTO {
  private String username;
  private String firstname;
  private String lastname;
  private String avatarUrl;
}
