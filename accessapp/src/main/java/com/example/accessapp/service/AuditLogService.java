package com.example.accessapp.service;

import com.example.accessapp.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for audit log operations.
 */
public interface AuditLogService {

    /**
     * Create a new audit log entry with all columns
     *
     * @param action the action performed
     * @param details the details of the action
     * @param userId the ID of the user who performed the action
     * @param username the username of the user who performed the action
     * @param ipAddress the IP address of the client
     * @param userAgent the user agent of the client
     * @param clientId the client ID for multi-tenancy support
     * @param entityType the type of entity affected
     * @param entityId the ID of the entity affected
     * @return the created audit log entry
     */
    AuditLog createAuditLog(String action, String details, Long userId, String username, String ipAddress, String userAgent, Long clientId, String entityType, Long entityId);

    /**
     * @deprecated Use the overload with all columns
     */
    @Deprecated
    AuditLog createAuditLog(String action, String details, Long userId, String username, String clientIp, String userAgent, Long clientId);

    /**
     * @deprecated Use the overload with all columns
     */
    @Deprecated
    AuditLog createAuditLog(String action, String details, Long userId, String username, String clientIp, String userAgent, Long clientId, String entityType);

    /**
     * Find audit logs by user ID
     *
     * @param userId the user ID
     * @return list of audit logs for the user
     */
    List<AuditLog> findByUserId(Long userId);

    /**
     * Find audit logs by action type
     *
     * @param action the action type
     * @return list of audit logs for the action
     */
    List<AuditLog> findByAction(String action);

    /**
     * Find audit logs by client ID
     *
     * @param clientId the client ID
     * @return list of audit logs for the client
     */
    List<AuditLog> findByClientId(Long clientId);

    /**
     * Find audit logs by time range
     *
     * @param startTime the start time
     * @param endTime the end time
     * @return list of audit logs in the time range
     */
    List<AuditLog> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Find audit logs by user ID and time range
     *
     * @param userId the user ID
     * @param startTime the start time
     * @param endTime the end time
     * @return list of audit logs for the user in the time range
     */
    List<AuditLog> findByUserIdAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Find audit logs by client ID and time range
     *
     * @param clientId the client ID
     * @param startTime the start time
     * @param endTime the end time
     * @return list of audit logs for the client in the time range
     */
    List<AuditLog> findByClientIdAndTimeRange(Long clientId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find audit logs by user ID with pagination
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of audit logs for the user
     */
    Page<AuditLog> findByUserId(Long userId, Pageable pageable);

    /**
     * Find audit logs by action type with pagination
     *
     * @param action the action type
     * @param pageable pagination information
     * @return page of audit logs for the action
     */
    Page<AuditLog> findByAction(String action, Pageable pageable);

    /**
     * Find audit logs by client ID with pagination
     *
     * @param clientId the client ID
     * @param pageable pagination information
     * @return page of audit logs for the client
     */
    Page<AuditLog> findByClientId(Long clientId, Pageable pageable);

    /**
     * Find audit logs by time range with pagination
     *
     * @param startTime the start time
     * @param endTime the end time
     * @param pageable pagination information
     * @return page of audit logs in the time range
     */
    Page<AuditLog> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    /**
     * Find audit logs by user ID and action with pagination
     *
     * @param userId the user ID
     * @param action the action
     * @param pageable pagination information
     * @return page of audit logs for the user and action
     */
    Page<AuditLog> findByUserIdAndAction(Long userId, String action, Pageable pageable);

    /**
     * Find audit logs by user ID and client ID with pagination
     *
     * @param userId the user ID
     * @param clientId the client ID
     * @param pageable pagination information
     * @return page of audit logs for the user and client
     */
    Page<AuditLog> findByUserIdAndClientId(Long userId, Long clientId, Pageable pageable);

    /**
     * Find audit logs by action and client ID with pagination
     *
     * @param action the action
     * @param clientId the client ID
     * @param pageable pagination information
     * @return page of audit logs for the action and client
     */
    Page<AuditLog> findByActionAndClientId(String action, Long clientId, Pageable pageable);

    /**
     * Find audit logs by user ID, action, and client ID with pagination
     *
     * @param userId the user ID
     * @param action the action
     * @param clientId the client ID
     * @param pageable pagination information
     * @return page of audit logs for the user, action, and client
     */
    Page<AuditLog> findByUserIdAndActionAndClientId(Long userId, String action, Long clientId, Pageable pageable);
}