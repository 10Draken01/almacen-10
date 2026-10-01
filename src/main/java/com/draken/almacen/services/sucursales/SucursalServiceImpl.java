package com.draken.almacen.services.sucursales;

import com.draken.almacen.dto.sucursales.SucursalRequest;
import com.draken.almacen.dto.sucursales.SucursalResponse;
import com.draken.almacen.entities.Sucursal;
import com.draken.almacen.exceptions.ConflictoException;
import com.draken.almacen.exceptions.RecursoNoEncontradoException;
import com.draken.almacen.mappers.SucursalMapper;
import com.draken.almacen.repositories.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService{
    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    @Override
    public List<SucursalResponse> listarConFiltroOpcional(String nombre, String direccion) {
        log.info("Listando todos las sucursales");
        return sucursalRepository.listarConFiltro(nombre, direccion).stream()
                .map(sucursalMapper::entidadAResponse).toList();
    }

    @Override
    public SucursalResponse obtenerPorId(Long id) {
        return sucursalMapper.entidadAResponse(obtenerSucursalOException(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {

        validarDatosUnicos(request);

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);
        log.info("Registrando sucursal: {}", sucursal.getNombre());

        sucursalRepository.save(sucursal);

        log.info("Sucursal registrada: {}", sucursal.getNombre());
        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {

        Sucursal sucursal = obtenerSucursalOException(id);

        validarCambiosUnicos(request, id);

        log.info("Actualizando sucursal con combre: {}", sucursal.getNombre());

        sucursalRepository.saveAndFlush(sucursal);

        log.info("Sucursal actualizada con combre: {}", sucursal.getNombre());
        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Eliminando sucursal {} con id: {} eliminado correctamete", sucursal.getNombre(), sucursal.getId());

        sucursalRepository.delete(sucursal);
        sucursalRepository.flush();

        log.info("Sucursal {} con id: {} eliminado correctamete", sucursal.getNombre(), sucursal.getId());
    }

    private Sucursal obtenerSucursalOException(Long id){
        log.info("Obteniendo sucursal con id: {}", id);
        return sucursalRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Sucursal no encontrada con id: " + id));
    }

    private void validarDatosUnicos(SucursalRequest request){
        log.info("Validando nombre unico...");

        if(sucursalRepository.existsByNombreIgnoreCase(request.nombre()))
            throw new ConflictoException("Sucursal ya existe con nombre: " + request.nombre());

    }

    private void validarCambiosUnicos(SucursalRequest request, Long id){
        log.info("Validando cambio de nombre unico...");

        if(sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id))
            throw new ConflictoException("Ya existe una sucursal con el nombre: " + request.nombre());

    }
}
