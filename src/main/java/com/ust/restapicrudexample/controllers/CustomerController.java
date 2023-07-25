package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.CustomerNotFoundException;
import com.ust.restapicrudexample.model.Customer;
import com.ust.restapicrudexample.persistance.CustomerRepository;
import com.ust.restapicrudexample.services.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @ApiOperation(value = "All()"
            ,notes = "Lists all existing customers in the database.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Customers are sourced correctly.", response = Customer.class ),
            @ApiResponse(code = 400, message = "Bad Request. Answer in string format.", response = String.class),
            @ApiResponse(code = 500, message = "Unexpected system error.") })
    @GetMapping("/customers")
    List<Customer> all()
    {

        return customerService.listCustomers();
    }

    @GetMapping("/customersinactives")
    List<Customer> allInactives()
    {

        return customerService.listCustomersInactives();
    }

    @GetMapping("/customers/{id}")
    Customer getById(@PathVariable Long id)
    {
        return customerService.getCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        //Optional<Customer> customer = customerRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/customers")
    Customer createNew(@Valid @RequestBody Customer newCustomer)
    {

        return customerService.createNewCustomer(newCustomer);
    }

    @DeleteMapping("/customers/{id}")
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

    @PutMapping("/customers/{id}")
    Customer updateOrCreate(@Valid @RequestBody Customer newCustomer, @PathVariable Long id) {
        return customerService.updateCustomer(newCustomer, id);
    }
}
