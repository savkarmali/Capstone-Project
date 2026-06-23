package com.capstone.customer.service.impl;

import com.capstone.customer.dto.*;
import com.capstone.customer.entity.Customer;
import com.capstone.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements com.capstone.customer.service.CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public com.capstone.customer.dto.CustomerResponse registerCustomer(com.capstone.customer.dto.CustomerRegistrationRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Customer customer = new Customer();
        customer.setTitle(request.getTitle());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
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

        if (!customer.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        CustomerLoginResponse response = new CustomerLoginResponse();
        response.setCustomerId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setEmail(customer.getEmail());
        response.setMessage("Login successful");
        return response;
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Customer account not found"));

        if (!customer.getPassword().equals(request.getOldPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        customer.setPassword(request.getNewPassword());
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
