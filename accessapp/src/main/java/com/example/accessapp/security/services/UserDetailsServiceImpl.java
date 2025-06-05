package com.example.accessapp.security.services;

import com.example.accessapp.entity.User;
import com.example.accessapp.repository.UserRepository;
import com.example.accessapp.security.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to load user details for authentication.
 * Integrates with LoginAttemptService to enforce account lockout policy.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private LoginAttemptService loginAttemptService;

    /**
     * Load a user by username for authentication
     * Checks if the account is locked before returning user details
     *
     * @param username the username to search for
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     * @throws LockedException if the account is locked due to too many failed login attempts
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the account is locked
        if (loginAttemptService.isLocked(username)) {
            long minutesRemaining = loginAttemptService.getLockoutMinutesRemaining(username);
            throw new LockedException("Account is locked due to too many failed attempts. Try again in " 
                    + minutesRemaining + " minutes.");
        }
        
        // Find the user in the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Build and return user details
        return UserDetailsImpl.build(user);
    }
}