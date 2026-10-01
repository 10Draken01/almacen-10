package com.draken.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informacion de sucursal")
public record SucursalResponse(
        @Schema(
                description = "Identificador de la sucursal",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nombre de la sucursal",
                example = "Abarrotes Chica"
        )
        String nombre,


        @Schema(
                description = "Direccion de la sucursal",
                example = "PRIVADA UNIÓN 10, COL. AGRÍCOLA PANTITLÁN, IZTACALCO, 08100, CIUDAD DE MÉXICO, MÉXICO."
        )
        String direccion
) { }