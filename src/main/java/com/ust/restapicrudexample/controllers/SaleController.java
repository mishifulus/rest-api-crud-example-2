package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.SaleNotFoundException;
import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.model.Sale;
import com.ust.restapicrudexample.services.SaleService;
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
@RequestMapping(value="/sale")
@Api(tags = "Sale controller", description = "CRUD of sales in the application")
public class SaleController {

    @Autowired
    SaleService saleService;

    @GetMapping(value="/")
    @ApiOperation("Get all sales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Sale records were successfully obtained", response = Sale.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    List<Sale> all()
    {

        return saleService.listSales();
    }

    @GetMapping(value="/bystatus/{status}")
    @ApiOperation("Get all sales by status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Sale records were successfully obtained", response = Sale.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    List<Sale> getByStatus(@PathVariable int status)
    {

        return saleService.listSalesByStatus(status);
    }

    @GetMapping(value="/{id}")
    @ApiOperation("Get a sale by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The sale record was successfully obtained", response = Sale.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Sale getById(@PathVariable Long id)
    {
        return saleService.getSaleById(id).orElseThrow(() -> new SaleNotFoundException(id));
        //Optional<Sale> sale = saleRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return sale.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value="/")
    @ApiOperation("Create a new sale")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The sale was successfully created", response = Sale.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Sale createNew(@Valid @RequestBody Sale newSale)
    {

        return saleService.createNewSale(newSale);
    }

    @DeleteMapping(value="/{id}")
    @ApiOperation("Delete an existent sale")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The sale was successfully deleted", response = Sale.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (saleService.deleteSale(id))
        {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/{id}")
    @ApiOperation("Update an existent sale or create a new sale")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The sale was successfully updated", response = Sale.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Sale updateOrCreate(@Valid @RequestBody Sale newSale, @PathVariable Long id) {
        return saleService.updateSale(newSale, id);
    }

    @GetMapping(value="/{id}/items")
    @ApiOperation("Get all items by sale")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Item records by sale were successfully obtained", response = Sale.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    ResponseEntity<List<Item>> getItemsBySaleId(@PathVariable Long id)
    {

        if (saleService.getItemsBySaleId(id).size() > 0)
        {
            List<Item> items = saleService.getItemsBySaleId(id);
            return ResponseEntity.ok(items);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

}
