package com.ust.restapicrudexample.controllers.handlers;

public class CustomerNotFoundException extends RuntimeException{

    private Long id;

    public CustomerNotFoundException(Long id)
    {
        super("Could not find customer " + id);
    }
}
