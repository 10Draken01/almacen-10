package com.draken.almacen.repositories;

import com.draken.almacen.entities.Venta;
import com.draken.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("""
        SELECT v
        FROM Venta v
        WHERE (:estadoVenta IS NULL 
            OR v.estadoVenta = :estadoVenta)
    """)
    List<Venta> findAllConFiltroOpcional(
            @Param("estadoVenta") EstadoVenta estadoVenta
    );
}
