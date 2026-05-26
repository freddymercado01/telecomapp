package com.telecom.telecom_app.repository;

import com.telecom.telecom_app.model.Contrato;
import com.telecom.telecom_app.model.EstadoContrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    boolean existsByCliente_IdCliente(Long idCliente);
    boolean existsByPlan_IdPlan(Long idPlan);
    boolean existsByVendedor_IdVendedor(Long idVendedor);
    List<Contrato> findByEstado(EstadoContrato estado);
}

