package com.example.accessapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a role in the system.
 * Defines access levels (e.g., Admin, User, Manager, Viewer).
 */
@Entity
@Table(name = "roles")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String name;
    
    @Size(max = 200)
    private String description;
    
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePermission> rolePermissions = new HashSet<>();
    
    // Convenience method to get permissions
    @Transient
    public Set<Permission> getPermissions() {
        return rolePermissions.stream()
                .map(RolePermission::getPermission)
                .collect(java.util.stream.Collectors.toSet());
    }
    
    @Column(name = "client_id")
    private Long clientId; // For multi-tenant support
    
    @Column(name = "is_predefined")
    private boolean isPredefined = false; // Indicates if this is a system-defined role
    
    // Constructors
    public Role() {
    }
    
    public Role(String name) {
        this.name = name;
    }
    
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Set<RolePermission> getRolePermissions() {
        return rolePermissions;
    }
    
    public void setRolePermissions(Set<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
    
    // Convenience method to set permissions
    public void setPermissions(Set<Permission> permissions) {
        // Clear existing permissions
        this.rolePermissions.clear();
        
        // Add new permissions
        if (permissions != null) {
            permissions.forEach(permission -> this.rolePermissions.add(new RolePermission(this, permission)));
        }
    }
    
    // Convenience method to add a permission
    public void addPermission(Permission permission) {
        RolePermission rolePermission = new RolePermission(this, permission);
        this.rolePermissions.add(rolePermission);
    }
    
    // Convenience method to remove a permission
    public void removePermission(Permission permission) {
        this.rolePermissions.removeIf(rolePermission -> rolePermission.getPermission().equals(permission));
    }
    
    public Long getClientId() {
        return clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    
    public boolean isPredefined() {
        return isPredefined;
    }
    
    public void setPredefined(boolean isPredefined) {
        this.isPredefined = isPredefined;
    }
}