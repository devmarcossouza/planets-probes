package br.com.elo7.sonda.marcos.service;

import br.com.elo7.sonda.marcos.dto.PlanetDTO;
import br.com.elo7.sonda.marcos.dto.PlanetResponseDTO;
import br.com.elo7.sonda.marcos.dto.ProbeResponseDTO;
import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.model.Probe;
import br.com.elo7.sonda.marcos.persistence.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public PlanetResponseDTO create(final PlanetDTO planetDTO) {
        Planet planet = new Planet(planetDTO.getHeight(), planetDTO.getWidth());
        planetRepository.save(planet);
        return convertEntityToDTO(planet);
    }
    public Optional<PlanetResponseDTO> getById(final Long id) {
        return planetRepository.findById(id).map(this::convertEntityToDTO);
    }

    public Optional<Planet> getByIdEntity(final Long id) {
        return planetRepository.findById(id);
    }

    private PlanetResponseDTO convertEntityToDTO(Planet planet) {
        return new PlanetResponseDTO(planet.getWidth(), planet.getHeight(), planet.getId());
    }
}
