package com.draken.almacen.services.ventas;

import com.draken.almacen.dto.ventas.VentaRequest;
import com.draken.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {
    List<VentaResponse> listarConFiltroOpcionalActivo(String descripcion);

    VentaResponse obtenerPorId(Long id);

    VentaResponse registrarVenta(VentaRequest request);

    VentaResponse cancelar(Long id);
}
