package com.example.accessapp.security;

import com.example.accessapp.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Component for custom security checks related to users.
 */
@Component("userSecurity")
public class UserSecurity {

    /**
     * Check if the current authenticated user is the user with the given ID.
     * Used for method-level security to allow users to access their own resources.
     *
     * @param userId the user ID to check against
     * @return true if the current user is the user with the given ID, false otherwise
     */
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        Object principal = authentication.getPrincipal();
        
        if (!(principal instanceof UserDetailsImpl)) {
            return false;
        }
        
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        return userDetails.getId().equals(userId);
    }
}