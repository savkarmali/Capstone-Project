package com.capstone.customer.service.impl;

import com.capstone.customer.dto.*;
import com.capstone.customer.entity.Customer;
import com.capstone.customer.repository.CustomerRepository;
import com.capstone.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements com.capstone.customer.service.CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public CustomerResponse registerCustomer(CustomerRegistrationRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Customer customer = new Customer();
        customer.setTitle(request.getTitle());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
//        customer.setPassword(request.getPassword());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setCity(request.getCity());
        customer.setCountry(request.getCountry());
        customer.setCreatedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);
        return toResponse(savedCustomer);
    }

    @Override
    public CustomerLoginResponse loginCustomer(CustomerLoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

//        if (!customer.getPassword().equals(request.getPassword())) {
//            throw new IllegalArgumentException("Invalid email or password");
//        }
        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        CustomerLoginResponse response = new CustomerLoginResponse();
        response.setCustomerId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setEmail(customer.getEmail());
        response.setMessage("Login successful");
        response.setToken(jwtUtil.generateToken(customer.getEmail()));
        response.setTokenType("Bearer");
        return response;
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Customer account not found"));

//        if (!customer.getPassword().equals(request.getOldPassword())) {
//            throw new IllegalArgumentException("Old password is incorrect");
//        }
        if (!passwordEncoder.matches(request.getOldPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

//        customer.setPassword(request.getNewPassword());
        customer.setPassword(passwordEncoder.encode(request.getNewPassword()));
        Customer updatedCustomer = customerRepository.save(customer);

        ChangePasswordResponse response = new ChangePasswordResponse();
        response.setEmail(updatedCustomer.getEmail());
        response.setMessage("Password changed successfully");
        return response;
    }

    private CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setTitle(customer.getTitle());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCity(customer.getCity());
        response.setCountry(customer.getCountry());
        response.setCreatedAt(customer.getCreatedAt());
        return response;
    }
}
