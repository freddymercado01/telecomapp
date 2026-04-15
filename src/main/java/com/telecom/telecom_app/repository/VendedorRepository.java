package com.telecom.telecom_app.repository;

import com.telecom.telecom_app.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    boolean existsByUsuario_Id(Long idUsuario);
}
