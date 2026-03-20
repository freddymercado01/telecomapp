package com.telecom.telecom_app.controller;
import com.telecom.telecom_app.model.Plan;
import com.telecom.telecom_app.repository.PlanRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/planes")
public class PlanController {

    private final PlanRepository repo;

    public PlanController(PlanRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("planes", repo.findAll());
        return "planes/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("plan", new Plan());
        return "planes/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Plan plan) {
        repo.save(plan);
        return "redirect:/planes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("plan", repo.findById(id).orElseThrow());
        return "planes/form";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/planes";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        Plan plan = repo.findById(id).orElseThrow();
        plan.setActivo(!plan.isActivo());
        repo.save(plan);
        return "redirect:/planes";
    }
}