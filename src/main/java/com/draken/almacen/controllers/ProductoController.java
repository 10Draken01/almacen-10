package com.draken.almacen.controllers;

import com.draken.almacen.docs.ProblemaDoc;
import com.draken.almacen.dto.productos.ProductoRequest;
import com.draken.almacen.dto.productos.ProductoResponse;
import com.draken.almacen.services.productos.ProductoService;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestion del inventario de productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(
            summary = "Listar productos",
            description = "Todos los filtros son opcionales"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido"
    )
    public ResponseEntity<List<ProductoResponse>> listar(
            @Parameter(description = "Busqueda por nombre", example = "Laptop")
            @RequestParam(required = false) String nombre,
            @Parameter(description = "Filtro por categoria", example = "Electronica")
            @RequestParam(required = false) String categoria,
            @Parameter(description = "Precio minimo", example = "10000.0")
            @RequestParam(required = false) BigDecimal precioMin,
            @Parameter(description = "Precio maximo", example = "20000.0")
            @RequestParam(required = false) BigDecimal preicoMax
    ) {
        return ResponseEntity.ok(productoService.listar(nombre, categoria,precioMin, preicoMax));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener producto por su identificador",
            description = "Asignar el identificador del producto"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    public ResponseEntity<ProductoResponse> obtenerPorId(
            @Parameter(description = "Identificador del producto", example = "1")
            @PathVariable @Positive(message = "El identificador debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar un nuevo producto"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Producto creado"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con datos",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    public ResponseEntity<ProductoResponse> registrar(
            // Siempre usar valida para que valide el body en el dto
            @Valid  @RequestBody ProductoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un producto existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Producto no existe",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con los nuevos datos",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    public ResponseEntity<ProductoResponse> actualizar(
            @Parameter(description = "Identificador del producto", example = "1")
            @PathVariable @Positive(message = "El identificador debe ser positivo") Long id,
            @Valid  @RequestBody ProductoRequest request
    ) {
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un producto"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Producto elminado correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Producto no existe",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "El producto esta en uso y no puede eliminarse",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Identificador del producto", example = "1")
            @PathVariable Long id
    ) {
        productoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
