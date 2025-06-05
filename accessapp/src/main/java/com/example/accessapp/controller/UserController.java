package com.example.accessapp.controller;

import com.example.accessapp.dto.BaseResponse;
import com.example.accessapp.dto.UserDto;
import com.example.accessapp.entity.User;
import com.example.accessapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for user management endpoints.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get all users
     *
     * @return list of all users
     */
    @Operation(
        summary = "Get all users",
        description = "Retrieve a list of all users. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of users returned successfully")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        // For simplicity, not implementing pagination
        List<UserDto> users = userService.findAll().stream()
                .map(userService::convertToDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(users);
    }

    /**
     * Get a user by ID
     *
     * @param id the user ID
     * @return the user if found
     */
    @Operation(
        summary = "Get user by ID",
        description = "Retrieve a user by their ID. Requires ADMIN, SUPER_ADMIN, or the user themselves."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found and returned"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or @userSecurity.isCurrentUser(#id)")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(userService::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update a user's enabled status
     *
     * @param id the user ID
     * @param enabled the enabled status
     * @return the updated user
     */
    @Operation(
        summary = "Update user enabled status",
        description = "Enable or disable a user account. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Update failed")
    })
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestParam boolean enabled) {
        try {
            User user = userService.updateEnabledStatus(id, enabled);
            return ResponseEntity.ok(userService.convertToDto(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Delete a user
     *
     * @param id the user ID
     * @return response indicating success or failure
     */
    @Operation(
        summary = "Delete user",
        description = "Delete a user by their ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Delete failed")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok(BaseResponse.success("User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Get users for a specific client
     *
     * @param clientId the client ID
     * @return list of users for the client
     */
    @Operation(
        summary = "Get users by client ID",
        description = "Retrieve all users for a specific client. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of users for the client returned successfully")
    })
    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getUsersByClientId(@PathVariable Long clientId) {
        List<UserDto> users = userService.findAllByClientId(clientId).stream()
                .map(userService::convertToDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(users);
    }

    /**
     * Update user details
     *
     * @param id the user ID
     * @param userDto the user data to update
     * @return the updated user
     */
    @Operation(
        summary = "Update user details",
        description = "Update user details by ID. Requires ADMIN, SUPER_ADMIN, or the user themselves."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Update failed")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or @userSecurity.isCurrentUser(#id)")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            User updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(userService.convertToDto(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
}