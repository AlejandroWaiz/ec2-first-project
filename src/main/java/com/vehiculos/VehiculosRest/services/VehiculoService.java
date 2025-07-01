/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vehiculos.VehiculosRest.services;
import com.vehiculos.VehiculosRest.models.VehiculoModel;
import com.vehiculos.VehiculosRest.repositories.IVehiculoRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Villacura
 */
@Service
public class VehiculoService {
    @Autowired
    IVehiculoRepository autoRepository;
    
    
    public ArrayList<VehiculoModel> getVehiculos(){
        return (ArrayList<VehiculoModel>)  autoRepository.findAll();
    }
        
        
        
    public VehiculoModel saveAuto(VehiculoModel auto){
        return autoRepository.save(auto);
    }

    public Optional<VehiculoModel> getbyId(Long id){
        return autoRepository.findById(id);
    }
    
    
        public VehiculoModel updateById(VehiculoModel request , Long id) {
        Optional<VehiculoModel> optionalAuto = autoRepository.findById(id);

        if (optionalAuto.isPresent()) {
            VehiculoModel auto = optionalAuto.get();
            auto.setMarca(request.getMarca());
            auto.setModelo(request.getModelo());
            auto.setAno(request.getAno());
            auto.setColor(request.getColor());
            auto.setTipo_combustible(request.getTipo_combustible());

            // Guardar los cambios en la base de datos
            autoRepository.save(auto);

            return auto;
        } else {
            throw new UserNotFoundException("El vehiculo que intenta eliminar no existe con el id " + id);
        }
    }

        
    public void deleteAuto(Long id) {
        Optional<VehiculoModel> optionalAuto = autoRepository.findById(id);

        if (optionalAuto.isPresent()) {
            try {
                autoRepository.deleteById(id);
            } catch (Exception e) {
                // Manejar cualquier excepción lanzada durante la eliminación
                throw new RuntimeException("El vehiculo que intenta eliminar no existe con el id " + id);
            }
        } else {
            throw new UserNotFoundException("El vehiculo que intenta eliminar no existe con el id " + id);
        }
    }
    
        
    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
        
    

}
