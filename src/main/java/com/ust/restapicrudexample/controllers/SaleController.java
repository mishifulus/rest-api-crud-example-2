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
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    SaleService saleService;

    @GetMapping("/sales")
    List<Sale> all()
    {

        return saleService.listSales();
    }

    @GetMapping("/salesinactives")
    List<Sale> allInactives()
    {

        return saleService.listSalesInactives();
    }

    @GetMapping("/sales/{id}")
    Sale getById(@PathVariable Long id)
    {
        return saleService.getSaleById(id).orElseThrow(() -> new SaleNotFoundException(id));
        //Optional<Sale> sale = saleRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return sale.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sales")
    Sale createNew(@Valid @RequestBody Sale newSale)
    {

        return saleService.createNewSale(newSale);
    }

    @DeleteMapping("/sales/{id}")
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

    @PutMapping("/sales/{id}")
    Sale updateOrCreate(@Valid @RequestBody Sale newSale, @PathVariable Long id) {
        return saleService.updateSale(newSale, id);
    }

    @GetMapping("/sales/{id}/items")
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
