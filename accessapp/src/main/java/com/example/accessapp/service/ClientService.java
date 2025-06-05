package com.example.accessapp.service;

import com.example.accessapp.entity.Client;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for client operations.
 */
public interface ClientService {

    /**
     * Find a client by ID
     *
     * @param id the client ID
     * @return Optional containing the client if found
     */
    Optional<Client> findById(Long id);

    /**
     * Find a client by name
     *
     * @param name the client name
     * @return Optional containing the client if found
     */
    Optional<Client> findByName(String name);

    /**
     * Find a client by API key
     *
     * @param apiKey the API key
     * @return Optional containing the client if found
     */
    Optional<Client> findByApiKey(String apiKey);

    /**
     * Check if a client exists by name
     *
     * @param name the client name
     * @return true if the client exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Save a client
     *
     * @param client the client to save
     * @return the saved client
     */
    Client save(Client client);

    /**
     * Get all clients
     *
     * @return list of all clients
     */
    List<Client> findAll();

    /**
     * Delete a client by ID
     *
     * @param id the client ID
     */
    void deleteById(Long id);

    /**
     * Enable or disable a client
     *
     * @param id the client ID
     * @param enabled the enabled status
     * @return the updated client
     */
    Client updateEnabledStatus(Long id, boolean enabled);

    /**
     * Generate a new API key for a client
     *
     * @param id the client ID
     * @return the updated client with new API key
     */
    Client generateNewApiKey(Long id);
}