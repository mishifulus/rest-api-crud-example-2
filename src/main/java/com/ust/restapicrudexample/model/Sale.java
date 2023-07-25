package com.ust.restapicrudexample.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotNull
    @Min(value = 0)
    @Max(value = 1000000)
    private float total;

     // Validaciones con JSR 380
    @ManyToMany // Relación muchos a muchos con Item
    @JoinTable(
            name = "SALE_ITEM", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "sale_id"), // Columna que hace referencia a la tabla Sale
            inverseJoinColumns = @JoinColumn(name = "item_id") // Columna que hace referencia a la tabla Item
    )
    private List<Item> items;

    @ManyToOne // Solo un cliente
    @NotNull
    private Customer customer;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime date;

    @Column(nullable = false)
    private int status = 1;
}
