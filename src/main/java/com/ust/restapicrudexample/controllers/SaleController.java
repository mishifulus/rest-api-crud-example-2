package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.SaleNotFoundException;
import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.model.Sale;
import com.ust.restapicrudexample.persistance.SaleRepository;
import com.ust.restapicrudexample.services.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/sale")
public class SaleController {

    @Autowired
    SaleService saleService;

    @GetMapping(value="/sales")
    List<Sale> all()
    {

        return saleService.listSales();
    }

    @GetMapping(value="/salesinactives")
    List<Sale> allInactives()
    {

        return saleService.listSalesInactives();
    }

    @GetMapping(value="/sale/{id}")
    Sale getById(@PathVariable Long id)
    {
        return saleService.getSaleById(id).orElseThrow(() -> new SaleNotFoundException(id));
        //Optional<Sale> sale = saleRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return sale.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value="/sale")
    Sale createNew(@Valid @RequestBody Sale newSale)
    {

        return saleService.createNewSale(newSale);
    }

    @DeleteMapping(value="/sale/{id}")
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
    Sale updateOrCreate(@Valid @RequestBody Sale newSale, @PathVariable Long id) {
        return saleService.updateSale(newSale, id);
    }

    @GetMapping(value="/sales/{id}/items")
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
