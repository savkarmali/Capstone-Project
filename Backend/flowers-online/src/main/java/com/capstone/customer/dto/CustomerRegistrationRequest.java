package com.capstone.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegistrationRequest {

        @NotBlank(message = "Title is required")
        private String title;

        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must be 100 characters or less")
        private String firstName;

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must be 100 characters or less")
        private String lastName;

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address")
        @Size(max = 150, message = "Email must be 150 characters or less")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
        private String password;

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain 10 digits")
        private String phoneNumber;

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City must be 100 characters or less")
        private String city;

        @NotBlank(message = "Country is required")
        @Size(max = 100, message = "Country must be 100 characters or less")
        private String country;
}
