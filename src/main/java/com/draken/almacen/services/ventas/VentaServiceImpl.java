package com.draken.almacen.services.ventas;

import com.draken.almacen.dto.ventas.VentaRequest;
import com.draken.almacen.dto.ventas.VentaResponse;
import com.draken.almacen.entities.DetalleVenta;
import com.draken.almacen.entities.Producto;
import com.draken.almacen.entities.Sucursal;
import com.draken.almacen.entities.Venta;
import com.draken.almacen.enums.EstadoVenta;
import com.draken.almacen.exceptions.RecursoNoEncontradoException;
import com.draken.almacen.mappers.VentaMapper;
import com.draken.almacen.repositories.ProductoRepository;
import com.draken.almacen.repositories.SucursalRepository;
import com.draken.almacen.repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService{
    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<VentaResponse> listarConFiltroOpcionalActivo(String descripcion) {
        log.info("Listando ventas...");

        EstadoVenta estadoVenta = descripcion == null ? null
                : EstadoVenta.obtenerEstadoVentaPorDescripcion(descripcion);
        return ventaRepository.findAllConFiltroOpcional(estadoVenta).stream()
                .map( ventaMapper::entidadAResponse).toList();
    }

    @Override
    public VentaResponse obtenerPorId(Long id) {
        log.info("Obteniendo venta por id: {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(
                        () -> new RecursoNoEncontradoException("Venta no encontrada con id: " + id)
                );

        log.info("Venta con id: {} obtenida exitosamente", id);
        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    public VentaResponse registrarVenta(VentaRequest request) {
        Sucursal sucursal = obtenerSucursalOException(request.idSucursal());
        log.info("Registrando venta...");

        Venta venta = Venta.crear(
            sucursal
        );
        request.productos().forEach(p-> {
            Producto producto = obtenerProductoOException(p.idProducto());
            DetalleVenta detalleVenta = DetalleVenta.crear(
              producto,
              p.cantidadProducto()
            );
            venta.agregarDetalle(detalleVenta);
                });

        ventaRepository.save(venta);

        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    public VentaResponse cancelar(Long id) {
        Venta venta = obtenerVentaOException(id);
        venta.cancelarVenta();

        ventaRepository.save(venta);

        return ventaMapper.entidadAResponse(venta);
    }

    private Venta obtenerVentaOException(Long id){
        log.info("Obteniendo venta por id: {}", id);
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con id: " + id));
    }

    private Sucursal obtenerSucursalOException(Long id){
        log.info("Obteniendo sucursal con id: {}", id);
        return sucursalRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Sucursal no encontrada con id: " + id));
    }

    private Producto obtenerProductoOException(Long id) {
        log.info("Obteniendo producto con id: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }
}
