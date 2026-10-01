package com.draken.almacen.mappers;

import com.draken.almacen.dto.ventas.VentaResponse;
import com.draken.almacen.entities.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VentaMapper {
    private final DetalleVentaMapper detalleVentaMapper;
    private final SucursalMapper sucursalMapper;

    public VentaResponse entidadAResponse(Venta venta){

        return venta == null
                ? null
                : new VentaResponse(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getEstadoVenta().getDescripcion(),
                sucursalMapper.entidadAResponse(venta.getSucursal()),
                venta.getDetalleVentas().stream()
                        .map(detalleVentaMapper::entidadAResponse).toList(),
                venta.obtenerTotalVenta()
        );
    }
}
