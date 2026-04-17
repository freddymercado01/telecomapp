package com.telecom.telecom_app.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="contratos")
public class Contrato {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrato;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoContrato estado = EstadoContrato.ACTIVO;

    // Relaciones
    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    private Plan plan;

    @ManyToOne(optional = false)
    private Vendedor vendedor;

    @OneToOne(cascade = CascadeType.ALL)
    private Infraestructura infraestructura;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL)
    private List<Factura> facturas = new ArrayList<>();

    public Long getIdContrato() { return idContrato; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public EstadoContrato getEstado() { return estado; }
    public Cliente getCliente() { return cliente; }
    public Plan getPlan() { return plan; }
    public Vendedor getVendedor() { return vendedor; }
    public Infraestructura getInfraestructura() { return infraestructura; }
    public List<Factura> getFacturas() { return facturas; }

    public void setIdContrato(Long idContrato) { this.idContrato = idContrato; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public void setEstado(EstadoContrato estado) { this.estado = estado; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setPlan(Plan plan) { this.plan = plan; }
    public void setVendedor(Vendedor vendedor) { this.vendedor = vendedor; }
    public void setInfraestructura(Infraestructura infraestructura) { this.infraestructura = infraestructura; }
}
