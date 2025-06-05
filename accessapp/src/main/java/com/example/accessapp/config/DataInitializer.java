package com.example.accessapp.config;

import com.example.accessapp.entity.Role;
import com.example.accessapp.entity.User;
import com.example.accessapp.repository.RoleRepository;
import com.example.accessapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Ensure default roles exist
        String[] defaultRoles = {"SUPER_ADMIN", "ADMIN", "USER"};
        for (String roleName : defaultRoles) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                role.setDescription(roleName + " role");
                role.setClientId(1L); // Default client for system roles
                return roleRepository.save(role);
            });
        }

        // Ensure at least one super admin user exists
        Optional<User> superAdminOpt = userRepository.findFirstByRoleName("SUPER_ADMIN");
        if (superAdminOpt.isEmpty()) {
            // Only create if not present, and do not overwrite user preferences
            User superAdmin = new User();
            if (superAdmin.getUsername() == null) superAdmin.setUsername("Cyprienhr");
            if (superAdmin.getEmail() == null) superAdmin.setEmail("rwendere@gmail.com");
            if (superAdmin.getPassword() == null) superAdmin.setPassword(passwordEncoder.encode("Rwendere@2001"));
            superAdmin.setEnabled(true);
            superAdmin.setClientId(1L);
            superAdmin.setSuperAdmin(true);
            // Save the user first to get an ID
            superAdmin = userRepository.save(superAdmin);
            // Now assign roles
            Role superAdminRole = roleRepository.findByName("SUPER_ADMIN").get();
            Set<Role> roles = new HashSet<>();
            roles.add(superAdminRole);
            superAdmin.setRoles(roles);
            // Save the user again with roles
            userRepository.save(superAdmin);
            System.out.println("\n*** Default SUPER_ADMIN created: username=Cyprienhr, password=Rwendere@2001 ***");
            System.out.println("*** Please change this password immediately after first login! ***\n");
        }
    }
} 