package com.draken.almacen.services.ventas;

import com.draken.almacen.dto.ventas.VentaRequest;
import com.draken.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {
    List<VentaResponse> listar(String nombre, String direccion);

    VentaResponse obtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest request);

    VentaResponse cancelar(Long id);
}
