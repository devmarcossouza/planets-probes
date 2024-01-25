package br.com.elo7.sonda.marcos.service;

import br.com.elo7.sonda.marcos.dto.PlanetDTO;
import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.persistence.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public Planet create(final PlanetDTO planetDTO) {
        Planet planet = new Planet(planetDTO.getHeight(), planetDTO.getWidth());
        planetRepository.save(planet);
        return planet;
    }
    public Optional<Planet> getById(final Long id) {
        return planetRepository.findById(id);
    }
}
