package com.draken.almacen.services.productos;


import com.draken.almacen.dto.productos.ProductoRequest;
import com.draken.almacen.dto.productos.ProductoResponse;

import java.math.BigDecimal;
import java.util.List;
// patron de diseno service layer
public interface ProductoService {
    List<ProductoResponse> listar(String nombre, String descripcion, BigDecimal precioMin, BigDecimal preicoMax);

    ProductoResponse obtenerPorId(Long id);

    ProductoResponse registrar(ProductoRequest request);

    ProductoResponse actualizar(ProductoRequest request, Long id);

    void eliminar(Long id);
}
