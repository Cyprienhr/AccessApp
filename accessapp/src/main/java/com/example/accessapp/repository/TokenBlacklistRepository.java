package com.example.accessapp.repository;

import com.example.accessapp.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    
    /**
     * Find a blacklisted token by its token value
     * @param token the token value to search for
     * @return an Optional containing the blacklisted token if found
     */
    Optional<TokenBlacklist> findByToken(String token);
    
    /**
     * Check if a token is blacklisted
     * @param token the token value to check
     * @return true if the token is blacklisted, false otherwise
     */
    boolean existsByToken(String token);
    
    /**
     * Delete all expired blacklisted tokens
     * @param now the current time
     * @return the number of tokens deleted
     */
    @Modifying
    @Query("DELETE FROM TokenBlacklist t WHERE t.expiryDate < ?1")
    int deleteAllExpiredTokens(Instant now);
    
    /**
     * Check if a token is blacklisted for a specific client
     *
     * @param token the token string
     * @param clientId the client ID
     * @return true if the token is blacklisted for the client, false otherwise
     */
    boolean existsByTokenAndClientId(String token, Long clientId);

    /**
     * Find all blacklisted tokens for a specific client
     *
     * @param clientId the client ID
     * @return list of blacklisted tokens for the client
     */
    List<TokenBlacklist> findByClientId(Long clientId);
}