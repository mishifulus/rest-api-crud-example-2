package com.ust.restapicrudexample.persistance;

import com.ust.restapicrudexample.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
    List<Sale> findByStatus(int status);
}
