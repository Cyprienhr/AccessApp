package com.example.accessapp.repository;

import com.example.accessapp.entity.RolePermission;
import com.example.accessapp.entity.RolePermissionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionKey> {
    
    /**
     * Find all RolePermission entries for a specific role
     * @param roleId the role ID
     * @return a list of RolePermission objects
     */
    List<RolePermission> findByIdRoleId(Long roleId);
    
    /**
     * Find all RolePermission entries for a specific permission
     * @param permissionId the permission ID
     * @return a list of RolePermission objects
     */
    List<RolePermission> findByIdPermissionId(Long permissionId);
    
    /**
     * Delete all RolePermission entries for a specific role
     * @param roleId the role ID
     */
    void deleteByIdRoleId(Long roleId);
    
    /**
     * Delete all RolePermission entries for a specific permission
     * @param permissionId the permission ID
     */
    void deleteByIdPermissionId(Long permissionId);
    
    /**
     * Check if a role has a specific permission
     * @param roleId the role ID
     * @param permissionId the permission ID
     * @return true if the role has the permission, false otherwise
     */
    boolean existsByIdRoleIdAndIdPermissionId(Long roleId, Long permissionId);
}