package br.com.elo7.sonda.marcos.service;

import br.com.elo7.sonda.marcos.dto.PlanetDTO;
import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.persistence.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PlanetServiceIntegrationTest {
    @Autowired
    private PlanetService planetService;

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    void createPlanet() {
        PlanetDTO planetDTO = new PlanetDTO(5, 5);
        Planet planet = planetService.create(planetDTO);

        assertThat(planet.getId()).isNotNull();
        assertThat(planet.getHeight()).isEqualTo(5);
        assertThat(planet.getWidth()).isEqualTo(5);
    }

    @Test
    void notCreatePlanetWithNegativeWidth() {
        PlanetDTO planetDTO = new PlanetDTO(-1, 5);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Planet planet = planetService.create(planetDTO);
        });

    }

    @Test
    void notCreatePlanetWithNegativeHeight() {
        PlanetDTO planetDTO = new PlanetDTO(5, -1);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Planet planet = planetService.create(planetDTO);
        });
    }

    @Test
    void testGetById() {
        PlanetDTO planetDTO = new PlanetDTO(5, 5);
        Planet planet = planetService.create(planetDTO);

        Optional<Planet> optionalPlanet = planetService.getById(planet.getId());

        assertThat(optionalPlanet).isPresent();
        assertThat(optionalPlanet.get().getId()).isEqualTo(planet.getId());
        assertThat(optionalPlanet.get().getHeight()).isEqualTo(planet.getHeight());
        assertThat(optionalPlanet.get().getWidth()).isEqualTo(planet.getWidth());
    }
}