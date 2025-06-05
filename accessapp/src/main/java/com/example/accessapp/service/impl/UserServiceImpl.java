package com.example.accessapp.service.impl;

import com.example.accessapp.dto.UserDto;
import com.example.accessapp.entity.User;
import com.example.accessapp.repository.UserRepository;
import com.example.accessapp.repository.RoleRepository;
import com.example.accessapp.repository.UserRoleRepository;
import com.example.accessapp.service.UserService;
import com.example.accessapp.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Implementation of the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByClientId(Long clientId) {
        return userRepository.findAllByClientId(clientId);
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        
        // Handle name conversion - split full name into first and last name if available
        String fullName = user.getName();
        if (fullName != null && !fullName.isEmpty()) {
            String[] nameParts = fullName.split(" ", 2);
            userDto.setFirstName(nameParts[0]);
            userDto.setLastName(nameParts.length > 1 ? nameParts[1] : "");
        } else {
            userDto.setFirstName("");
            userDto.setLastName("");
        }
        
        userDto.setEnabled(user.isEnabled());
        userDto.setCreatedAt(LocalDateTime.now()); // Set current time as we don't have these fields in User entity
        userDto.setUpdatedAt(LocalDateTime.now());
        userDto.setClientId(user.getClientId());
        
        // Set roles directly
        userDto.setRoles(user.getRoles());
        // Set phone number
        userDto.setPhoneNumber(user.getPhoneNumber());
        
        return userDto;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateEnabledStatus(Long id, boolean enabled) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Combine firstName and lastName into names
        String fullName = (userDto.getFirstName() != null ? userDto.getFirstName() : "") +
                          " " +
                          (userDto.getLastName() != null ? userDto.getLastName() : "");
        user.setName(fullName.trim());

        // Set phone number if present
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        // Update email if present
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        // Update enabled status if present
        user.setEnabled(userDto.isEnabled());

        // Optionally update other fields as needed

        return userRepository.save(user);
    }

    @Override
    public void assignRoleToUser(Long userId, Long roleId, Long assignedBy, HttpServletRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        com.example.accessapp.entity.Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
        com.example.accessapp.entity.UserRole userRole = new com.example.accessapp.entity.UserRole(user, role, assignedBy);
        userRole.setClientId(user.getClientId());
        userRoleRepository.save(userRole);
        // Audit log
        String ip = request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For").split(",")[0] : request.getRemoteAddr();
        auditLogService.createAuditLog(
            "ASSIGN_ROLE",
            "Assigned role '" + role.getName() + "' to user '" + user.getUsername() + "'",
            assignedBy,
            null, // username of assigner (optional, can be fetched if needed)
            ip,
            request.getHeader("User-Agent"),
            user.getClientId(),
            "UserRole",
            null // entityId (composite key not supported)
        );
    }
}