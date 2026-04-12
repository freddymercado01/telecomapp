package com.telecom.telecom_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Authentication", description = "Endpoints para autenticación")
public class AuthController {
    
    @Operation(summary = "Mostrar página de login", description = "Retorna la página de login del usuario")
    @ApiResponse(responseCode = "200", description = "Página de login cargada exitosamente")
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}