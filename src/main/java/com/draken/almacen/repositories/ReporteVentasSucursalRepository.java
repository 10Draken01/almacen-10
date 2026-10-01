package com.draken.almacen.repositories;

import com.draken.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.draken.almacen.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteVentasSucursalRepository extends JpaRepository<Venta, Long> {

    // Elegi JPQL ya que proporciona las herramientas suficientes para cumplir
    // el requerimiento sin anadir complejidad extra
    @Query(value = """
        SELECT new com.draken.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse(
                v.sucursal.id,
                v.sucursal.nombre,
                SUM(d.cantidadProducto * d.precioProducto),
                SUM(d.cantidadProducto)
            )
        FROM Venta v
        JOIN v.detalleVentas d
        WHERE v.estadoVenta = com.draken.almacen.enums.EstadoVenta.REGISTRADA
        GROUP BY v.sucursal.id, v.sucursal.nombre
    """)
    List<ReporteVentasSucursalResponse> obtenerReportes();
}
