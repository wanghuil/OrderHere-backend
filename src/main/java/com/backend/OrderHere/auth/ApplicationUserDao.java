package com.backend.OrderHere.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<ApplicationUserDetails> fetchUserbyUserEmail(String email);
}