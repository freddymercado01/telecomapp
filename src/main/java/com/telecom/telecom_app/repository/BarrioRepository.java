package com.telecom.telecom_app.repository;

import com.telecom.telecom_app.model.Barrio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarrioRepository extends JpaRepository<Barrio, Long> {
    Optional<Barrio> findByNombreIgnoreCase(String nombre);
}
