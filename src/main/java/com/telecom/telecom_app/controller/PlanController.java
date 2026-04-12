package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.Plan;
import com.telecom.telecom_app.repository.PlanRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/planes")
@Tag(name = "Planes", description = "Operaciones CRUD para planes de telecomunicaciones")
public class PlanController {

    private final PlanRepository repo;

    public PlanController(PlanRepository repo) {
        this.repo = repo;
    }

    @Operation(summary = "Listar todos los planes", description = "Obtiene la lista de todos los planes disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de planes obtenida exitosamente")
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("planes", repo.findAll());
        return "planes/list";
    }

    @Operation(summary = "Mostrar formulario nuevo", description = "Retorna el formulario para crear un nuevo plan")
    @ApiResponse(responseCode = "200", description = "Formulario cargado exitosamente")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("plan", new Plan());
        return "planes/form";
    }

    @Operation(summary = "Guardar plan", description = "Crea o actualiza un plan")
    @ApiResponse(responseCode = "200", description = "Plan guardado exitosamente")
    @PostMapping
    public String guardar(@ModelAttribute Plan plan) {
        repo.save(plan);
        return "redirect:/planes";
    }

    @Operation(summary = "Editar plan", description = "Carga el formulario con los datos del plan para editar")
    @ApiResponse(responseCode = "200", description = "Plan cargado para edición")
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable @Parameter(description = "ID del plan") Long id, Model model) {
        model.addAttribute("plan", repo.findById(id).orElseThrow());
        return "planes/form";
    }

    @Operation(summary = "Eliminar plan", description = "Elimina un plan por su ID")
    @ApiResponse(responseCode = "200", description = "Plan eliminado exitosamente")
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable @Parameter(description = "ID del plan") Long id) {
        repo.deleteById(id);
        return "redirect:/planes";
    }

    @Operation(summary = "Cambiar estado del plan", description = "Activa o desactiva un plan")
    @ApiResponse(responseCode = "200", description = "Estado del plan actualizado")
    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable @Parameter(description = "ID del plan") Long id) {
        Plan plan = repo.findById(id).orElseThrow();
        plan.setActivo(!plan.isActivo());
        repo.save(plan);
        return "redirect:/planes";
    }
}