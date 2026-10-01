package com.draken.almacen.controllers;

import com.draken.almacen.docs.ProblemaDoc;
import com.draken.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.draken.almacen.services.reporteVentasSucursal.ReporteVentasSucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes-ventas")
@RequiredArgsConstructor
@Tag(name = "Reportes Ventas Sucursales", description = "Gestion de reportes de ventas en sucursales")
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
public class ReporteVentasSucursalController {

    private final ReporteVentasSucursalService reporteVentasSucursalService;

    @GetMapping
    @Operation(
            summary = "Listar reporte de ventas de cada sucursal"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Reportes listados"
    )
    public ResponseEntity<List<ReporteVentasSucursalResponse>> listarReporteVentasSucursal() {
        return ResponseEntity.ok(reporteVentasSucursalService.listarReporteVentasSucursal());
    }
}
