package com.telecom.telecom_app.model;

import jakarta.persistence.*;

@Entity
@Table(name="clientes")
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nombre;
    private String direccion;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "barrio_id")
    private Barrio barrio;

    public Long getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public Barrio getBarrio() { return barrio; }

    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setBarrio(Barrio barrio) { this.barrio = barrio; }
}
