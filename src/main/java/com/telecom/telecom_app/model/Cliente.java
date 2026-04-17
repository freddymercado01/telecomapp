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

    public Long getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }

    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
