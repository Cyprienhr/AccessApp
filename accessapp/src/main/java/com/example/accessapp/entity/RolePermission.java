package com.example.accessapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Junction entity representing the many-to-many relationship between roles and permissions.
 */
@Entity
@Table(name = "role_permissions")
public class RolePermission {
    
    @EmbeddedId
    private RolePermissionKey id;
    
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;
    
    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private Permission permission;
    
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
    
    @Column(name = "assigned_by")
    private Long assignedBy; // User ID who assigned this permission
    
    @Column(name = "client_id")
    private Long clientId; // For multi-tenant support
    
    // Constructors
    public RolePermission() {
        this.assignedAt = LocalDateTime.now();
    }
    
    public RolePermission(Role role, Permission permission) {
        this.id = new RolePermissionKey(role.getId(), permission.getId());
        this.role = role;
        this.permission = permission;
        this.assignedAt = LocalDateTime.now();
    }
    
    public RolePermission(Role role, Permission permission, Long assignedBy) {
        this.id = new RolePermissionKey(role.getId(), permission.getId());
        this.role = role;
        this.permission = permission;
        this.assignedBy = assignedBy;
        this.assignedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public Permission getPermission() {
        return permission;
    }
    
    public void setPermission(Permission permission) {
        this.permission = permission;
    }
    
    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
    
    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
    
    public Long getAssignedBy() {
        return assignedBy;
    }
    
    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
    }
    
    public Long getClientId() {
        return clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}