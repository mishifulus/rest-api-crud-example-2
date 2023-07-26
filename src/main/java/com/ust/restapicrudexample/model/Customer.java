package com.ust.restapicrudexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank(message = "Name is mandatory")
    @NotNull
    private String name;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Last name is mandatory")
    @NotNull
    private String lastName;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "Email is mandatory")
    @NotNull
    @Email
    private String email;

    @Column(length = 12)
    @NotBlank(message = "Phone is mandatory")
    @NotNull
    private String phone;

    @Column(length = 100)
    @NotBlank(message = "Address is mandatory")
    @NotNull
    private String address;

    @Column(nullable = false)
    private int status = 1;

}
