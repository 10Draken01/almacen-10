package com.draken.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Detalle de un producto dentro de una venta")
public record DetalleVentaRequest(
        @Schema(description = "Identificador del producto", example = "1")
        @NotNull(message = "El identificador del producto es requerido")
        @Positive(message = "El identificador del producto debe ser positivo")
        Long idProducto,

        @Schema(description = "Cantidad del producto", example = "1")
        @NotNull(message = "La cantidad del producto es requerida")
        @Positive(message = "La cantidad del producto debe ser positiva")
        Integer cantidadProducto
) {
}
