package com.ust.restapicrudexample.services;

import com.ust.restapicrudexample.model.Customer;
import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.model.Sale;
import com.ust.restapicrudexample.persistence.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;
    private Sale saleA, saleB;
    private Item item1, item2;
    private Customer customer;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        List<Item> items = new ArrayList<>();
        item1 = new Item(1L, "Item1", "Item1", 5.0, "ITEM", 1);
        item2 = new Item(2L, "Item2", "Item2", 5.0, "ITEM", 1);

        items.add(item1);
        items.add(item2);
        customer = new Customer(1L, "Citlalli", "Martinez", "citla@gmail.com","524772337489", "Geiser 112A Leon, Guanajuato", 1);
        LocalDateTime date = LocalDateTime.now();

        saleA = new Sale(1L, 10.0, items, customer, date, 1);
        saleB = new Sale(2L, 5.0, items, customer, date, 0);
    }

    @Test
    void getSalesTest()
    {
        List<Sale> saleList = new ArrayList<>();

        saleList.add(saleA);
        saleList.add(saleB);
        when(saleRepository.findAll()).thenReturn(saleList);
        List<Sale> result = saleService.listSales();

        assertEquals(2, result.size());
        assertEquals(10.0, result.get(0).getTotal());
        assertEquals(5.0, result.get(1).getTotal());
    }

    @Test
    void createNewSaleTest()
    {
        when(saleRepository.save(any(Sale.class))).thenReturn(saleA);
        Sale result = saleService.createNewSale(saleA);

        assertNotNull(result);
        assertEquals( 10.0, result.getTotal());
        assertEquals(1L, result.getId());
    }

    @Test
    void updateSaleTest()
    {
        Sale saleB = new Sale();
        saleB.setTotal(11.0);
        when(saleRepository.findById(1L)).thenReturn(Optional.of(saleB));
        saleService.updateSale(saleB, 1L);

        Sale result = saleService.getSaleById(1L).get();
        assertEquals(11.0, result.getTotal());
    }

    @Test
    void deleteSaleTest()
    {
        when(saleRepository.findById(1L)).thenReturn(Optional.of(saleA));
        saleService.deleteSale(1L);
        Sale result = saleService.getSaleById(1L).get();
        assertEquals(0, result.getStatus());
    }
}
