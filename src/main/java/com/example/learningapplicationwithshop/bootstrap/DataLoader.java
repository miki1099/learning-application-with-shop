package com.example.learningapplicationwithshop.bootstrap;

import com.example.learningapplicationwithshop.model.Role;
import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.repositories.RoleRepository;
import com.example.learningapplicationwithshop.repositories.UserRepository;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");
        roleRepository.save(role);

        Role roleAdmin = new Role();
        role.setId(2);
        role.setName("ADMIN");
        roleRepository.save(role);

        User adminUser = new User();
        adminUser.setId(1);
        adminUser.setEnabled(true);
        adminUser.setEmail("email@wp.pl");
        adminUser.setLogin("AdminLogin");
        adminUser.setPassword(passwordEncoder.encode("Password"));
        adminUser.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(adminUser);
    }
}
