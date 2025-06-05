package com.example.accessapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Embeddable composite key for RolePermission entity.
 */
@Embeddable
public class RolePermissionKey implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "role_id")
    private Long roleId;
    
    @Column(name = "permission_id")
    private Long permissionId;
    
    // Default constructor
    public RolePermissionKey() {
    }
    
    public RolePermissionKey(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
    
    // Getters and setters
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public Long getPermissionId() {
        return permissionId;
    }
    
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
    
    // Equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionKey that = (RolePermissionKey) o;
        return Objects.equals(roleId, that.roleId) && 
               Objects.equals(permissionId, that.permissionId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }
}