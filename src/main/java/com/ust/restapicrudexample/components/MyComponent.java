package com.ust.restapicrudexample.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {

    @Value("${server.port}")
    private int serverPort;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    public void mostrarInformacion() {
        System.out.println("El puerto del servidor es: " + serverPort);
        System.out.println("La URL de la base de datos es: " + dbUrl);
        // Agrega aquí la lógica adicional que desees realizar con las propiedades configuradas
    }

}
