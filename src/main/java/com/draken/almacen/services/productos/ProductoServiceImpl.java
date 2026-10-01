package com.draken.almacen.services.productos;

import com.draken.almacen.dto.productos.ProductoRequest;
import com.draken.almacen.dto.productos.ProductoResponse;
import com.draken.almacen.entities.Producto;
import com.draken.almacen.enums.Categoria;
import com.draken.almacen.exceptions.RecursoNoEncontradoException;
import com.draken.almacen.mappers.ProductoMapper;
import com.draken.almacen.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor // Implementa constructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String descripcion, BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Listando todos los productos");
        Categoria categoriaValidada = descripcion == null ? null
            : Categoria.obtenerCategoriaPorDescripcion(descripcion);
        return productoRepository.listarConFiltro(nombre, categoriaValidada, precioMin, precioMax).stream()
                .map(productoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return productoMapper.entidadAResponse(obtenerProductoOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {

        log.info("Registrando nuevo producto...");

        Producto producto = productoMapper.requestAEntidad(
                request,
                Categoria.obtenerCategoriaPorDescripcion(request.categoria())
        );

        productoRepository.save(producto);

        log.info("Registrando nuevo {} producto", producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        Producto producto = obtenerProductoOException(id);

        log.info("Actualizando producto {} con id: {}", producto.getNombre(), producto.getId());

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(
                        request.categoria().trim()
                ),
                request.precio(),
                request.cantidad()
        );

        productoRepository.saveAndFlush(producto);

        log.info("Producto {} con id: {} actualizado correctamente", producto.getNombre(), producto.getId());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoOException(id);

        log.info("Eliminando producto {} con id: {}", producto.getNombre(), producto.getId());

        productoRepository.delete(producto);
        // ejecuta la sentencia del orm inmediatamente
        productoRepository.flush();

        log.info("Producto {} con id: {} eliminado correctamete", producto.getNombre(), producto.getId());
    }

    private Producto obtenerProductoOException(Long id) {
        log.info("Obteniendo producto con id: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }
}
