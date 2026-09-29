package com.draken.almacen.utils;

import com.draken.almacen.entities.Producto;
import com.draken.almacen.enums.Categoria;
import com.draken.almacen.repositories.ProductoRepository;
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
        }
    }
}
