package com.example.accessapp.service;

import com.example.accessapp.entity.Permission;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for permission operations.
 */
public interface PermissionService {

    /**
     * Find a permission by ID
     *
     * @param id the permission ID
     * @return Optional containing the permission if found
     */
    Optional<Permission> findById(Long id);

    /**
     * Find a permission by name
     *
     * @param name the permission name
     * @return Optional containing the permission if found
     */
    Optional<Permission> findByName(String name);

    /**
     * Check if a permission exists by name
     *
     * @param name the permission name
     * @return true if the permission exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Save a permission
     *
     * @param permission the permission to save
     * @return the saved permission
     */
    Permission save(Permission permission);

    /**
     * Get all permissions
     *
     * @return list of all permissions
     */
    List<Permission> findAll();

    /**
     * Get all permissions for a specific client
     *
     * @param clientId the client ID
     * @return list of permissions for the client
     */
    List<Permission> findAllByClientId(Long clientId);

    /**
     * Delete a permission by ID
     *
     * @param id the permission ID
     */
    void deleteById(Long id);
}