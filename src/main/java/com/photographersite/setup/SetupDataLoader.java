package com.photographersite.setup;

import com.photographersite.entity.Role;
import com.photographersite.entity.User;
import com.photographersite.repository.RoleRepository;
import com.photographersite.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;


        var adminRole = createRoleIfNotFound("ROLE_ADMIN");
        var userRole = createRoleIfNotFound("ROLE_USER");
        if (userRepository.findByUsername("admin").isEmpty()) {
            var roles = new HashSet<Role>();
            roles.add(adminRole);
            roles.add(userRole);

            User admin = new User("admin",
                    passwordEncoder.encode("your password"))
                    .setEmail("test@test.com")
                    .setRole(roles);
            userRepository.save(admin);
        }

        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(String name) {

        Optional<Role> roleOptional = roleRepository.findByName(name);
        Role role;
        if (roleOptional.isEmpty()) {
            role = new Role(name);
            roleRepository.save(role);
        } else {
            role = roleOptional.get();
        }
        return role;
    }
}
