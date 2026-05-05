package com.supermercado.PruebaTecnicaSupermercado.controller;

import com.supermercado.PruebaTecnicaSupermercado.dto.VentaDTO;
import com.supermercado.PruebaTecnicaSupermercado.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> traerVentas(){
        return ResponseEntity.ok(ventaService.traerVentas());
    }

    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaDTO VentaDTO){
        VentaDTO creado = ventaService.crearVenta(VentaDTO);
        return ResponseEntity.created(URI.create("/api/ventas" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Long id, @RequestBody VentaDTO VentaDTO){
        return ResponseEntity.ok(ventaService.actualizarVenta(id, VentaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id){
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
