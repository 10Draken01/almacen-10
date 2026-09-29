package com.draken.almacen.mappers;

import com.draken.almacen.dto.productos.ProductoRequest;
import com.draken.almacen.dto.productos.ProductoResponse;
import com.draken.almacen.entities.Producto;
import com.draken.almacen.enums.Categoria;
import org.springframework.stereotype.Component;

@Component // clase gestionada por el contenedor de spring (Framework)
public class ProductoMapper {
    public Producto requestAEntidad(ProductoRequest request, Categoria categoria) {
        return request == null
                ? null
                : Producto.crear(
                request.nombre(),
                categoria,
                request.precio(),
                request.cantidad()
        );
    }

    public ProductoResponse entidadAResponse(Producto producto) {
        return producto == null
                ? null
                : new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria().getDescripcion(),
                producto.getPrecio(),
                producto.getCantidad()
        );
    }
}
