package com.ust.restapicrudexample.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    @NotBlank
    @NotNull
    private String name;

    @Column(nullable = false, length = 20)
    @NotBlank
    @NotNull
    private String lastName;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank
    @NotNull
    @Email
    private String email;

    @Column(length = 12)
    @NotBlank
    @NotNull
    private String phone;

    @Column(length = 100)
    @NotBlank
    @NotNull
    private String address;

    @Column(nullable = false)
    private int status = 1;

}
