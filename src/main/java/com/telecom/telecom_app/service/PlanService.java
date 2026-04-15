package com.telecom.telecom_app.service;

import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.model.Plan;
import com.telecom.telecom_app.repository.ContratoRepository;
import com.telecom.telecom_app.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final ContratoRepository contratoRepository;

    public PlanService(PlanRepository planRepository, ContratoRepository contratoRepository) {
        this.planRepository = planRepository;
        this.contratoRepository = contratoRepository;
    }

    public List<Plan> listarTodos() {
        return planRepository.findAll();
    }

    public Plan obtenerPorId(Long id) {
        return planRepository.findById(id).orElseThrow();
    }

    public Plan guardar(Plan plan) {
        return planRepository.save(plan);
    }

    public Plan actualizar(Long id, Plan planActualizado) {
        Plan plan = obtenerPorId(id);
        plan.setNombre(planActualizado.getNombre());
        plan.setTipoServicio(planActualizado.getTipoServicio());
        plan.setPrecioMensual(planActualizado.getPrecioMensual());
        plan.setDescripcion(planActualizado.getDescripcion());
        plan.setActivo(planActualizado.isActivo());
        return planRepository.save(plan);
    }

    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new java.util.NoSuchElementException();
        }
        if (contratoRepository.existsByPlan_IdPlan(id)) {
            throw new ResourceInUseException("El plan esta asociado a contratos");
        }
        planRepository.deleteById(id);
    }

    public Plan alternarEstado(Long id) {
        Plan plan = obtenerPorId(id);
        plan.setActivo(!plan.isActivo());
        return planRepository.save(plan);
    }
}
