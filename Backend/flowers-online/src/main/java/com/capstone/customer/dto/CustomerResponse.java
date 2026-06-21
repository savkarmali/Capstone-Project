package com.capstone.customer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerResponse {

    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String city;
    private String country;
    private LocalDateTime createdAt;
}
