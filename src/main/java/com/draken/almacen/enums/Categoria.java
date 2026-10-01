package com.draken.almacen.enums;

import com.draken.almacen.exceptions.DatoInvalidoException;
import com.draken.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Categoria {
    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA("Electronica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia");

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");
        String descripcionNormalizada = StringCustomUtils.normalizarTexto(descripcion);

        for ( Categoria categoria : values()) {
            if (StringCustomUtils.normalizarTexto(categoria.descripcion).equals(descripcionNormalizada))
                return  categoria;
        }

        throw new DatoInvalidoException("No existe una categoria con la descripcion: " + descripcion);
    }
    }
