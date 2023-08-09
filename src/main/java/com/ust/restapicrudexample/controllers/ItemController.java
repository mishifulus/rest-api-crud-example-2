package com.ust.restapicrudexample.controllers;

import com.ust.restapicrudexample.controllers.handlers.ItemNotFoundException;
import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.services.ItemService;
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
@RequestMapping(value="/item")
@Api(tags = "Item controller", description = "CRUD of items in the application")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping(value="/")
    @ApiOperation("Get all items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Item records were successfully obtained", response = Item.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    List<Item> all()
    {

        return itemService.listItems();
    }

    @GetMapping(value="/bystatus/{status}")
    @ApiOperation("Get all items by status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Item records were successfully obtained", response = Item.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    List<Item> getByStatus(@PathVariable int status)
    {

        return itemService.listItemsByStatus(status);
    }

    @GetMapping(value="/{id}")
    @ApiOperation("Get an item by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The item record was successfully obtained", response = Item.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Item getById(@PathVariable Long id)
    {
        return itemService.getItemById(id).orElseThrow(() -> new ItemNotFoundException(id));
        //Optional<Item> item = itemRepository.findById(id); //Para evitar tronar si no se encuentra el id
        //return item.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value="/")
    @ApiOperation("Create a new item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The item was successfully created", response = Item.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Item createNew(@Valid @RequestBody Item newItem)
    {

        return itemService.createNewItem(newItem);
    }

    @DeleteMapping(value="/{id}")
    @ApiOperation("Delete an existent item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The item was successfully deleted", response = Item.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
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

    @PutMapping(value="/{id}")
    @ApiOperation("Update an existent item or create a new item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. The item was successfully updated", response = Item.class),
            @ApiResponse(code = 400, message = "BAD REQUEST. The server could not understand the request", response = String.class),
            @ApiResponse(code = 200, message = "INTERNAL SERVER ERROR. Server error, the request could not be processed")
    })
    Item updateOrCreate(@Valid @RequestBody Item newItem, @PathVariable Long id) {
        return itemService.updateItem(newItem, id);
    }
}
