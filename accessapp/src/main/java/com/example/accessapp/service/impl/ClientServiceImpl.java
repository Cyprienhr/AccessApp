package com.example.accessapp.service.impl;

import com.example.accessapp.entity.Client;
import com.example.accessapp.repository.ClientRepository;
import com.example.accessapp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the ClientService interface.
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public Optional<Client> findByApiKey(String apiKey) {
        return clientRepository.findByApiKey(apiKey);
    }

    @Override
    public boolean existsByName(String name) {
        return clientRepository.existsByName(name);
    }

    @Override
    public Client save(Client client) {
        // If it's a new client, generate an API key
        if (client.getId() == null && client.getApiKey() == null) {
            client.setApiKey(generateApiKey());
        }
        
        // Set timestamps
        if (client.getId() == null) {
            client.setCreatedAt(LocalDateTime.now());
        }
        client.setUpdatedAt(LocalDateTime.now());
        
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client updateEnabledStatus(Long id, boolean enabled) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        
        client.setEnabled(enabled);
        client.setUpdatedAt(LocalDateTime.now());
        
        return clientRepository.save(client);
    }

    @Override
    public Client generateNewApiKey(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        
        client.setApiKey(generateApiKey());
        client.setUpdatedAt(LocalDateTime.now());
        
        return clientRepository.save(client);
    }

    /**
     * Generate a unique API key
     *
     * @return the generated API key
     */
    private String generateApiKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}