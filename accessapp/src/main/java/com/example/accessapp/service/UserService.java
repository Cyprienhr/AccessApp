package com.example.accessapp.service;

import com.example.accessapp.dto.UserDto;
import com.example.accessapp.entity.User;

import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Service interface for user operations.
 */
public interface UserService {

    /**
     * Find a user by ID
     *
     * @param id the user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Long id);

    /**
     * Find a user by username
     *
     * @param username the username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by email
     *
     * @param email the email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a username exists
     *
     * @param username the username
     * @return true if the username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if an email exists
     *
     * @param email the email
     * @return true if the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Save a user
     *
     * @param user the user to save
     * @return the saved user
     */
    User save(User user);

    /**
     * Get all users
     *
     * @return list of all users
     */
    List<User> findAll();

    /**
     * Get all users for a specific client
     *
     * @param clientId the client ID
     * @return list of users for the client
     */
    List<User> findAllByClientId(Long clientId);

    /**
     * Convert a User entity to UserDto
     *
     * @param user the user entity
     * @return the user DTO
     */
    UserDto convertToDto(User user);

    /**
     * Delete a user by ID
     *
     * @param id the user ID
     */
    void deleteById(Long id);

    /**
     * Enable or disable a user
     *
     * @param id the user ID
     * @param enabled the enabled status
     * @return the updated user
     */
    User updateEnabledStatus(Long id, boolean enabled);

    /**
     * Update a user by ID using a UserDto
     *
     * @param id the user ID
     * @param userDto the user DTO
     * @return the updated user
     */
    User updateUser(Long id, UserDto userDto);

    /**
     * Assign a role to a user, recording who assigned it and logging the action.
     * @param userId the user to assign the role to
     * @param roleId the role to assign
     * @param assignedBy the user ID of the assigner
     * @param request the HTTP request (for IP/user agent)
     */
    void assignRoleToUser(Long userId, Long roleId, Long assignedBy, HttpServletRequest request);
}