package com.telecom.telecom_app.repository;

import com.telecom.telecom_app.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    void deleteByContrato_IdContrato(Long idContrato);
}
