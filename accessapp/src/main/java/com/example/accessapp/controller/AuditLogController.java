package com.example.accessapp.controller;

import com.example.accessapp.entity.AuditLog;
import com.example.accessapp.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDateTime;

/**
 * Controller for audit log management endpoints.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    /**
     * Get all audit logs with pagination
     *
     * @param pageable pagination information
     * @return paginated list of audit logs
     */
    @Operation(
        summary = "Get all audit logs (paginated)",
        description = "Retrieve all audit logs with pagination. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paginated list of audit logs returned successfully")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<AuditLog>> getAllAuditLogs(Pageable pageable) {
        // For simplicity, using a default client ID of 1L
        Long clientId = 1L;
        Page<AuditLog> auditLogs = auditLogRepository.findByClientId(clientId, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    /**
     * Get audit logs by user ID with pagination
     *
     * @param userId the user ID
     * @param pageable pagination information
     * @return paginated list of audit logs for the user
     */
    @Operation(
        summary = "Get audit logs by user ID (paginated)",
        description = "Retrieve audit logs for a specific user with pagination. Requires ADMIN, SUPER_ADMIN, or the user themselves."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paginated list of audit logs for the user returned successfully")
    })
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or @userSecurity.isCurrentUser(#userId)")
    public ResponseEntity<Page<AuditLog>> getAuditLogsByUserId(
            @PathVariable Long userId,
            Pageable pageable) {
        // For simplicity, using a default client ID of 1L
        Long clientId = 1L;
        Page<AuditLog> auditLogs = auditLogRepository.findByUserIdAndClientId(userId, clientId, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    /**
     * Get audit logs by action type with pagination
     *
     * @param action the action type
     * @param pageable pagination information
     * @return paginated list of audit logs for the action type
     */
    @Operation(
        summary = "Get audit logs by action type (paginated)",
        description = "Retrieve audit logs for a specific action type with pagination. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paginated list of audit logs for the action type returned successfully")
    })
    @GetMapping("/action/{action}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<AuditLog>> getAuditLogsByAction(
            @PathVariable String action,
            Pageable pageable) {
        // For simplicity, using a default client ID of 1L
        Long clientId = 1L;
        Page<AuditLog> auditLogs = auditLogRepository.findByActionAndClientId(action, clientId, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    /**
     * Get audit logs by time range with pagination
     *
     * @param startTime the start time
     * @param endTime the end time
     * @param pageable pagination information
     * @return paginated list of audit logs for the time range
     */
    @Operation(
        summary = "Get audit logs by time range (paginated)",
        description = "Retrieve audit logs for a specific time range with pagination. Requires ADMIN or SUPER_ADMIN role."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paginated list of audit logs for the time range returned successfully")
    })
    @GetMapping("/time-range")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<AuditLog>> getAuditLogsByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            Pageable pageable) {
        // For simplicity, using a default client ID of 1L
        Long clientId = 1L;
        Page<AuditLog> auditLogs = auditLogRepository.findByTimestampBetweenAndClientId(startTime, endTime, clientId, pageable);
        return ResponseEntity.ok(auditLogs);
    }

    /**
     * Get audit logs by user ID and action type with pagination
     *
     * @param userId the user ID
     * @param action the action type
     * @param pageable pagination information
     * @return paginated list of audit logs for the user and action type
     */
    @Operation(
        summary = "Get audit logs by user ID and action type (paginated)",
        description = "Retrieve audit logs for a specific user and action type with pagination. Requires ADMIN, SUPER_ADMIN, or the user themselves."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paginated list of audit logs for the user and action type returned successfully")
    })
    @GetMapping("/user/{userId}/action/{action}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or @userSecurity.isCurrentUser(#userId)")
    public ResponseEntity<Page<AuditLog>> getAuditLogsByUserIdAndAction(
            @PathVariable Long userId,
            @PathVariable String action,
            Pageable pageable) {
        // For simplicity, using a default client ID of 1L
        Long clientId = 1L;
        Page<AuditLog> auditLogs = auditLogRepository.findByUserIdAndActionAndClientId(userId, action, clientId, pageable);
        return ResponseEntity.ok(auditLogs);
    }
}