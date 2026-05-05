package com.supermercado.PruebaTecnicaSupermercado.service;

import com.supermercado.PruebaTecnicaSupermercado.dto.DetalleVentaDTO;
import com.supermercado.PruebaTecnicaSupermercado.dto.VentaDTO;
import com.supermercado.PruebaTecnicaSupermercado.exception.NotFoundException;
import com.supermercado.PruebaTecnicaSupermercado.mapper.Mapper;
import com.supermercado.PruebaTecnicaSupermercado.model.DetalleVenta;
import com.supermercado.PruebaTecnicaSupermercado.model.Producto;
import com.supermercado.PruebaTecnicaSupermercado.model.Sucursal;
import com.supermercado.PruebaTecnicaSupermercado.model.Venta;
import com.supermercado.PruebaTecnicaSupermercado.repository.ProductoRepository;
import com.supermercado.PruebaTecnicaSupermercado.repository.SucursalRepository;
import com.supermercado.PruebaTecnicaSupermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<VentaDTO> traerVentas() {
         List<Venta> ventas = ventaRepository.findAll();
         List<VentaDTO> ventasDTO = new ArrayList<>();
         VentaDTO ventaDTO;
         for (Venta v: ventas){
             ventaDTO = Mapper.toDTO(v);
             ventasDTO.add(ventaDTO);
         }
         return ventasDTO;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        if(ventaDTO == null)
            throw new RuntimeException("Venta is null");
        if(ventaDTO.getIdSucursal() == null)
            throw new RuntimeException("Debe indicar la sucursal");
        if(ventaDTO.getDetalle() == null || ventaDTO.getDetalle().isEmpty())
            throw new RuntimeException("Debe incluir al menos un producto");

        Sucursal sucursal = sucursalRepository.findById(ventaDTO.getIdSucursal()).orElse(null);
        if(sucursal == null)
            throw new NotFoundException("Sucursal no encontrada");

        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha());
        venta.setEstado(ventaDTO.getEstado());
        venta.setSucursal(sucursal);
        venta.setTotal(ventaDTO.getTotal());

        List<DetalleVenta> detalles = new ArrayList<>();
        Double totalCalculado = 0.0;
        for(DetalleVentaDTO detalleVentaDTO: ventaDTO.getDetalle()){
            Producto p = productoRepository.findByNombre(detalleVentaDTO.getNombreProducto()).orElse(null);
            if(p == null)
                throw new RuntimeException("Producto no encontrado: " + detalleVentaDTO.getNombreProducto());

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setProducto(p);
            detalleVenta.setCantidadProducto(detalleVentaDTO.getCantidadProducto());
            detalleVenta.setPrecio(detalleVentaDTO.getPrecio());
            detalleVenta.setVenta(venta);
            detalles.add(detalleVenta);
            totalCalculado = totalCalculado + (detalleVentaDTO.getCantidadProducto() * detalleVentaDTO.getPrecio());
        }
        venta.setDetalle(detalles);
        venta.setTotal(totalCalculado);
        venta = ventaRepository.save(venta);
        return Mapper.toDTO(venta);
    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        Venta venta = ventaRepository.findById(id).orElse(null);
        if(venta == null)
            throw new NotFoundException("Venta no encontrada");

        if(ventaDTO.getFecha() != null){
            venta.setFecha( ventaDTO.getFecha());
        }
        if(ventaDTO.getEstado() != null){
            venta.setEstado(ventaDTO.getEstado());
        }
        if(ventaDTO.getTotal() != null){
            venta.setTotal(ventaDTO.getTotal());
        }
        if(ventaDTO.getIdSucursal() != null){
            Sucursal sucursal = sucursalRepository.findById(ventaDTO.getIdSucursal()).orElse(null);
            if(sucursal == null)
                throw new NotFoundException("Sucursal no encontrada");
            venta.setSucursal(sucursal);
        }
        ventaRepository.save(venta);
        return Mapper.toDTO(venta);
    }

    @Override
    public void eliminarVenta(Long id) {
        Venta venta = ventaRepository.findById(id).orElse(null);
        if(venta == null)
            throw new NotFoundException("Venta no encontrada");
        ventaRepository.delete(venta);
    }
}
