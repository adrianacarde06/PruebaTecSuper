package com.supermercado.PruebaTecnicaSupermercado.mapper;

import com.supermercado.PruebaTecnicaSupermercado.dto.DetalleVentaDTO;
import com.supermercado.PruebaTecnicaSupermercado.dto.ProductoDTO;
import com.supermercado.PruebaTecnicaSupermercado.dto.SucursalDTO;
import com.supermercado.PruebaTecnicaSupermercado.dto.VentaDTO;
import com.supermercado.PruebaTecnicaSupermercado.model.Producto;
import com.supermercado.PruebaTecnicaSupermercado.model.Sucursal;
import com.supermercado.PruebaTecnicaSupermercado.model.Venta;

import java.util.stream.Collectors;

public class Mapper {

    public static ProductoDTO toDTO(Producto p){
        if(p == null) return null;

        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .categoria(p.getCategoria())
                .precio(p.getPrecio())
                .cantidad(p.getCantidad())
                .build();
    }

    public static SucursalDTO toDTO(Sucursal s){
        if(s == null) return null;

        return SucursalDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .build();
    }

    public static VentaDTO toDTO(Venta v){
        if(v == null) return null;

        var detalle =v.getDetalle().stream().map(detalleVenta ->
                        DetalleVentaDTO.builder()
                                .id(detalleVenta.getId())
                                .cantidadProducto(detalleVenta.getCantidadProducto())
                                .nombreProducto(detalleVenta.getProducto().getNombre())
                                .precio(detalleVenta.getPrecio())
                                .subTotal(detalleVenta.getPrecio() * detalleVenta.getCantidadProducto())
                                .build()).toList();

        var total = detalle.stream()
                .map(DetalleVentaDTO:: getSubTotal)
                .reduce(0.0, Double::sum);

        return VentaDTO.builder()
                .id(v.getId())
                .fecha(v.getFecha())
                .estado(v.getEstado())
                .idSucursal(v.getSucursal().getId())
                .total(total)
                .detalle(detalle)
                .build();
    }


}
