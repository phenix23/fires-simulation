package org.training.firessimulation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.firessimulation.model.Simulation;
import org.training.firessimulation.service.ForestFireService;

@RestController
@RequestMapping("/api/simulation")
public class ForestFireController {
    private final ForestFireService service;

    public ForestFireController(ForestFireService service) {
        this.service = service;
    }

    @GetMapping("/current")
    public Simulation getCurrentState() {
        return service.getCurrentState();
    }

    @GetMapping("/next")
    public Simulation nextStep() {
        return service.nextStep();
    }

    @GetMapping("/reset")
    public Simulation reset() {
        service.reset();
        return service.getCurrentState();
    }
}
