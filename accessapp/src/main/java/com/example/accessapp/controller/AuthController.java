package com.example.accessapp.controller;

import com.example.accessapp.dto.*;
import com.example.accessapp.exception.TokenRefreshException;
import com.example.accessapp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller for authentication endpoints.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Authenticate a user and generate JWT tokens
     *
     * @param loginRequest the login request containing username and password
     * @param request the HTTP request for client information
     * @return JWT response containing tokens and user information
     */
    @Operation(
        summary = "Authenticate user (login)",
        description = "Authenticate user with username/email and password. Returns JWT token on success. Use the returned token to authorize further requests."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
        @ApiResponse(responseCode = "400", description = "Authentication failed or account locked")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        // For simplicity, using a default client ID of 1L
        // In a real application, this would be determined from the request or a multi-tenant context
        Long clientId = 1L;
        
        try {
            JwtResponse jwtResponse = authService.authenticateUser(loginRequest, request, clientId);
            return ResponseEntity.ok(jwtResponse);
        } catch (LockedException e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error("Authentication failed: " + e.getMessage()));
        }
    }

    /**
     * Register a new user
     *
     * @param registerRequest the registration request
     * @param request the HTTP request for client information
     * @return response indicating success or failure
     */
    @Operation(
        summary = "Register a new user",
        description = "Register a new user account. Provide username, email, and password."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Registration failed")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        // For simplicity, using a default client ID of 1L
        Long clientId = 1L;
        
        try {
            authService.registerUser(registerRequest, request, clientId);
            return ResponseEntity.ok(BaseResponse.success("User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Refresh an access token using a refresh token
     *
     * @param request the token refresh request containing the refresh token
     * @return response containing new access token and refresh token
     */
    @Operation(
        summary = "Refresh JWT access token",
        description = "Obtain a new access token using a valid refresh token."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid or expired refresh token")
    })
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        // For simplicity, using a default client ID of 1L
        Long clientId = 1L;
        
        try {
            TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request.getRefreshToken(), clientId);
            return ResponseEntity.ok(tokenRefreshResponse);
        } catch (TokenRefreshException e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Logout a user by invalidating their refresh token
     *
     * @param request the HTTP request for client information
     * @return response indicating success or failure
     */
    @Operation(
        summary = "Logout user",
        description = "Logout the current user by invalidating their refresh token. Requires authentication."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Logged out successfully"),
        @ApiResponse(responseCode = "400", description = "Logout failed or user not found")
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        // For simplicity, using a default client ID of 1L and user ID from request attribute
        Long clientId = 1L;
        Long userId = (Long) request.getAttribute("userId");
        
        if (userId == null) {
            return ResponseEntity.badRequest().body(BaseResponse.error("User ID not found in request"));
        }
        
        try {
            authService.logoutUser(userId, request, clientId);
            return ResponseEntity.ok(BaseResponse.success("Logged out successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
}