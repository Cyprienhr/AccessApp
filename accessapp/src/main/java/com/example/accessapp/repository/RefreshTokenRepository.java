package com.example.accessapp.repository;

import com.example.accessapp.entity.RefreshToken;
import com.example.accessapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    /**
     * Find a refresh token by its token value
     * @param token the token value to search for
     * @return an Optional containing the refresh token if found
     */
    Optional<RefreshToken> findByToken(String token);
    
    /**
     * Delete all refresh tokens for a specific user
     * @param user the user whose tokens should be deleted
     * @return the number of tokens deleted
     */
    @Modifying
    int deleteByUser(User user);
    
    /**
     * Find all refresh tokens for a specific user
     * @param user the user whose tokens should be found
     * @return an Optional containing the refresh token if found
     */
    Optional<RefreshToken> findByUser(User user);
}