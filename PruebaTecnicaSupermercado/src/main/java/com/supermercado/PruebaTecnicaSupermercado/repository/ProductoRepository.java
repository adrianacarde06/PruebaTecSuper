package com.supermercado.PruebaTecnicaSupermercado.repository;

import com.supermercado.PruebaTecnicaSupermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombre(String nombre);
}
