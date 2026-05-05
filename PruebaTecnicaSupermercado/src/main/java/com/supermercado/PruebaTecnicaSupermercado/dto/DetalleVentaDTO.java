package com.supermercado.PruebaTecnicaSupermercado.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {

    private Long id;

    private String nombreProducto;

    private Integer cantidadProducto;

    private Double precio;

    private Double subTotal;

}
