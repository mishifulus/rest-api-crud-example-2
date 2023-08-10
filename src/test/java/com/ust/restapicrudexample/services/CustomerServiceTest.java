package com.ust.restapicrudexample.services;

import com.ust.restapicrudexample.model.Customer;
import com.ust.restapicrudexample.persistence.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;
    private Customer customerA, customerB;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        customerA = new Customer(1L, "Citlalli", "Martinez", "citla@gmail.com","524772337489", "Geiser 112A Leon, Guanajuato", 1);
        customerB = new Customer(2L, "Jazmin", "Rios", "jaz@gmail.com","524772849586", "Heroes 212 Leon, Guanajuato", 1);
    }

    @Test
    void getCustomersTest()
    {
        List<Customer> customerList = new ArrayList<>();

        customerList.add(customerA);
        customerList.add(customerB);
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> result = customerService.listCustomers();

        assertEquals(2, result.size());
        assertEquals("Citlalli", result.get(0).getName());
        assertEquals("Jazmin", result.get(1).getName());
    }

    @Test
    void getCustomerTest()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerA));
        Customer result = customerService.getCustomerById(1L).get();
        assertEquals("Citlalli", result.getName());
    }

    @Test
    void createNewCustomerTest()
    {
        when(customerRepository.save(any(Customer.class))).thenReturn(customerA);
        Customer result = customerService.createNewCustomer(customerA);

        assertNotNull(result);
        assertEquals(result.getName(), "Citlalli");
        assertEquals(result.getId(), 1L);
    }

    @Test
    void updateCustomerTest()
    {
        Customer customerB = new Customer();
        customerB.setEmail("newemail@gmail.com");
        customerB.setAddress("Home");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerB));
        customerService.updateCustomer(customerB, 1L);
        Customer result = customerService.getCustomerById(1L).get();
        assertEquals("newemail@gmail.com",result.getEmail());
        assertEquals("Home",result.getAddress());
    }

    @Test
    void deleteCustomerTest()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerA));
        customerService.deleteCustomer(1L);
        Customer result = customerService.getCustomerById(1L).get();
        assertEquals(0,result.getStatus());
    }
}
