package com.ust.restapicrudexample.persistence;

import com.ust.restapicrudexample.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{
    List<Item> findByStatus(int status);
}
