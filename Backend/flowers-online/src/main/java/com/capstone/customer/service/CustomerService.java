package com.capstone.customer.service;

import com.capstone.customer.dto.CustomerResponse;

public interface CustomerService {

    CustomerResponse registerCustomer(com.capstone.customer.dto.CustomerRegistrationRequest request);
}
