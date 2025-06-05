package com.example.accessapp.repository;

import com.example.accessapp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    /**
     * Find a client by its client ID
     * @param clientId the client ID to search for
     * @return an Optional containing the client if found
     */
    Optional<Client> findByClientId(String clientId);
    
    /**
     * Check if a client with the given client ID exists
     * @param clientId the client ID to check
     * @return true if the client exists, false otherwise
     */
    Boolean existsByClientId(String clientId);
    
    /**
     * Find a client by its name
     * @param name the client name to search for
     * @return an Optional containing the client if found
     */
    Optional<Client> findByName(String name);
    
    /**
     * Check if a client with the given name exists
     * @param name the client name to check
     * @return true if the client exists, false otherwise
     */
    Boolean existsByName(String name);
    
    /**
     * Find a client by API key
     *
     * @param apiKey the API key
     * @return Optional containing the client if found
     */
    Optional<Client> findByApiKey(String apiKey);
}