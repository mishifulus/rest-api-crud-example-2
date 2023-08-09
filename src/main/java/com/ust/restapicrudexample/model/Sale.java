package com.ust.restapicrudexample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SALE")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Total is mandatory")
    @Min(value = 0)
    @Max(value = 1000000)
    private double total;

     // Validaciones con JSR 380
    @ManyToMany // Relación muchos a muchos con Item
    @JoinTable(
            name = "SALE_ITEM", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "sale_id"), // Columna que hace referencia a la tabla Sale
            inverseJoinColumns = @JoinColumn(name = "item_id") // Columna que hace referencia a la tabla Item
    )
    private List<Item> items;

    @ManyToOne // Solo un cliente
    @NotNull(message = "Customer is mandatory")
    private Customer customer;

    @Column(nullable = false)
    @NotNull(message = "Date is mandatory")
    private LocalDateTime date;

    @Column(nullable = false)
    private int status = 1;
}
