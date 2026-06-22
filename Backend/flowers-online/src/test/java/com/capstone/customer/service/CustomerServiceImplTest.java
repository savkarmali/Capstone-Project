package com.capstone.customer.service;

import com.capstone.customer.dto.CustomerLoginRequest;
import com.capstone.customer.dto.CustomerLoginResponse;
import com.capstone.customer.dto.CustomerRegistrationRequest;
import com.capstone.customer.dto.CustomerResponse;
import com.capstone.customer.entity.Customer;
import com.capstone.customer.repository.CustomerRepository;
import com.capstone.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Test
    void registerCustomerShouldSaveNewCustomer() {
        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerService service = new CustomerServiceImpl(repository);

        CustomerRegistrationRequest request = getRequest();

        when(repository.existsByEmail("mary@example.com")).thenReturn(false);
        when(repository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            customer.setId(1L);
            return customer;
        });

        CustomerResponse response = service.registerCustomer(request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(captor.capture());

        assertEquals("Mary", captor.getValue().getFirstName());
        assertEquals("mary@example.com", response.getEmail());
        assertEquals(1L, response.getId());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void registerCustomerShouldThrowExceptionForDuplicateEmail() {
        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerService service = new CustomerServiceImpl(repository);

        CustomerRegistrationRequest request = getRequest();
        when(repository.existsByEmail("mary@example.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.registerCustomer(request));
    }

    @Test
    void loginCustomerShouldReturnSuccessForValidCredentials() {
        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerService service = new CustomerServiceImpl(repository);

        Customer customer = getCustomer();
        CustomerLoginRequest request = new CustomerLoginRequest();
        request.setEmail("mary@example.com");
        request.setPassword("secret123");

        when(repository.findByEmail("mary@example.com")).thenReturn(Optional.of(customer));

        CustomerLoginResponse response = service.loginCustomer(request);

        assertEquals(1L, response.getCustomerId());
        assertEquals("Mary", response.getFirstName());
        assertEquals("Login successful", response.getMessage());
    }

    @Test
    void loginCustomerShouldThrowExceptionForWrongPassword() {
        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerService service = new CustomerServiceImpl(repository);

        Customer customer = getCustomer();
        CustomerLoginRequest request = new CustomerLoginRequest();
        request.setEmail("mary@example.com");
        request.setPassword("wrong-password");

        when(repository.findByEmail("mary@example.com")).thenReturn(Optional.of(customer));

        assertThrows(IllegalArgumentException.class, () -> service.loginCustomer(request));
    }
    private CustomerRegistrationRequest getRequest() {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest();
        request.setTitle("Ms");
        request.setFirstName("Mary");
        request.setLastName("Rose");
        request.setEmail("mary@example.com");
        request.setPassword("secret123");
        request.setPhoneNumber("9876543210");
        request.setCity("Bengaluru");
        request.setCountry("India");
        return request;
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setTitle("Ms");
        customer.setFirstName("Mary");
        customer.setLastName("Rose");
        customer.setEmail("mary@example.com");
        customer.setPassword("secret123");
        customer.setPhoneNumber("9876543210");
        customer.setCity("Bengaluru");
        customer.setCountry("India");
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }
}