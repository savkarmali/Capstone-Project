package com.capstone.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerLoginResponse {

    private Long customerId;
    private String firstName;
    private String email;
    private String message;
    private String token;
    private String tokenType;

}
