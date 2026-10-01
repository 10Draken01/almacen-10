package com.draken.almacen.controllers;


import com.draken.almacen.docs.ProblemaDoc;
import com.draken.almacen.dto.ventas.VentaRequest;
import com.draken.almacen.dto.ventas.VentaResponse;
import com.draken.almacen.entities.DetalleVenta;
import com.draken.almacen.entities.Producto;
import com.draken.almacen.entities.Sucursal;
import com.draken.almacen.entities.Venta;
import com.draken.almacen.exceptions.RecursoNoEncontradoException;
import com.draken.almacen.services.ventas.VentaService;
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
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Tag(name = "Ventas", description = "Gestion de ventas")
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
public class VentaController {
    private final VentaService ventaService;

    @GetMapping
    @Operation(
            summary = "Listar ventas",
            description = "Todos los filtros son opcionales"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con la categoria inexistente"
    )
    public ResponseEntity<List<VentaResponse>> listarConFiltroOpcionalActivo(
            @Parameter(description = "Estado de la venta", example = "true")
            @RequestParam(required = false) String estadoVenta
    ) {
        return ResponseEntity.ok(ventaService.listarConFiltroOpcionalActivo(estadoVenta));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener venta con identificador",
            description = "Todos los filtros son opcionales"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Venta obtenida"
    )
    public ResponseEntity<VentaResponse> obtenerPorId(
            @Parameter(description = "Identificador de la venta", example = "1")
            @PathVariable @Positive(message = "El identificador debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar una venta"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Venta creada"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con los datos"
    )
    public ResponseEntity<VentaResponse> registrarVenta(
            @Valid @RequestBody VentaRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.registrarVenta(request));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Cancelar una sucursal con su id",
            description = "Asignar el identificador de la sucursal"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Sucursal cancelada correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con los nuevos datos"
    )
    public ResponseEntity<VentaResponse> cancelar(
            @Parameter(description = "Identificador de la venta", example = "1")
            @PathVariable @Positive(message = "El identificador debe ser positivo") Long id
    ) {
        ventaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
