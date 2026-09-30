package com.draken.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para registrar o actualizar una sucursal")
public record SucursalRequest(
        @Schema(
                description = "Nombre de la sucursal",
                example = "Abarrotes Chica"
        )
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre debe contener entre 5 y 50 caracteres")
        String nombre,

        @Schema(
                description = "Direccion de la sucursal",
                example = "PRIVADA UNIÓN 10, COL. AGRÍCOLA PANTITLÁN, IZTACALCO, 08100, CIUDAD DE MÉXICO, MÉXICO."
        )
        @NotBlank(message = "La direccion es requerida")
        @Size(min = 50, max = 150, message = "La direccion debe contener entre 50 y 150 caracteres")
        String direccion
) {}