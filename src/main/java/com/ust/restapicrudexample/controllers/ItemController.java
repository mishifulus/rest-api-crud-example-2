package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.ItemNotFoundException;
import com.ust.restapicrudexample.controllers.handlers.SaleNotFoundException;
import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.persistance.ItemRepository;
import com.ust.restapicrudexample.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    List<Item> all()
    {

        return itemService.listItems();
    }

    @GetMapping("/itemsinactives")
    List<Item> allInactives()
    {

        return itemService.listItemsInactives();
    }

    @GetMapping("/items/{id}")
    Item getById(@PathVariable Long id)
    {
        return itemService.getItemById(id).orElseThrow(() -> new ItemNotFoundException(id));
        //Optional<Item> item = itemRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return item.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/items")
    Item createNew(@Valid @RequestBody Item newItem)
    {

        return itemService.createNewItem(newItem);
    }

    @DeleteMapping("/items/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id)
    {

        if (itemService.deleteItem(id))
        {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/items/{id}")
    Item updateOrCreate(@Valid @RequestBody Item newItem, @PathVariable Long id) {
        return itemService.updateItem(newItem, id);
    }
}
