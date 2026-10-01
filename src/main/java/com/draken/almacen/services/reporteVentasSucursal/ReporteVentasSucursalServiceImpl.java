package com.draken.almacen.services.reporteVentasSucursal;

import com.draken.almacen.dto.reporteVentasSucursal.ReporteVentasSucursalResponse;
import com.draken.almacen.repositories.ReporteVentasSucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReporteVentasSucursalServiceImpl implements ReporteVentasSucursalService{
    private final ReporteVentasSucursalRepository reporteVentasSucursalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasSucursalResponse> listarReporteVentasSucursal() {

        log.info("Listando reportes de ventas de sucursales...");

        return reporteVentasSucursalRepository.obtenerReportes();
    }

//    @Override
//    public List<ReporteVentasSucursalResponse> listarReporteVentasSucursal() {
//
//        log.info("Listando reportes de ventas de sucursales...");
//
//        return sucursalRepository.findAll().stream()
//                .map(sucursal -> {
//                    List<Venta> ventas = ventaRepository.findAllBySucursalAndEstadoVenta(
//                            sucursal,
//                            EstadoVenta.REGISTRADA
//                    );
//                    return new ReporteVentasSucursalResponse(
//                            sucursal.getId(),
//                            sucursal.getNombre(),
//                            ventas.stream()
//                                    .map(Venta::obtenerTotalVenta)
//                                    .reduce(BigDecimal.ZERO, BigDecimal::add),
//                            ventas.stream()
//                                    .map(Venta::obtenerTotalProductos)
//                                    .reduce(0, Integer::sum)
//                    );
//                }).toList();
//    }
}
