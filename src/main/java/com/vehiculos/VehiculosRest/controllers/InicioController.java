package com.vehiculos.VehiculosRest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class InicioController {


    private String version = "1.0";
    @GetMapping("/")
    public String comienzo(){
        Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        return "API funcionando  -  Version " + version   +  " -   " + anio ;
    }

}
