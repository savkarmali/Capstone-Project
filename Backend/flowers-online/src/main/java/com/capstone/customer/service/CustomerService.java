package com.capstone.customer.service;

import com.capstone.customer.dto.CustomerLoginRequest;
import com.capstone.customer.dto.CustomerLoginResponse;
import com.capstone.customer.dto.CustomerResponse;

public interface CustomerService {

    CustomerResponse registerCustomer(com.capstone.customer.dto.CustomerRegistrationRequest request);

    CustomerLoginResponse loginCustomer(CustomerLoginRequest request);
}
