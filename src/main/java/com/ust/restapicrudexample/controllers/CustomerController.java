package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.CustomerNotFoundException;
import com.ust.restapicrudexample.model.Customer;
import com.ust.restapicrudexample.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @GetMapping(value="/")
    @ApiOperation("Get all customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Customer records were successfully obtained", response = Customer.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    List<Customer> all()
    {

        return customerService.listCustomers();
    }

    @GetMapping(value="/bystatus/{status}")
    @ApiOperation("Get all customers by status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Customer records were successfully obtained", response = Customer.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    List<Customer> getByStatus(@PathVariable int status)
    {

        return customerService.listCustomersByStatus(status);
    }

    @GetMapping(value="/{id}")
    @ApiOperation("Get a customer by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The customer record was successfully obtained", response = Customer.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Customer getById(@PathVariable Long id)
    {
        return customerService.getCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        //Optional<Customer> customer = customerRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value="/")
    @ApiOperation("Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The customer was successfully created", response = Customer.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Customer createNew(@Valid @RequestBody Customer newCustomer)
    {

        return customerService.createNewCustomer(newCustomer);
    }

    @DeleteMapping(value="/{id}")
    @ApiOperation("Delete an existent customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The customer was successfully deleted", response = Customer.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
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

    @PutMapping(value="/{id}")
    @ApiOperation("Update an existent customer or create a new customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The customer was successfully updated", response = Customer.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Customer updateOrCreate(@Valid @RequestBody Customer newCustomer, @PathVariable Long id) {
        return customerService.updateCustomer(newCustomer, id);
    }
}
