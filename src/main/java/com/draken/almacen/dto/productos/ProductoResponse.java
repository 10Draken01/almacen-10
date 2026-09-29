package com.draken.almacen.dto.productos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Informacion de un producto")
public record ProductoResponse(

        @Schema(
                description = "Identificador del producto",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nombre del producto",
                example = "Laptop Gamer"
        )
        String nombre,


        @Schema(
                description = "Categoria del producto",
                example = "Elctronica"
        )
        String categoria,

        @Schema(
                description = "Precio del producto",
                example = "20000.50"
        )
        BigDecimal precio,

        @Schema(
                description = "Cantidad disponible del producto",
                example = "121"
        )
        Integer cantidad
) {
}
