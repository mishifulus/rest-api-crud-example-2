package com.ust.restapicrudexample.controllers.handlers;

public class SaleNotFoundException extends RuntimeException{

    private Long id;

    public SaleNotFoundException(Long id)
    {
        super("Could not find sale " + id);
    }
}
