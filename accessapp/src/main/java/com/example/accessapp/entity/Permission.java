package com.example.accessapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entity representing a permission in the system.
 * Defines system capabilities (e.g., CREATE_USER, VIEW_REPORTS).
 */
@Entity
@Table(name = "permissions")
public class Permission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String name;
    
    @Size(max = 200)
    private String description;
    
    @Column(name = "client_id")
    private Long clientId; // For multi-tenant support
    
    @Column(name = "is_predefined")
    private boolean isPredefined = false; // Indicates if this is a system-defined permission
    
    // Constructors
    public Permission() {
    }
    
    public Permission(String name) {
        this.name = name;
    }
    
    public Permission(String name, String description) {
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