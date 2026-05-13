package com.telecom.telecom_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "barrios", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class Barrio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarrio;

    @Column(nullable = false)
    private String nombre;

    private boolean cobertura = false;

    private String descripcion;

    public Long getIdBarrio() {
        return idBarrio;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isCobertura() {
        return cobertura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdBarrio(Long idBarrio) {
        this.idBarrio = idBarrio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCobertura(boolean cobertura) {
        this.cobertura = cobertura;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
