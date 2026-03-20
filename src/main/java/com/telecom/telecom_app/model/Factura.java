package com.telecom.telecom_app.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="facturas")
public class Factura {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    private LocalDate fechaEmision;
    private double total;

    @Enumerated(EnumType.STRING)
    private EstadoFactura estado = EstadoFactura.PENDIENTE;

    @ManyToOne(optional = false)
    private Contrato contrato;

    public Long getIdFactura() { return idFactura; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public double getTotal() { return total; }
    public EstadoFactura getEstado() { return estado; }
    public Contrato getContrato() { return contrato; }

    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public void setTotal(double total) { this.total = total; }
    public void setEstado(EstadoFactura estado) { this.estado = estado; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }
}