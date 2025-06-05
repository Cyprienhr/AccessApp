package com.example.accessapp.controller;

import com.example.accessapp.dto.BaseResponse;
import com.example.accessapp.entity.Client;
import com.example.accessapp.service.ClientService;
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
 * Controller for client management endpoints.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * Get all clients
     *
     * @return list of all clients
     */
    @Operation(
        summary = "Get all clients",
        description = "Retrieve a list of all clients. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of clients returned successfully")
    })
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    /**
     * Get a client by ID
     *
     * @param id the client ID
     * @return the client if found
     */
    @Operation(
        summary = "Get client by ID",
        description = "Retrieve a client by its ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client found and returned"),
        @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new client
     *
     * @param client the client to create
     * @return the created client
     */
    @Operation(
        summary = "Create a new client",
        description = "Create a new client. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client created successfully"),
        @ApiResponse(responseCode = "400", description = "Client creation failed")
    })
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client) {
        if (clientService.existsByName(client.getName())) {
            return ResponseEntity.badRequest().body(BaseResponse.error("Client name already exists"));
        }
        
        // API key will be generated automatically in the service
        Client savedClient = clientService.save(client);
        return ResponseEntity.ok(savedClient);
    }

    /**
     * Update a client
     *
     * @param id the client ID
     * @param client the updated client
     * @return the updated client
     */
    @Operation(
        summary = "Update a client",
        description = "Update an existing client by ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client updated successfully"),
        @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        return clientService.findById(id)
                .map(existingClient -> {
                    // Update fields
                    existingClient.setName(client.getName());
                    existingClient.setDescription(client.getDescription());
                    existingClient.setEnabled(client.isEnabled());
                    
                    Client updatedClient = clientService.save(existingClient);
                    return ResponseEntity.ok(updatedClient);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a client
     *
     * @param id the client ID
     * @return response indicating success or failure
     */
    @Operation(
        summary = "Delete a client",
        description = "Delete a client by its ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Delete failed")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteById(id);
            return ResponseEntity.ok(BaseResponse.success("Client deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Update a client's enabled status
     *
     * @param id the client ID
     * @param enabled the enabled status
     * @return the updated client
     */
    @Operation(
        summary = "Update client enabled status",
        description = "Enable or disable a client. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Update failed")
    })
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateClientStatus(@PathVariable Long id, @RequestParam boolean enabled) {
        try {
            Client client = clientService.updateEnabledStatus(id, enabled);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    /**
     * Generate a new API key for a client
     *
     * @param id the client ID
     * @return the updated client with the new API key
     */
    @Operation(
        summary = "Generate new API key for client",
        description = "Generate a new API key for a client by ID. Requires SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "New API key generated successfully"),
        @ApiResponse(responseCode = "400", description = "API key generation failed")
    })
    @PostMapping("/{id}/api-key")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> generateNewApiKey(@PathVariable Long id) {
        try {
            Client client = clientService.generateNewApiKey(id);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
}