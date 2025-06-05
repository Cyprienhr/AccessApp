package com.example.accessapp.repository;

import com.example.accessapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Find a role by its name
     * @param name the role name to search for
     * @return an Optional containing the role if found
     */
    Optional<Role> findByName(String name);
    
    /**
     * Check if a role with the given name exists
     * @param name the role name to check
     * @return true if the role exists, false otherwise
     */
    Boolean existsByName(String name);

    /**
     * Find all roles by client ID
     * @param clientId the client ID
     * @return a list of roles
     */
    List<Role> findAllByClientId(Long clientId);
}