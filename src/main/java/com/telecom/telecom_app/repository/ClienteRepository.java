package com.telecom.telecom_app.repository;

import com.telecom.telecom_app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
