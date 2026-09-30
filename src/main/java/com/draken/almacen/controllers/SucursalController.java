package com.draken.almacen.controllers;

import com.draken.almacen.docs.ProblemaDoc;
import com.draken.almacen.dto.sucursales.SucursalRequest;
import com.draken.almacen.dto.sucursales.SucursalResponse;
import com.draken.almacen.entities.Sucursal;
import com.draken.almacen.exceptions.ConflictoException;
import com.draken.almacen.repositories.SucursalRepository;
import com.draken.almacen.services.sucursales.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "Gestion de sucursales")
@ApiResponse(
        responseCode = "400",
        description = "Datos o parametros invalidos",
        content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemaDoc.class
                )
        )
)
@ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemaDoc.class
                )
        )
)
public class SucursalController {
    private final SucursalService sucursalService;

    @GetMapping
    @Operation(
            summary = "Listar sucursales",
            description = "Todos los filtros son opcionales"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido"
    )
    public ResponseEntity<List<SucursalResponse>> listar(
            @Parameter(description = "Nombre de la sucursal", example = "Abarrotes Chica")
            @RequestParam(required = false) String nombre,
            @Parameter(description = "Direccion de la sucursal", example = "PRIVADA UNIÓN 10, COL. AGRÍCOLA PANTITLÁN, IZTACALCO, 08100, CIUDAD DE MÉXICO, MÉXICO.")
            @RequestParam(required = false) String direccion
    ) {
        return ResponseEntity.ok(sucursalService.listar(nombre, direccion));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una sucursal por id",
            description = "Asignar el identificador de la sucursal"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sucursal obtenida"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada"
    )
    public ResponseEntity<SucursalResponse> obtenerPorId(
            @Parameter(description = "Identificador de la sucursal", example = "1")
            @PathVariable @Positive(message = "El identificador debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar una sucursal"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Sucursal creada"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con los nuevos datos"
    )
    public ResponseEntity<SucursalResponse> registrar(
            @Valid @RequestBody SucursalRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sucursalService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Registrar una sucursal con su id",
            description = "Asignar el identificador de la sucursal"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sucursal actualizada"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con los nuevos datos"
    )
    public ResponseEntity<SucursalResponse> actualizar(
            @Parameter(description = "Identificador de la sucursal", example = "1")
            @PathVariable @Positive(message = "El identificador debe ser positivo")  Long id,
            @Valid @RequestBody SucursalRequest request
    ) {
        return ResponseEntity.ok(sucursalService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Registrar una sucursal con su id",
            description = "Asignar el identificador de la sucursal"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Sucursal eliminada correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con los nuevos datos"
    )
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Identificador de la sucursal", example = "1")
            @PathVariable @Positive(message = "El identificador debe ser positivo")  Long id
    ) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
