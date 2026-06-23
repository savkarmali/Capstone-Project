package com.capstone.customer.service;

import com.capstone.customer.dto.*;

public interface CustomerService {

    CustomerResponse registerCustomer(com.capstone.customer.dto.CustomerRegistrationRequest request);

    CustomerLoginResponse loginCustomer(CustomerLoginRequest request);

    ChangePasswordResponse changePassword(ChangePasswordRequest request);
}
