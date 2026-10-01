package com.draken.almacen.repositories;

import com.draken.almacen.entities.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    //  SELECT COUNT(*) FROM SUCURSALES WHERE LOWER(NOMBRE) = LOWER(?);
    boolean existsByNombreIgnoreCase(String nombre);

    //  SELECT COUNT(*) FROM SUCURSALES WHERE LOWER(NOMBRE) = LOWER(?) AND ID_SUCURSAL <> ?;
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    @Query("""
        SELECT s
        FROM Sucursal s
        WHERE (:nombre IS NULL 
            OR LOWER(s.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
        AND (:direccion IS NULL
            OR LOWER(s.direccion) LIKE LOWER(CONCAT('%', :direccion, '%')))
    """)
    List<Sucursal> listarConFiltro(
            @Param("nombre") String nombre,
            @Param("direccion") String direccion
    );
}
