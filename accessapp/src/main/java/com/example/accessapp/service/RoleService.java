package com.example.accessapp.service;

import com.example.accessapp.entity.Role;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for role operations.
 */
public interface RoleService {

    /**
     * Find a role by ID
     *
     * @param id the role ID
     * @return Optional containing the role if found
     */
    Optional<Role> findById(Long id);

    /**
     * Find a role by name
     *
     * @param name the role name
     * @return Optional containing the role if found
     */
    Optional<Role> findByName(String name);

    /**
     * Check if a role exists by name
     *
     * @param name the role name
     * @return true if the role exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Save a role
     *
     * @param role the role to save
     * @return the saved role
     */
    Role save(Role role);

    /**
     * Get all roles
     *
     * @return list of all roles
     */
    List<Role> findAll();

    /**
     * Get all roles for a specific client
     *
     * @param clientId the client ID
     * @return list of roles for the client
     */
    List<Role> findAllByClientId(Long clientId);

    /**
     * Delete a role by ID
     *
     * @param id the role ID
     */
    void deleteById(Long id);

    /**
     * Add a permission to a role
     *
     * @param roleId the role ID
     * @param permissionId the permission ID
     * @return the updated role
     */
    Role addPermissionToRole(Long roleId, Long permissionId);

    /**
     * Remove a permission from a role
     *
     * @param roleId the role ID
     * @param permissionId the permission ID
     * @return the updated role
     */
    Role removePermissionFromRole(Long roleId, Long permissionId);
}