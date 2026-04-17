package com.telecom.telecom_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vendedores")
public class Vendedor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVendedor;

    private String nombre;
    private String telefono;
    private String email;

    // opcional: asociarlo al usuario del sistema
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Long getIdVendedor() { return idVendedor; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public Usuario getUsuario() { return usuario; }

    public void setIdVendedor(Long idVendedor) { this.idVendedor = idVendedor; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
