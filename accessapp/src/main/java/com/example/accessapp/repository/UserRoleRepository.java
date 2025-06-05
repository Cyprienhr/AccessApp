package com.example.accessapp.repository;

import com.example.accessapp.entity.UserRole;
import com.example.accessapp.entity.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {
    
    /**
     * Find all UserRole entries for a specific user
     * @param userId the user ID
     * @return a list of UserRole objects
     */
    List<UserRole> findByIdUserId(Long userId);
    
    /**
     * Find all UserRole entries for a specific role
     * @param roleId the role ID
     * @return a list of UserRole objects
     */
    List<UserRole> findByIdRoleId(Long roleId);
    
    /**
     * Delete all UserRole entries for a specific user
     * @param userId the user ID
     */
    void deleteByIdUserId(Long userId);
    
    /**
     * Delete all UserRole entries for a specific role
     * @param roleId the role ID
     */
    void deleteByIdRoleId(Long roleId);
    
    /**
     * Check if a user has a specific role
     * @param userId the user ID
     * @param roleId the role ID
     * @return true if the user has the role, false otherwise
     */
    boolean existsByIdUserIdAndIdRoleId(Long userId, Long roleId);
}