package com.ust.restapicrudexample.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    @NotBlank
    @NotNull
    private String name;

    @Column(nullable = false, length = 100)
    @NotBlank
    @NotNull
    private String description;

    @Column(nullable = false)
    @NotNull
    @Min(value = 0)
    @Max(value = 10000)
    private double price;

    @Column(nullable = false, length = 20)
    @NotBlank
    @NotNull
    private String category;

    @Column(nullable = false)
    private int status = 1;

}
