package com.draken.almacen.entities;

import com.draken.almacen.enums.EstadoVenta;
import com.draken.almacen.exceptions.ConflictoException;
import com.draken.almacen.exceptions.DatoInvalidoException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private Long id;

    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "venta",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalle(DetalleVenta detalleVenta) {
        if (detalleVenta == null)
            throw new DatoInvalidoException(
                    "El detalle de venta no puede ser nulo"
            );

        if (this.detalleVentas.contains(detalleVenta))
            throw new ConflictoException(
                    "El detalle de venta ya se encuentra registrado"
            );

        detalleVenta
                .getProducto()
                .descontarCantidad(
                        detalleVenta.getCantidadProducto()
                );

        detalleVenta.asignarVenta(this);
        this.detalleVentas.add(detalleVenta);
    }

    public void cancelarVenta(){
        if (this.estadoVenta == EstadoVenta.CANCELADA)
            throw new ConflictoException(
                    "La venta ya se encuentra cancelada"
            );

        this.detalleVentas.forEach(d->
                d.getProducto().aumentarCantidad(d.getCantidadProducto())
        );
        this.estadoVenta = EstadoVenta.CANCELADA;
    }

    public BigDecimal obtenerTotalVenta(){
        return this.detalleVentas.stream()
                .map(d ->
                        d.getPrecioProducto().multiply(
                                BigDecimal.valueOf(d.getCantidadProducto())
                        )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Integer obtenerTotalProductos(){
        return this.detalleVentas.stream()
                .map(DetalleVenta::getCantidadProducto
                ).reduce(0, Integer::sum);
    }

    public static Venta crear(Sucursal sucursal) {
        if(sucursal == null)
            throw new DatoInvalidoException(
                    "La sucursal asociada a la venta es obligatoria"
            );

        return Venta.builder()
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .sucursal(sucursal)
                .build();
    }
}
