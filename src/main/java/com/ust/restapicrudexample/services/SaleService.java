package com.ust.restapicrudexample.services;

import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.model.Sale;
import com.ust.restapicrudexample.persistance.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    public List<Sale> listSales()
    {
        return saleRepository.findAll();
    }

    public List<Sale> listSalesInactives()
    {
        return saleRepository.findByStatus(0);
    }

    public Optional<Sale> getSaleById(Long id)
    {
        return saleRepository.findById(id);
    }

    public Sale createNewSale(Sale newSale)
    {
        return saleRepository.save(newSale);
    }

    public Boolean deleteSale(Long id)
    {
        if(saleRepository.findById(id).isPresent())
        {
            saleRepository.findById(id)
                    .map(sale -> {
                        sale.setStatus(0);
                        return saleRepository.save(sale);
                    });
            return true;
        }
        else
        {
            return false;
        }
    }

    public Sale updateSale(Sale newSale, Long id)
    {
        return saleRepository.findById(id)
                .map(sale -> {
                    sale.setDate(newSale.getDate());
                    sale.setCustomer(newSale.getCustomer());
                    sale.setItems(newSale.getItems());
                    sale.setTotal(newSale.getTotal());
                    sale.setStatus(newSale.getStatus());
                    return saleRepository.save(sale); //Se guarda el que ya tiene los cambios realizados
                })
                .orElseGet(() -> {
                    newSale.setId(id);
                    return saleRepository.save(newSale);
                });
    }

    public List<Item> getItemsBySaleId(Long id)
    {
        Optional<Sale> sale = saleRepository.findById(id);
        return sale.get().getItems();
    }
}
