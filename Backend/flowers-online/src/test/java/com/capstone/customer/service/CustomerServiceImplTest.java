package com.capstone.customer.service;

import com.capstone.customer.dto.CustomerRegistrationRequest;
import com.capstone.customer.dto.CustomerResponse;
import com.capstone.customer.entity.Customer;
import com.capstone.customer.repository.CustomerRepository;
import com.capstone.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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
}