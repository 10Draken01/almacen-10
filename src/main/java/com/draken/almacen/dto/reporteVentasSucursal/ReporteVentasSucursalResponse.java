package com.draken.almacen.dto.reporteVentasSucursal;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Datos de un reporte de ventas de una sucursal")
public record ReporteVentasSucursalResponse(

        @Schema(
                description = "Identificador de la sucursal",
                example = "1"
        )
        Long idSucursal,

        @Schema(
                description = "Nombre de la sucursal",
                example = "Abarrotes Chica"
        )
        String nombreSucursal,

        @Schema(
                description = "Total de ventas de la sucursal",
                example = "19020.0"
        )
        BigDecimal totalFacturado,

        @Schema(
                description = "Cantidad de productos vendidos en la sucursal",
                example = "121"
        )
        Long cantidadProductosVendidos
) { }