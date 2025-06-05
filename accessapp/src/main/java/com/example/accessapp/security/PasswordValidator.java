package com.example.accessapp.security;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service to validate password strength according to configurable policies.
 * Enforces password complexity requirements to enhance security.
 */
@Component
public class PasswordValidator {

    // Password policy configuration
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 128;
    private static final boolean REQUIRE_UPPERCASE = true;
    private static final boolean REQUIRE_LOWERCASE = true;
    private static final boolean REQUIRE_DIGIT = true;
    private static final boolean REQUIRE_SPECIAL_CHAR = true;
    private static final boolean DISALLOW_COMMON_PASSWORDS = true;
    
    // Regex patterns for validation
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*()_+\\[\\]{};':\"\\|,.<>/?`~-]");
    
    // Common passwords to disallow (abbreviated list - would be much larger in production)
    private static final List<String> COMMON_PASSWORDS = List.of(
            "password", "123456", "qwerty", "admin", "welcome",
            "password123", "abc123", "letmein", "monkey", "1234567890");
    
    /**
     * Validates a password against the configured password policy
     * 
     * @param password The password to validate
     * @return A list of validation errors, empty if password is valid
     */
    public List<String> validatePassword(String password) {
        List<String> validationErrors = new ArrayList<>();
        
        // Check password length
        if (password == null || password.length() < MIN_LENGTH) {
            validationErrors.add("Password must be at least " + MIN_LENGTH + " characters long");
        }
        
        if (password != null && password.length() > MAX_LENGTH) {
            validationErrors.add("Password must be less than " + MAX_LENGTH + " characters long");
        }
        
        // Skip other checks if password is null
        if (password == null) {
            return validationErrors;
        }
        
        // Check for uppercase letters
        if (REQUIRE_UPPERCASE && !UPPERCASE_PATTERN.matcher(password).find()) {
            validationErrors.add("Password must contain at least one uppercase letter");
        }
        
        // Check for lowercase letters
        if (REQUIRE_LOWERCASE && !LOWERCASE_PATTERN.matcher(password).find()) {
            validationErrors.add("Password must contain at least one lowercase letter");
        }
        
        // Check for digits
        if (REQUIRE_DIGIT && !DIGIT_PATTERN.matcher(password).find()) {
            validationErrors.add("Password must contain at least one digit");
        }
        
        // Check for special characters
        if (REQUIRE_SPECIAL_CHAR && !SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            validationErrors.add("Password must contain at least one special character");
        }
        
        // Check against common passwords
        if (DISALLOW_COMMON_PASSWORDS && COMMON_PASSWORDS.contains(password.toLowerCase())) {
            validationErrors.add("Password is too common and easily guessable");
        }
        
        return validationErrors;
    }
    
    /**
     * Convenience method to check if a password is valid
     * 
     * @param password The password to validate
     * @return true if the password is valid, false otherwise
     */
    public boolean isValid(String password) {
        return validatePassword(password).isEmpty();
    }
    
    /**
     * Get a description of the password policy for user guidance
     * 
     * @return A string describing the password policy
     */
    public String getPasswordPolicyDescription() {
        StringBuilder policy = new StringBuilder("Password must:");
        policy.append("\n- Be between ").append(MIN_LENGTH).append(" and ").append(MAX_LENGTH).append(" characters long");
        
        if (REQUIRE_UPPERCASE) {
            policy.append("\n- Contain at least one uppercase letter");
        }
        
        if (REQUIRE_LOWERCASE) {
            policy.append("\n- Contain at least one lowercase letter");
        }
        
        if (REQUIRE_DIGIT) {
            policy.append("\n- Contain at least one digit");
        }
        
        if (REQUIRE_SPECIAL_CHAR) {
            policy.append("\n- Contain at least one special character");
        }
        
        if (DISALLOW_COMMON_PASSWORDS) {
            policy.append("\n- Not be a commonly used password");
        }
        
        return policy.toString();
    }
}