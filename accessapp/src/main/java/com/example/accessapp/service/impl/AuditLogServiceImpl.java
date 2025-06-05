package com.example.accessapp.service.impl;

import com.example.accessapp.entity.AuditLog;
import com.example.accessapp.repository.AuditLogRepository;
import com.example.accessapp.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the AuditLogService interface.
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public AuditLog createAuditLog(String action, String details, Long userId, String username, String ipAddress, String userAgent, Long clientId, String entityType, Long entityId) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setDetails(details);
        auditLog.setUserId(userId);
        auditLog.setUsername(username);
        auditLog.setIpAddress(ipAddress);
        auditLog.setUserAgent(userAgent);
        auditLog.setTimestamp(LocalDateTime.now());
        auditLog.setClientId(clientId);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        return auditLogRepository.save(auditLog);
    }

    @Override
    @Deprecated
    public AuditLog createAuditLog(String action, String details, Long userId, String username, String clientIp, String userAgent, Long clientId) {
        return createAuditLog(action, details, userId, username, clientIp, userAgent, clientId, null, null);
    }

    @Override
    @Deprecated
    public AuditLog createAuditLog(String action, String details, Long userId, String username, String clientIp, String userAgent, Long clientId, String entityType) {
        return createAuditLog(action, details, userId, username, clientIp, userAgent, clientId, entityType, null);
    }

    @Override
    public List<AuditLog> findByUserId(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }

    @Override
    public List<AuditLog> findByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    @Override
    public List<AuditLog> findByClientId(Long clientId) {
        return auditLogRepository.findByClientId(clientId);
    }

    @Override
    public List<AuditLog> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return auditLogRepository.findByTimestampBetween(startTime, endTime);
    }

    @Override
    public List<AuditLog> findByUserIdAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return auditLogRepository.findByUserIdAndTimestampBetween(userId, startTime, endTime);
    }

    @Override
    public List<AuditLog> findByClientIdAndTimeRange(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        return auditLogRepository.findByTimestampBetweenAndClientId(startTime, endTime, clientId);
    }
    
    @Override
    public Page<AuditLog> findByUserId(Long userId, Pageable pageable) {
        return auditLogRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<AuditLog> findByAction(String action, Pageable pageable) {
        return auditLogRepository.findByAction(action, pageable);
    }

    @Override
    public Page<AuditLog> findByClientId(Long clientId, Pageable pageable) {
        return auditLogRepository.findByClientId(clientId, pageable);
    }

    @Override
    public Page<AuditLog> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return auditLogRepository.findByTimestampBetween(startTime, endTime, pageable);
    }

    @Override
    public Page<AuditLog> findByUserIdAndAction(Long userId, String action, Pageable pageable) {
        return auditLogRepository.findByUserIdAndAction(userId, action, pageable);
    }

    @Override
    public Page<AuditLog> findByUserIdAndClientId(Long userId, Long clientId, Pageable pageable) {
        return auditLogRepository.findByUserIdAndClientId(userId, clientId, pageable);
    }

    @Override
    public Page<AuditLog> findByActionAndClientId(String action, Long clientId, Pageable pageable) {
        return auditLogRepository.findByActionAndClientId(action, clientId, pageable);
    }

    @Override
    public Page<AuditLog> findByUserIdAndActionAndClientId(Long userId, String action, Long clientId, Pageable pageable) {
        return auditLogRepository.findByUserIdAndActionAndClientId(userId, action, clientId, pageable);
    }
}