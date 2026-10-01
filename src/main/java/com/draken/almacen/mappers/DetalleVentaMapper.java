package com.draken.almacen.mappers;

import com.draken.almacen.dto.ventas.DetalleVentaRequest;
import com.draken.almacen.dto.ventas.DetalleVentaResponse;
import com.draken.almacen.entities.DetalleVenta;
import com.draken.almacen.entities.Producto;
import com.draken.almacen.entities.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DetalleVentaMapper {
    public DetalleVentaResponse entidadAResponse(DetalleVenta detalleVenta){

        return detalleVenta == null ? null
            : new DetalleVentaResponse(
                detalleVenta.getProducto().getId(),
                detalleVenta.getProducto().getNombre(),
                detalleVenta.getCantidadProducto(),
                detalleVenta.getPrecioProducto(),
                detalleVenta.obtenerSubTotal()
        );
    }
}
