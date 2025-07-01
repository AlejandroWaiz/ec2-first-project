/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition
/**
 *
 * @author Villacura
 */
public class SwaggerConfigurations {
    
    
    public OpenAPI api(){
        return new OpenAPI();
    }
    
    
}
