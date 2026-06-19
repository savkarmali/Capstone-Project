package com.capstone.survey.config;

import com.capstone.survey.entity.User;
import com.capstone.survey.enums.RoleName;
import com.capstone.survey.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final String DEFAULT_ADMIN_FIRST_NAME = "System";
    private static final String DEFAULT_ADMIN_LAST_NAME = "Admin";
    private static final String DEFAULT_ADMIN_EMAIL = "admin@survey.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "Admin@123";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail(DEFAULT_ADMIN_EMAIL)) {
            User admin = User.builder()
                    .firstName(DEFAULT_ADMIN_FIRST_NAME)
                    .lastName(DEFAULT_ADMIN_LAST_NAME)
                    .email(DEFAULT_ADMIN_EMAIL)
                    .password(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD))
                    .role(RoleName.ADMIN)
                    .active(true)
                    .build();

            userRepository.save(admin);
        }
    }
}