package com.example.accessapp.repository;

import com.example.accessapp.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    /**
     * Find a permission by its name
     * @param name the permission name to search for
     * @return an Optional containing the permission if found
     */
    Optional<Permission> findByName(String name);
    
    /**
     * Check if a permission with the given name exists
     * @param name the permission name to check
     * @return true if the permission exists, false otherwise
     */
    Boolean existsByName(String name);

    /**
     * Find all permissions by client ID
     * @param clientId the client ID
     * @return a list of permissions
     */
    List<Permission> findAllByClientId(Long clientId);
}