package com.telecom.telecom_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthController {

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/up")
    @ResponseBody
    public ResponseEntity<String> up() {
        return ResponseEntity.ok("ok");
    }
}
