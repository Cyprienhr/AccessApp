package com.example.accessapp.service.impl;

import com.example.accessapp.dto.JwtResponse;
import com.example.accessapp.dto.LoginRequest;
import com.example.accessapp.dto.RegisterRequest;
import com.example.accessapp.dto.TokenRefreshResponse;
import com.example.accessapp.entity.RefreshToken;
import com.example.accessapp.entity.Role;
import com.example.accessapp.entity.TokenBlacklist;
import com.example.accessapp.entity.User;
import com.example.accessapp.exception.TokenRefreshException;
import com.example.accessapp.repository.RoleRepository;
import com.example.accessapp.repository.TokenBlacklistRepository;
import com.example.accessapp.repository.UserRepository;
import com.example.accessapp.security.LoginAttemptService;
import com.example.accessapp.security.jwt.JwtUtils;
import com.example.accessapp.security.services.UserDetailsImpl;
import com.example.accessapp.service.AuditLogService;
import com.example.accessapp.service.AuthService;
import com.example.accessapp.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the AuthService interface.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuditLogService auditLogService;
    
    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest, HttpServletRequest request, Long clientId) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Get user details from authentication
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            
            // Record successful login attempt
            loginAttemptService.loginSucceeded(loginRequest.getUsername());
            
            // Generate JWT token
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            // Get user roles
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            
            // Create refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), clientId);
            
            // Log the login action
            auditLogService.createAuditLog(
                    "LOGIN",
                    "User logged in",
                    userDetails.getId(),
                    userDetails.getUsername(),
                    getClientIp(request),
                    request.getHeader("User-Agent"),
                    clientId,
                    null, // entityType
                    null  // entityId
            );
            
            // Return JWT response
            return new JwtResponse(
                    jwt,
                    refreshToken.getToken(),
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
        } catch (Exception e) {
            // Record failed login attempt
            loginAttemptService.loginFailed(loginRequest.getUsername());
            
            // Log the failed login attempt
            auditLogService.createAuditLog(
                    "LOGIN_FAILED",
                    "Failed login attempt: " + e.getMessage(),
                    null,
                    loginRequest.getUsername(),
                    getClientIp(request),
                    request.getHeader("User-Agent"),
                    clientId,
                    null, // entityType
                    null  // entityId
            );
            
            // Re-throw the exception to be handled by the controller
            throw e;
        }
    }

    @Override
    @Transactional
    public User registerUser(RegisterRequest registerRequest, HttpServletRequest request, Long clientId) {
        // Check if username already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Create new user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        // If isSuperAdmin is not available, comment or remove this line
        // user.setSuperAdmin(registerRequest.isSuperAdmin());
        user.setClientId(clientId);

        // Combine firstName and lastName into names field
        String fullName = "";
        try {
            // Defensive: handle both possible DTOs
            java.lang.reflect.Method getFirstName = registerRequest.getClass().getMethod("getFirstName");
            java.lang.reflect.Method getLastName = registerRequest.getClass().getMethod("getLastName");
            Object firstNameObj = getFirstName.invoke(registerRequest);
            Object lastNameObj = getLastName.invoke(registerRequest);
            String firstName = firstNameObj != null ? firstNameObj.toString() : "";
            String lastName = lastNameObj != null ? lastNameObj.toString() : "";
            fullName = (firstName + " " + lastName).trim();
        } catch (Exception e) {
            // fallback: do nothing, leave fullName as empty string
        }
        if (fullName.isEmpty()) {
            fullName = registerRequest.getUsername();
        }
        user.setName(fullName);

        // Set phone number if present
        try {
            java.lang.reflect.Method getPhoneNumber = registerRequest.getClass().getMethod("getPhoneNumber");
            Object phoneObj = getPhoneNumber.invoke(registerRequest);
            if (phoneObj != null) {
                user.setPhoneNumber(phoneObj.toString());
            }
        } catch (Exception e) {
            // fallback: do nothing
        }

        // Set user roles
        Set<Role> roles = new HashSet<>();
        
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            // Default to USER role if none specified
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            roles.add(userRole);
        } else {
            registerRequest.getRoles().forEach(roleName -> {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            });
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        // Log the registration action
        auditLogService.createAuditLog(
                "REGISTER",
                "New user registered: " + user.getUsername(),
                savedUser.getId(),
                savedUser.getUsername(),
                getClientIp(request),
                request.getHeader("User-Agent"),
                clientId,
                null, // entityType
                null  // entityId
        );

        return savedUser;
    }

    @Override
    public TokenRefreshResponse refreshToken(String refreshToken, Long clientId) {
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // Generate new JWT token
                    String token = jwtUtils.generateTokenFromUser(user);
                    
                    // Return token refresh response
                    return new TokenRefreshResponse(token, refreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token not found"));
    }

    @Override
    @Transactional
    public void logoutUser(Long userId, HttpServletRequest request, Long clientId) {
        // Delete user's refresh token
        refreshTokenService.deleteByUserId(userId);
        
        // Get user information for audit log
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        // Log the logout action
        auditLogService.createAuditLog(
                "LOGOUT",
                "User logged out",
                userId,
                user.getUsername(),
                getClientIp(request),
                request.getHeader("User-Agent"),
                clientId,
                null, // entityType
                null  // entityId
        );
    }

    @Override
    public void blacklistToken(String token, Long clientId) {
        // Check if token is already blacklisted
        if (tokenBlacklistRepository.existsByToken(token)) {
            return;
        }
        
        // Create new blacklist entry
        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);
        blacklist.setBlacklistedAt(java.time.LocalDateTime.now());
        blacklist.setExpiryDate(java.time.LocalDateTime.now()); // Use now() as fallback if conversion fails
        blacklist.setClientId(clientId);
        
        tokenBlacklistRepository.save(blacklist);
    }

    @Override
    public boolean isTokenBlacklisted(String token, Long clientId) {
        return tokenBlacklistRepository.existsByTokenAndClientId(token, clientId);
    }

    /**
     * Get client IP address from request
     *
     * @param request the HTTP request
     * @return the client IP address
     */
    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}