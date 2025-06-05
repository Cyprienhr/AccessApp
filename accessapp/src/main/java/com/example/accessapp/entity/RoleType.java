package com.example.accessapp.entity;

/**
 * Class containing constants for default role types.
 * This is not an enum to allow for dynamic role creation beyond these defaults.
 */
public class RoleType {
    
    // Default role for the super admin (platform owner)
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    
    // Common default roles that might be useful across different applications
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    
    // Private constructor to prevent instantiation
    private RoleType() {
        throw new IllegalStateException("Utility class");
    }
}