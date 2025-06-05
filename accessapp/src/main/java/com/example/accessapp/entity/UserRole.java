package com.example.accessapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Junction entity representing the many-to-many relationship between users and roles.
 */
@Entity
@Table(name = "user_roles")
public class UserRole {
    
    @EmbeddedId
    private UserRoleKey id;
    
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;
    
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
    
    @Column(name = "assigned_by")
    private Long assignedBy; // User ID who assigned this role
    
    @Column(name = "client_id")
    private Long clientId; // For multi-tenant support
    
    // Constructors
    public UserRole() {
        this.assignedAt = LocalDateTime.now();
    }
    
    public UserRole(User user, Role role) {
        this.id = new UserRoleKey(user.getId(), role.getId());
        this.user = user;
        this.role = role;
        this.assignedAt = LocalDateTime.now();
    }
    
    public UserRole(User user, Role role, Long assignedBy) {
        this.id = new UserRoleKey(user.getId(), role.getId());
        this.user = user;
        this.role = role;
        this.assignedBy = assignedBy;
        this.assignedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
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