package com.example.accessapp.controller;

import com.example.accessapp.dto.BaseResponse;
import com.example.accessapp.entity.Role;
import com.example.accessapp.service.RoleService;
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
 * Controller for role management endpoints.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * Get all roles
     *
     * @return list of all roles
     */
    @Operation(
        summary = "Get all roles",
        description = "Retrieve a list of all roles. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of roles returned successfully")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    /**
     * Get a role by ID
     *
     * @param id the role ID
     * @return the role if found
     */
    @Operation(
        summary = "Get role by ID",
        description = "Retrieve a role by its ID. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Role found and returned"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new role
     *
     * @param role the role to create
     * @return the created role
     */
    @Operation(
        summary = "Create a new role",
        description = "Create a new role. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Role created successfully"),
        @ApiResponse(responseCode = "400", description = "Role creation failed")
    })
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createRole(@Valid @RequestBody Role role) {
        // For simplicity, using a default client ID of 1L
        role.setClientId(1L);
        
        if (roleService.existsByName(role.getName())) {
            return ResponseEntity.badRequest().body(BaseResponse.error("Role name already exists"));
        }
        
        Role savedRole = roleService.save(role);
        return ResponseEntity.ok(savedRole);
    }

    /**
     * Update a role
     *
     * @param id the role ID
     * @param role the updated role
     * @return the updated role
     */
    @Operation(
        summary = "Update a role",
        description = "Update an existing role by ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Role updated successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @Valid @RequestBody Role role) {
        return roleService.findById(id)
                .map(existingRole -> {
                    // Update fields
                    existingRole.setName(role.getName());
                    existingRole.setDescription(role.getDescription());
                    
                    Role updatedRole = roleService.save(existingRole);
                    return ResponseEntity.ok(updatedRole);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a role
     *
     * @param id the role ID
     * @return response indicating success or failure
     */
    @Operation(
        summary = "Delete a role",
        description = "Delete a role by its ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Role deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Delete failed")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteById(id);
            return ResponseEntity.ok(BaseResponse.success("Role deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Add a permission to a role
     *
     * @param roleId the role ID
     * @param permissionId the permission ID
     * @return the updated role
     */
    @Operation(
        summary = "Add permission to role",
        description = "Add a permission to a role by their IDs. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Permission added to role successfully"),
        @ApiResponse(responseCode = "400", description = "Add failed")
    })
    @PostMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        try {
            Role updatedRole = roleService.addPermissionToRole(roleId, permissionId);
            return ResponseEntity.ok(updatedRole);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Remove a permission from a role
     *
     * @param roleId the role ID
     * @param permissionId the permission ID
     * @return the updated role
     */
    @Operation(
        summary = "Remove permission from role",
        description = "Remove a permission from a role by their IDs. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Permission removed from role successfully"),
        @ApiResponse(responseCode = "400", description = "Remove failed")
    })
    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> removePermissionFromRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        try {
            Role updatedRole = roleService.removePermissionFromRole(roleId, permissionId);
            return ResponseEntity.ok(updatedRole);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Get roles for a specific client
     *
     * @param clientId the client ID
     * @return list of roles for the client
     */
    @Operation(
        summary = "Get roles by client ID",
        description = "Retrieve all roles for a specific client. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of roles for the client returned successfully")
    })
    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Role>> getRolesByClientId(@PathVariable Long clientId) {
        List<Role> roles = roleService.findAllByClientId(clientId);
        return ResponseEntity.ok(roles);
    }
}