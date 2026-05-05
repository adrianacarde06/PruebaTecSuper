package com.supermercado.PruebaTecnicaSupermercado.controller;

import com.supermercado.PruebaTecnicaSupermercado.dto.SucursalDTO;
import com.supermercado.PruebaTecnicaSupermercado.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private ISucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> traerSucursals(){
        return ResponseEntity.ok(sucursalService.traerSucursales());
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal(@RequestBody SucursalDTO SucursalDTO){
        SucursalDTO creado = sucursalService.crearSucursal(SucursalDTO);
        return ResponseEntity.created(URI.create("/api/sucursales" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizarSucursal(@PathVariable Long id, @RequestBody SucursalDTO SucursalDTO){
        return ResponseEntity.ok(sucursalService.actualizarSucursal(id, SucursalDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id){
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
