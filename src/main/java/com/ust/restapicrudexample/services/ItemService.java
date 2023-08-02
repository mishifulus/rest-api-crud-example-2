package com.ust.restapicrudexample.services;

import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.persistence.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public List<Item> listItems()
    {
        return itemRepository.findAll();
    }

    public List<Item> listItemsByStatus(int status)
    {

        return itemRepository.findByStatus(status);
    }

    public Optional<Item> getItemById(Long id)
    {
        return itemRepository.findById(id);
    }

    public Item createNewItem(Item newItem)
    {
        return itemRepository.save(newItem);
    }

    public Boolean deleteItem(Long id)
    {
        if(itemRepository.findById(id).isPresent())
        {
            itemRepository.findById(id)
                    .map(item -> {
                        item.setStatus(0);
                        return itemRepository.save(item);
                    });
            return true;
        }
        else
        {
            return false;
        }
    }

    public Item updateItem(Item newItem, Long id)
    {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setDescription(newItem.getDescription());
                    item.setCategory(newItem.getCategory());
                    item.setPrice(newItem.getPrice());
                    item.setStatus(newItem.getStatus());
                    return itemRepository.save(item); //Se guarda el que ya tiene los cambios realizados
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return itemRepository.save(newItem);
                });
    }
}
