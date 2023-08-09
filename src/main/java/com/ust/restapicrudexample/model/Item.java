package com.ust.restapicrudexample.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank(message = "Name is mandatory")
    @NotNull
    private String name;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Description is mandatory")
    @NotNull
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Price is mandatory")
    @Min(value = 0)
    @Max(value = 10000)
    private double price;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Category is mandatory")
    @NotNull
    private String category;

    @Column(nullable = false)
    private int status = 1;

}
