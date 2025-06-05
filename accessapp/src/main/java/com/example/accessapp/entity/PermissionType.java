package com.example.accessapp.entity;

/**
 * Class containing constants for default permission types.
 * This is not an enum to allow for dynamic permission creation beyond these defaults.
 */
public class PermissionType {
    
    // User management permissions
    public static final String CREATE_USER = "CREATE_USER";
    public static final String READ_USER = "READ_USER";
    public static final String UPDATE_USER = "UPDATE_USER";
    public static final String DELETE_USER = "DELETE_USER";
    
    // Role management permissions
    public static final String CREATE_ROLE = "CREATE_ROLE";
    public static final String READ_ROLE = "READ_ROLE";
    public static final String UPDATE_ROLE = "UPDATE_ROLE";
    public static final String DELETE_ROLE = "DELETE_ROLE";
    
    // Permission management permissions
    public static final String CREATE_PERMISSION = "CREATE_PERMISSION";
    public static final String READ_PERMISSION = "READ_PERMISSION";
    public static final String UPDATE_PERMISSION = "UPDATE_PERMISSION";
    public static final String DELETE_PERMISSION = "DELETE_PERMISSION";
    
    // Client management permissions
    public static final String CREATE_CLIENT = "CREATE_CLIENT";
    public static final String READ_CLIENT = "READ_CLIENT";
    public static final String UPDATE_CLIENT = "UPDATE_CLIENT";
    public static final String DELETE_CLIENT = "DELETE_CLIENT";
    
    // Private constructor to prevent instantiation
    private PermissionType() {
        throw new IllegalStateException("Utility class");
    }
}