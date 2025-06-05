package com.example.accessapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Entity for tracking important system events and actions.
 * Used for auditing and security monitoring.
 */
@Entity
@Table(name = "audit_logs")
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    private String action; // e.g., LOGIN, LOGOUT, CREATE_USER, etc.
    
    @Size(max = 500)
    private String details;
    
    @Column(name = "entity_type")
    @Size(max = 100)
    private String entityType; // Type of entity affected (e.g., User, Role, Permission)
    
    @Column(name = "entity_id")
    private Long entityId; // ID of the entity affected (e.g., userId, roleId, etc.)
    
    @Column(name = "user_id")
    private Long userId; // ID of the user who performed the action
    
    @Column(name = "username")
    private String username; // Username of the user who performed the action
    
    @Column(name = "ip_address")
    private String ipAddress; // IP address from which the action was performed
    
    @Column(name = "user_agent")
    private String userAgent; // Browser/client info
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Column(name = "client_id")
    private Long clientId; // For multi-tenant support
    
    // Constructors
    public AuditLog() {
        this.timestamp = LocalDateTime.now();
    }
    
    public AuditLog(String action, String details, Long userId, String username) {
        this.action = action;
        this.details = details;
        this.userId = userId;
        this.username = username;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getEntityType() {
        return entityType;
    }
    
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    
    public Long getEntityId() {
        return entityId;
    }
    
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Long getClientId() {
        return clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}