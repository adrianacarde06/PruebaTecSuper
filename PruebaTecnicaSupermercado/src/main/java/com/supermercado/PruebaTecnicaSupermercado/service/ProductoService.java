package com.supermercado.PruebaTecnicaSupermercado.service;

import com.supermercado.PruebaTecnicaSupermercado.dto.ProductoDTO;
import com.supermercado.PruebaTecnicaSupermercado.exception.NotFoundException;
import com.supermercado.PruebaTecnicaSupermercado.mapper.Mapper;
import com.supermercado.PruebaTecnicaSupermercado.model.Producto;
import com.supermercado.PruebaTecnicaSupermercado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> traerProductos() {
        return productoRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        var prod = Producto.builder()
                .nombre(productoDTO.getNombre())
                .categoria(productoDTO.getCategoria())
                .precio(productoDTO.getPrecio())
                .cantidad(productoDTO.getCantidad())
                .build();
        return Mapper.toDTO(productoRepository.save(prod));
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto prod = productoRepository.findById(id).orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        prod.setNombre(productoDTO.getNombre());
        prod.setCategoria(productoDTO.getCategoria());
        prod.setPrecio(productoDTO.getPrecio());
        prod.setCantidad(productoDTO.getCantidad());
        return Mapper.toDTO(productoRepository.save(prod));
    }

    @Override
    public void eliminarProducto(Long id) {
        if(!productoRepository.existsById(id))
            throw new NotFoundException("Producto no encontrado para eliminar");

        productoRepository.deleteById(id);
    }
}
