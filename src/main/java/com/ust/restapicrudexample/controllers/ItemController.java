package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.ItemNotFoundException;
import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.services.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/item")
@Api(tags = "Item controller", description = "CRUD of items in the application")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping(value="/items")
    @ApiOperation("Get all items")
    List<Item> all()
    {

        return itemService.listItems();
    }

    @GetMapping(value="/itemsinactives")
    @ApiOperation("Get all inactives items")
    List<Item> allInactives()
    {

        return itemService.listItemsInactives();
    }

    @GetMapping(value="/item/{id}")
    @ApiOperation("Get an item by id")
    Item getById(@PathVariable Long id)
    {
        return itemService.getItemById(id).orElseThrow(() -> new ItemNotFoundException(id));
        //Optional<Item> item = itemRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return item.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value="/item")
    @ApiOperation("Create a new item")
    Item createNew(@Valid @RequestBody Item newItem)
    {

        return itemService.createNewItem(newItem);
    }

    @DeleteMapping(value="/item/{id}")
    @ApiOperation("Delete an existent item")
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

    @PutMapping(value="/item/{id}")
    @ApiOperation("Update an existent item or create a new item")
    Item updateOrCreate(@Valid @RequestBody Item newItem, @PathVariable Long id) {
        return itemService.updateItem(newItem, id);
    }
}
