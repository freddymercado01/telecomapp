package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.model.Barrio;
import com.telecom.telecom_app.service.BarrioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/barrios")
@Tag(name = "Barrios", description = "Operaciones CRUD para barrios y cobertura")
public class BarrioController {

    private final BarrioService barrioService;

    public BarrioController(BarrioService barrioService) {
        this.barrioService = barrioService;
    }

    @Operation(summary = "Listar barrios", description = "Obtiene la lista de barrios y su estado de cobertura")
    @ApiResponse(responseCode = "200", description = "Lista de barrios obtenida exitosamente")
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("barrios", barrioService.listarTodos());
        return "barrios/list";
    }

    @Operation(summary = "Mostrar formulario nuevo", description = "Retorna el formulario para crear un barrio")
    @ApiResponse(responseCode = "200", description = "Formulario cargado exitosamente")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("barrio", new Barrio());
        return "barrios/form";
    }

    @Operation(summary = "Guardar barrio", description = "Crea o actualiza un barrio")
    @ApiResponse(responseCode = "200", description = "Barrio guardado exitosamente")
    @PostMapping
    public String guardar(@ModelAttribute Barrio barrio) {
        barrioService.guardar(barrio);
        return "redirect:/barrios";
    }

    @Operation(summary = "Editar barrio", description = "Carga el formulario con los datos del barrio")
    @ApiResponse(responseCode = "200", description = "Barrio cargado para edicion")
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable @Parameter(description = "ID del barrio") Long id, Model model) {
        model.addAttribute("barrio", barrioService.obtenerPorId(id));
        return "barrios/form";
    }

    @Operation(summary = "Eliminar barrio", description = "Elimina un barrio por su ID")
    @ApiResponse(responseCode = "200", description = "Barrio eliminado exitosamente")
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable @Parameter(description = "ID del barrio") Long id,
                           RedirectAttributes redirectAttributes) {
        try {
            barrioService.eliminar(id);
        } catch (ResourceInUseException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/barrios";
    }
}
