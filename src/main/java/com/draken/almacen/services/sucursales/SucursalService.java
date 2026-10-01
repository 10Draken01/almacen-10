package com.draken.almacen.services.sucursales;

import com.draken.almacen.dto.sucursales.SucursalRequest;
import com.draken.almacen.dto.sucursales.SucursalResponse;

import java.util.List;

public interface SucursalService {
    List<SucursalResponse> listarConFiltroOpcional(String nombre, String direccion);

    SucursalResponse obtenerPorId(Long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar(Long id);
}
