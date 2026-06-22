package com.capstone.customer.controller;

import com.capstone.customer.dto.CustomerLoginRequest;
import com.capstone.customer.dto.CustomerLoginResponse;
import com.capstone.customer.dto.CustomerRegistrationRequest;
import com.capstone.customer.dto.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    private final com.capstone.customer.service.CustomerService customerService;

    public CustomerController(com.capstone.customer.service.CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(
            @Valid @RequestBody CustomerRegistrationRequest request) {
        CustomerResponse response = customerService.registerCustomer(request);
        return new ResponseEntity<CustomerResponse>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerLoginResponse> loginCustomer(
            @Valid @RequestBody CustomerLoginRequest request) {
        return ResponseEntity.ok(customerService.loginCustomer(request));
    }
}
