package com.capstone.config;

import com.capstone.customer.entity.Customer;
import com.capstone.customer.entity.Role;
import com.capstone.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class AdminUserInitializer implements org.springframework.boot.CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@flowersonline.com";
    private static final String ADMIN_PASSWORD = "admin123";

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (customerRepository.existsByEmail(ADMIN_EMAIL)) {
            return;
        }

        Customer admin = new Customer();
        admin.setTitle("Mr");
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        admin.setPhoneNumber("9999999999");
        admin.setCity("Pune");
        admin.setCountry("India");
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(LocalDateTime.now());

        customerRepository.save(admin);
    }
}
