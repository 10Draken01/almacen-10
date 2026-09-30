package com.draken.almacen.utils;

import com.draken.almacen.entities.Producto;
import com.draken.almacen.entities.Sucursal;
import com.draken.almacen.enums.Categoria;
import com.draken.almacen.repositories.ProductoRepository;
import com.draken.almacen.repositories.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor // constructor con la dependencias obligatorias final o not null
public class DatosIniciales implements CommandLineRunner {
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0){
            productoRepository.saveAll(List.of(
                    new Producto(
                            null,
                            "Laptop Gamer",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(1500),
                            10
                    ),
                    new Producto(
                            null,
                            "Mouse Inalambrico",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(100),
                            15
                    ),
                    new Producto(
                            null,
                            "Terreneitor",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(100000),
                            1
                    ),
                    new Producto(
                            null,
                            "Consola Gamer Ultimate Halo",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(99999),
                            99
                    ),
                    new Producto(
                            null,
                            "Anabel",
                            Categoria.JUGUETE,
                            BigDecimal.valueOf(666),
                            666
                    )
            ));

            log.info("Productos de prueba cargados correctamente");

            if (sucursalRepository.count() == 0) {
                sucursalRepository.saveAll(List.of(
                        new Sucursal(
                                null,
                                "Sucursal El Tamal Infinito",
                                "Calle del WiFi Perdido #404, Col. Los Memes"
                        ),
                        new Sucursal(
                                null,
                                "Sucursal La Última Conexión",
                                "Av. Lag del Servidor #69, Col. Respawn"
                        ),
                        new Sucursal(
                                null,
                                "Sucursal El Taco Dorado",
                                "Calle Taco Dorado #123, Col. La Salsa"
                        ),
                        new Sucursal(
                                null,
                                "Sucursal NullPointer",
                                "Boulevard NullPointer #500, Col. Stack Overflow"
                        ),
                        new Sucursal(
                                null,
                                "Sucursal El Bug Feliz",
                                "Calle Spring Boot #8080, Col. localhost"
                        )
                ));
            }

                log.info("Sucursales de prueba cargados correctamente");
        }
    }
}
