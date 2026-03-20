package com.telecom.telecom_app.model;
import jakarta.persistence.*;

@Entity
@Table(name="infraestructuras")
public class Infraestructura {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccionInstalacion;
    private String nodo;
    private String equipoCPE;
    private String serialONT;
    private String ipWan;

    @Column(length = 1000)
    private String observaciones;

    public Long getId() { return id; }
    public String getDireccionInstalacion() { return direccionInstalacion; }
    public String getNodo() { return nodo; }
    public String getEquipoCPE() { return equipoCPE; }
    public String getSerialONT() { return serialONT; }
    public String getIpWan() { return ipWan; }
    public String getObservaciones() { return observaciones; }

    public void setDireccionInstalacion(String direccionInstalacion) { this.direccionInstalacion = direccionInstalacion; }
    public void setNodo(String nodo) { this.nodo = nodo; }
    public void setEquipoCPE(String equipoCPE) { this.equipoCPE = equipoCPE; }
    public void setSerialONT(String serialONT) { this.serialONT = serialONT; }
    public void setIpWan(String ipWan) { this.ipWan = ipWan; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}