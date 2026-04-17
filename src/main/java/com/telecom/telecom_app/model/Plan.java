package com.telecom.telecom_app.model;
import jakarta.persistence.*;

@Entity
@Table(name="planes")
public class Plan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;

    private String nombre;
    private String tipoServicio;     // Voz / Conectividad
    private double precioMensual;
    private String descripcion;

    private boolean activo = true;

    public Long getIdPlan() { return idPlan; }
    public String getNombre() { return nombre; }
    public String getTipoServicio() { return tipoServicio; }
    public double getPrecioMensual() { return precioMensual; }
    public String getDescripcion() { return descripcion; }
    public boolean isActivo() { return activo; }

    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
    public void setPrecioMensual(double precioMensual) { this.precioMensual = precioMensual; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
