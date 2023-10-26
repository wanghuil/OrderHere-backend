package com.backend.OrderHere.auth;

import com.backend.OrderHere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    @Override
    public UserDetails loadUserByUsername(String userEmail){
        return applicationUserDao.fetchUserbyUserEmail(userEmail)
                .orElseThrow(
                        ()->new ResourceNotFoundException("User detail not exist")
                );
    }
}