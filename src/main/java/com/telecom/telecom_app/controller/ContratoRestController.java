package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.Contrato;
import com.telecom.telecom_app.repository.ContratoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
@Tag(name = "Contratos API", description = "API REST para gestionar contratos de clientes")
public class ContratoRestController {

    private final ContratoRepository contratoRepository;

    public ContratoRestController(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todos los contratos", description = "Obtiene la lista completa de todos los contratos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de contratos obtenida exitosamente")
    public ResponseEntity<List<Contrato>> listarTodos() {
        List<Contrato> contratos = contratoRepository.findAll();
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener contrato por ID", description = "Busca un contrato específico utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contrato encontrado"),
            @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    public ResponseEntity<Contrato> obtenerPorId(
            @PathVariable @Parameter(description = "ID del contrato a buscar") Long id) {
        return contratoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo contrato", description = "Crea un nuevo contrato en la base de datos")
    @ApiResponse(responseCode = "201", description = "Contrato creado exitosamente")
    public ResponseEntity<Contrato> crear(@RequestBody Contrato contrato) {
        Contrato contratoGuardado = contratoRepository.save(contrato);
        return ResponseEntity.status(201).body(contratoGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar contrato", description = "Actualiza los datos de un contrato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contrato actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    public ResponseEntity<Contrato> actualizar(
            @PathVariable @Parameter(description = "ID del contrato a actualizar") Long id,
            @RequestBody Contrato contratoActualizado) {
        return contratoRepository.findById(id)
                .map(contrato -> {
                    contrato.setFechaInicio(contratoActualizado.getFechaInicio());
                    contrato.setFechaFin(contratoActualizado.getFechaFin());
                    contrato.setEstado(contratoActualizado.getEstado());
                    contrato.setCliente(contratoActualizado.getCliente());
                    contrato.setPlan(contratoActualizado.getPlan());
                    contrato.setVendedor(contratoActualizado.getVendedor());
                    return ResponseEntity.ok(contratoRepository.save(contrato));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar contrato", description = "Elimina un contrato de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contrato eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable @Parameter(description = "ID del contrato a eliminar") Long id) {
        if (contratoRepository.existsById(id)) {
            contratoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
