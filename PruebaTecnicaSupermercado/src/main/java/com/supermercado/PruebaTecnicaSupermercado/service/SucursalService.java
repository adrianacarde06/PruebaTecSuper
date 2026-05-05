package com.supermercado.PruebaTecnicaSupermercado.service;

import com.supermercado.PruebaTecnicaSupermercado.dto.SucursalDTO;
import com.supermercado.PruebaTecnicaSupermercado.exception.NotFoundException;
import com.supermercado.PruebaTecnicaSupermercado.mapper.Mapper;
import com.supermercado.PruebaTecnicaSupermercado.model.Sucursal;
import com.supermercado.PruebaTecnicaSupermercado.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService implements ISucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<SucursalDTO> traerSucursales() {
        return sucursalRepository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public SucursalDTO crearSucursal(SucursalDTO sucursalDTO) {
        Sucursal sucursal = Sucursal.builder()
                .nombre(sucursalDTO.getNombre())
                .direccion(sucursalDTO.getDireccion())
                .build();
        return Mapper.toDTO(sucursalRepository.save(sucursal));
    }

    @Override
    public SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDTO) {
        Sucursal sucursal = sucursalRepository.findById(id).orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
        sucursal.setNombre(sucursalDTO.getNombre());
        sucursal.setDireccion(sucursalDTO.getDireccion());
        return Mapper.toDTO(sucursalRepository.save(sucursal));
    }

    @Override
    public void eliminarSucursal(Long id) {
        if(!sucursalRepository.existsById(id))
            throw new NotFoundException("Sucursal no encontrada para eliminar");
        sucursalRepository.deleteById(id);
    }
}
