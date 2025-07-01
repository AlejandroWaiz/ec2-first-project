/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vehiculos.VehiculosRest.repositories;

import com.vehiculos.VehiculosRest.models.VehiculoModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Villacura
 */
public interface IVehiculoRepository extends JpaRepository<VehiculoModel,Long>{
    
}
