package com.draken.almacen.repositories;

import com.draken.almacen.entities.Producto;
import com.draken.almacen.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("""
        SELECT p
        FROM Producto p
        WHERE (:nombre IS NULL 
            OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
        AND (:categoria IS NULL
                   OR p.categoria = :categoria)
        AND (:precioMin IS NULL 
            OR p.precio >= :precioMin)
        AND (:preicoMax IS NULL 
            OR p.precio <= :preicoMax)
    """)
    List<Producto> listarConFiltro(
        @Param("nombre") String nombre,
        @Param("categoria") Categoria categoria,
        @Param("precioMin") BigDecimal precioMin,
        @Param("preicoMax") BigDecimal preicoMax
    );
}
