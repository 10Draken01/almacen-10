package com.draken.almacen.mappers;

import com.draken.almacen.dto.sucursales.SucursalRequest;
import com.draken.almacen.dto.sucursales.SucursalResponse;
import com.draken.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {
    public SucursalResponse entidadAResponse(Sucursal sucursal){
        return sucursal == null
                ? null
                : new SucursalResponse(
                        sucursal.getId(),
                        sucursal.getNombre(),
                        sucursal.getDireccion()
                );
    }

    public Sucursal requestAEntidad(SucursalRequest request){
        return request == null
                ? null
                : Sucursal.crear(
                        request.nombre(),
                        request.direccion()
                );
    }
}
