package com.telecom.telecom_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Dashboard", description = "Panel principal de la aplicación")
public class DashboardController {
    
    @Operation(summary = "Ver dashboard", description = "Muestra el panel principal con resumen de datos")
    @ApiResponse(responseCode = "200", description = "Dashboard cargado exitosamente")
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}