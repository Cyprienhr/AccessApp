package com.example.accessapp.repository;

import com.example.accessapp.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    /**
     * Find audit logs by user ID without pagination
     * @param userId the user ID to search for
     * @return a list of audit logs
     */
    List<AuditLog> findByUserId(Long userId);
    
    /**
     * Find audit logs by action type without pagination
     * @param action the action type to search for
     * @return a list of audit logs
     */
    List<AuditLog> findByAction(String action);
    
    /**
     * Find audit logs by client ID without pagination
     * @param clientId the client ID to search for
     * @return a list of audit logs
     */
    List<AuditLog> findByClientId(Long clientId);
    
    /**
     * Find audit logs by time range without pagination
     * @param startTime the start time
     * @param endTime the end time
     * @return a list of audit logs
     */
    List<AuditLog> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find audit logs by user ID and time range without pagination
     * @param userId the user ID to search for
     * @param startTime the start time
     * @param endTime the end time
     * @return a list of audit logs
     */
    List<AuditLog> findByUserIdAndTimestampBetween(Long userId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find audit logs by time range and client ID without pagination
     * @param startTime the start time
     * @param endTime the end time
     * @param clientId the client ID to search for
     * @return a list of audit logs
     */
    List<AuditLog> findByTimestampBetweenAndClientId(LocalDateTime startTime, LocalDateTime endTime, Long clientId);
    
    /**
     * Find audit logs by user ID
     * @param userId the user ID to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByUserId(Long userId, Pageable pageable);
    
    /**
     * Find audit logs by action type
     * @param action the action type to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByAction(String action, Pageable pageable);
    
    /**
     * Find audit logs by entity type
     * @param entityType the entity type to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByEntityType(String entityType, Pageable pageable);
    
    /**
     * Find audit logs by entity ID
     * @param entityId the entity ID to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByEntityId(Long entityId, Pageable pageable);
    
    /**
     * Find audit logs by time range
     * @param startTime the start time
     * @param endTime the end time
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByTimestampBetween(Instant startTime, Instant endTime, Pageable pageable);
    
    /**
     * Find audit logs by time range with LocalDateTime
     * @param startTime the start time
     * @param endTime the end time
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    
    /**
     * Find audit logs by user ID and action
     * @param userId the user ID to search for
     * @param action the action to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByUserIdAndAction(Long userId, String action, Pageable pageable);
    
    /**
     * Find audit logs by client ID
     * @param clientId the client ID to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByClientId(Long clientId, Pageable pageable);
    
    /**
     * Find audit logs by user ID and client ID
     * @param userId the user ID to search for
     * @param clientId the client ID to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByUserIdAndClientId(Long userId, Long clientId, Pageable pageable);
    
    /**
     * Find audit logs by action and client ID
     * @param action the action to search for
     * @param clientId the client ID to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByActionAndClientId(String action, Long clientId, Pageable pageable);
    
    /**
     * Find audit logs by user ID, action, and client ID
     * @param userId the user ID to search for
     * @param action the action to search for
     * @param clientId the client ID to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByUserIdAndActionAndClientId(Long userId, String action, Long clientId, Pageable pageable);
    
    /**
     * Find audit logs by time range and client ID
     * @param startTime the start time
     * @param endTime the end time
     * @param clientId the client ID to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByTimestampBetweenAndClientId(LocalDateTime startTime, LocalDateTime endTime, Long clientId, Pageable pageable);
    
    /**
     * Find audit logs by user ID and time range
     * @param userId the user ID to search for
     * @param startTime the start time
     * @param endTime the end time
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByUserIdAndTimestampBetween(Long userId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    
    /**
     * Find audit logs by user ID, time range, and client ID
     * @param userId the user ID to search for
     * @param startTime the start time
     * @param endTime the end time
     * @param clientId the client ID to search for
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByUserIdAndTimestampBetweenAndClientId(Long userId, LocalDateTime startTime, LocalDateTime endTime, Long clientId, Pageable pageable);
    
    /**
     * Find audit logs by user ID and action and client ID with timestamp between
     * @param userId the user ID to search for
     * @param action the action to search for
     * @param clientId the client ID to search for
     * @param startTime the start time
     * @param endTime the end time
     * @param pageable pagination information
     * @return a page of audit logs
     */
    Page<AuditLog> findByUserIdAndActionAndClientIdAndTimestampBetween(Long userId, String action, Long clientId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}