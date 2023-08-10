package com.ust.restapicrudexample.services;

import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.persistence.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;
    private Item itemA, itemB;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        itemA = new Item(1L, "Libreta", "Libreta profesional de 100 hojas raya", 25.5, "Papeleria", 1);
        itemB = new Item(2L, "Borrador", "Borrador para lapiz", 5.2, "Papeleria", 1);
    }

    @Test
    void getItemsTest()
    {
        List<Item> itemList = new ArrayList<>();

        itemList.add(itemA);
        itemList.add(itemB);
        when(itemRepository.findAll()).thenReturn(itemList);
        List<Item> result = itemService.listItems();

        assertEquals(2, result.size());
        assertEquals("Libreta", result.get(0).getName());
        assertEquals("Borrador", result.get(1).getName());
    }

    @Test
    void getItemTest()
    {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(itemA));
        Item result = itemService.getItemById(1L).get();
        assertEquals("Libreta", result.getName());
    }

    @Test
    void createNewItemTest()
    {
        when(itemRepository.save(any(Item.class))).thenReturn(itemA);
        Item result = itemService.createNewItem(itemA);

        assertNotNull(result);
        assertEquals(result.getName(), "Libreta");
        assertEquals(result.getId(), 1L);
    }

    @Test
    void updateItemTest()
    {
        Item itemB = new Item();
        itemB.setName("Lapiz");
        itemB.setPrice(12.0);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(itemB));
        itemService.updateItem(itemB,1L);
        Item result = itemService.getItemById(1L).get();
        assertEquals("Lapiz", result.getName());
        assertEquals(12.0, result.getPrice());
    }

    @Test
    void deleteItemTest()
    {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(itemA));
        itemService.deleteItem(1L);
        Item result = itemService.getItemById(1L).get();
        assertEquals(0, result.getStatus());
    }
}
