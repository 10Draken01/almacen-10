package com.draken.almacen.entities;

import com.draken.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "SUCURSALES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", length = 50, unique = true, nullable = false)
    private String nombre;

    @Column(name = "DIRECCION", length = 150, nullable = false)
    private String direccion;


    public static void validarDatos(String nombre, String direccion) {

        StringCustomUtils.validarTamanio(nombre, 5,50, "El nombre debe contener entre 5 y 50 caracteres");

        StringCustomUtils.validarTamanio(direccion, 50,150, "La direccion debe contener entre 50 y 150 caracteres");

    }

    public void actualizar(String nombre, String direccion) {

        validarDatos(nombre, direccion);

        this.nombre = nombre;
        this.direccion = direccion;

    }

    public static Sucursal crear(String nombre, String direccion) {
        validarDatos(nombre, direccion);

        return Sucursal.builder()
                .nombre(nombre.trim())
                .direccion(direccion.trim())
                .build();
    }
}