package br.com.elo7.sonda.marcos.controller;

import br.com.elo7.sonda.marcos.dto.PlanetDTO;
import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.service.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/planets")
public class PlanetController {
    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }
    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody @Validated PlanetDTO planet) {
        return ResponseEntity.ok(planetService.create(planet));
    }
    @GetMapping("{id}")
    public ResponseEntity<Planet> getById(@PathVariable Long id) {
        var planetOptional = planetService.getById(id);
        return planetOptional.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
