package com.example.accessapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Entity for tracking invalidated JWT tokens.
 * Used for logout and token invalidation tracking.
 */
@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(length = 500)
    private String token;
    
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
    
    @Column(name = "blacklisted_at")
    private LocalDateTime blacklistedAt;
    
    @Column(name = "client_id")
    private Long clientId; // For multi-tenant support
    
    // Constructors
    public TokenBlacklist() {
        this.blacklistedAt = LocalDateTime.now();
    }
    
    public TokenBlacklist(String token, LocalDateTime expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.blacklistedAt = LocalDateTime.now();
    }
    
    public TokenBlacklist(String token, LocalDateTime expiryDate, Long clientId) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.clientId = clientId;
        this.blacklistedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public LocalDateTime getBlacklistedAt() {
        return blacklistedAt;
    }
    
    public void setBlacklistedAt(LocalDateTime blacklistedAt) {
        this.blacklistedAt = blacklistedAt;
    }
    
    public Long getClientId() {
        return clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}