package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.SaleNotFoundException;
import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.model.Sale;
import com.ust.restapicrudexample.services.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping(value="/sales")
    @ApiOperation("Get all sales")
    List<Sale> all()
    {

        return saleService.listSales();
    }

    @GetMapping(value="/salesinactives")
    @ApiOperation("Get all inactives sales")
    List<Sale> allInactives()
    {

        return saleService.listSalesInactives();
    }

    @GetMapping(value="/sale/{id}")
    @ApiOperation("Get a sale by id")
    Sale getById(@PathVariable Long id)
    {
        return saleService.getSaleById(id).orElseThrow(() -> new SaleNotFoundException(id));
        //Optional<Sale> sale = saleRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return sale.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value="/sale")
    @ApiOperation("Create a new sale")
    Sale createNew(@Valid @RequestBody Sale newSale)
    {

        return saleService.createNewSale(newSale);
    }

    @DeleteMapping(value="/sale/{id}")
    @ApiOperation("Delete an existent sale")
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

    @PutMapping(value="/sale/{id}")
    @ApiOperation("Update an existent sale or create a new sale")
    Sale updateOrCreate(@Valid @RequestBody Sale newSale, @PathVariable Long id) {
        return saleService.updateSale(newSale, id);
    }

    @GetMapping(value="/sales/{id}/items")
    @ApiOperation("Get all items by sale")
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
