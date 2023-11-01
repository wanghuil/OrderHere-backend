package com.backend.orderhere.auth;

import java.util.Optional;

public interface ApplicationUserDao {
  Optional<ApplicationUserDetails> fetchUserbyUserEmail(String email);
}