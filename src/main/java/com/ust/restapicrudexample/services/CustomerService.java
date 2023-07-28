package com.ust.restapicrudexample.services;

import com.ust.restapicrudexample.model.Customer;
import com.ust.restapicrudexample.persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> listCustomers()
    {

        return customerRepository.findAll();
    }

    public List<Customer> listCustomersInactives()
    {

        return customerRepository.findByStatus(0);
    }

    public Optional<Customer> getCustomerById(Long id)
    {

        return customerRepository.findById(id);
    }

    public Customer createNewCustomer(Customer newCustomer)
    {

        return customerRepository.save(newCustomer);
    }

    public Boolean deleteCustomer(Long id)
    {
        if(customerRepository.findById(id).isPresent())
        {
            customerRepository.findById(id)
                    .map(customer -> {
                        customer.setStatus(0);
                        return  customerRepository.save(customer);
                    });
            return true;
        }
        else
        {
            return false;
        }
    }

    public Customer updateCustomer(Customer newCustomer, Long id)
    {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(newCustomer.getName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setPhone(newCustomer.getPhone());
                    customer.setAddress(newCustomer.getAddress());
                    customer.setStatus(newCustomer.getStatus());
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return customerRepository.save(newCustomer);
                });
    }
}
