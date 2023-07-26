package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.CustomerNotFoundException;
import com.ust.restapicrudexample.model.Customer;
import com.ust.restapicrudexample.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/customer")
@Api(tags = "Customer controller", description = "CRUD of customers in the application")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping(value="/customers")
    @ApiOperation("Get all customers")
    List<Customer> all()
    {

        return customerService.listCustomers();
    }

    @GetMapping(value="/customersinactives")
    @ApiOperation("Get all inactives customers")
    List<Customer> allInactives()
    {

        return customerService.listCustomersInactives();
    }

    @GetMapping(value="/customer/{id}")
    @ApiOperation("Get a customer by id")
    Customer getById(@PathVariable Long id)
    {
        return customerService.getCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        //Optional<Customer> customer = customerRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value="/customer/")
    @ApiOperation("Create a new customer")
    Customer createNew(@Valid @RequestBody Customer newCustomer)
    {

        return customerService.createNewCustomer(newCustomer);
    }

    @DeleteMapping(value="/customer/{id}")
    @ApiOperation("Delete an existent customer")
    ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (customerService.deleteCustomer(id))
        {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/customer/{id}")
    @ApiOperation("Update an existent customer or create a new customer")
    Customer updateOrCreate(@Valid @RequestBody Customer newCustomer, @PathVariable Long id) {
        return customerService.updateCustomer(newCustomer, id);
    }
}
