package com.example.accessapp.controller;

import com.example.accessapp.dto.BaseResponse;
import com.example.accessapp.entity.Permission;
import com.example.accessapp.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

/**
 * Controller for permission management endpoints.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * Get all permissions
     *
     * @return list of all permissions
     */
    @Operation(
        summary = "Get all permissions",
        description = "Retrieve a list of all permissions. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of permissions returned successfully")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.findAll();
        return ResponseEntity.ok(permissions);
    }

    /**
     * Get a permission by ID
     *
     * @param id the permission ID
     * @return the permission if found
     */
    @Operation(
        summary = "Get permission by ID",
        description = "Retrieve a permission by its ID. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Permission found and returned"),
        @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
        return permissionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new permission
     *
     * @param permission the permission to create
     * @return the created permission
     */
    @Operation(
        summary = "Create a new permission",
        description = "Create a new permission. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Permission created successfully"),
        @ApiResponse(responseCode = "400", description = "Permission creation failed")
    })
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createPermission(@Valid @RequestBody Permission permission) {
        // For simplicity, using a default client ID of 1L
        permission.setClientId(1L);
        
        if (permissionService.existsByName(permission.getName())) {
            return ResponseEntity.badRequest().body(BaseResponse.error("Permission name already exists"));
        }
        
        Permission savedPermission = permissionService.save(permission);
        return ResponseEntity.ok(savedPermission);
    }

    /**
     * Update a permission
     *
     * @param id the permission ID
     * @param permission the updated permission
     * @return the updated permission
     */
    @Operation(
        summary = "Update a permission",
        description = "Update an existing permission by ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
        @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updatePermission(@PathVariable Long id, @Valid @RequestBody Permission permission) {
        return permissionService.findById(id)
                .map(existingPermission -> {
                    // Update fields
                    existingPermission.setName(permission.getName());
                    existingPermission.setDescription(permission.getDescription());
                    
                    Permission updatedPermission = permissionService.save(existingPermission);
                    return ResponseEntity.ok(updatedPermission);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a permission
     *
     * @param id the permission ID
     * @return response indicating success or failure
     */
    @Operation(
        summary = "Delete a permission",
        description = "Delete a permission by its ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Permission deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Delete failed")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deletePermission(@PathVariable Long id) {
        try {
            permissionService.deleteById(id);
            return ResponseEntity.ok(BaseResponse.success("Permission deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Get permissions for a specific client
     *
     * @param clientId the client ID
     * @return list of permissions for the client
     */
    @Operation(
        summary = "Get permissions by client ID",
        description = "Retrieve all permissions for a specific client. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of permissions for the client returned successfully")
    })
    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Permission>> getPermissionsByClientId(@PathVariable Long clientId) {
        List<Permission> permissions = permissionService.findAllByClientId(clientId);
        return ResponseEntity.ok(permissions);
    }
}