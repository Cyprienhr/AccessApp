package com.example.accessapp.dto.response;

import java.util.List;

/**
 * DTO for JWT authentication response
 */
public class JwtResponse extends BaseResponse {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private boolean superAdmin;

    public JwtResponse(String token, String refreshToken, Long id, String username, String email, List<String> roles, boolean superAdmin) {
        super("success", "Authentication successful");
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.superAdmin = superAdmin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public boolean isSuperAdmin() {
        return superAdmin;
    }
    
    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }
}