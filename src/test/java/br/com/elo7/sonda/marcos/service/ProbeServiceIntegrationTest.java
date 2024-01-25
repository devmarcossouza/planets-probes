package br.com.elo7.sonda.marcos.service;

import br.com.elo7.sonda.marcos.dto.*;
import br.com.elo7.sonda.marcos.model.Direction;
import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.model.Probe;
import br.com.elo7.sonda.marcos.persistence.ProbeRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProbeServiceIntegrationTest {
    @Autowired
    private ProbeService probeService;

    @Autowired
    private ProbeRepository probeRepository;

    @Autowired
    private PlanetService planetService;

    @Test
    void testLandProbe() {
        PlanetDTO planetDTO = new PlanetDTO(5, 5);
        PlanetResponseDTO planet = planetService.create(planetDTO);

        ProbeDTO probeDTO = new ProbeDTO(1, 1, Direction.N, planet.getId());
        ProbeResponseDTO probe = probeService.landProbe(probeDTO);

        AssertionsForClassTypes.assertThat(probe.getId()).isNotNull();
        AssertionsForClassTypes.assertThat(probe.getX()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(probe.getY()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(probe.getDirection()).isEqualTo(Direction.N);
        AssertionsForClassTypes.assertThat(probe.getPlanetId()).isEqualTo(planet.getId());
    }

    @Test
    void testLandProbeNonExistPlanet() {
        ProbeDTO probeDTO = new ProbeDTO(1, 1, Direction.N, 1L);

        Throwable exception = assertThrows(NoResultException.class, () -> {
            ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        });

        String expectedMessage = "Planet not found!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLandProbeOutOfPlanetAreaX() {
        PlanetDTO planetDTO = new PlanetDTO(5, 5);
        PlanetResponseDTO planet = planetService.create(planetDTO);
        ProbeDTO probeDTO = new ProbeDTO(6, 5, Direction.N, planet.getId());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        });

        String expectedMessage = "X value not allowed!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLandProbeOutOfPlanetAreaY() {
        PlanetDTO planetDTO = new PlanetDTO(4, 4);
        PlanetResponseDTO planet = planetService.create(planetDTO);
        ProbeDTO probeDTO = new ProbeDTO(4, 5, Direction.N, planet.getId());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        });

        String expectedMessage = "Y value not allowed!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLandProbeWithNegativeX() {
        PlanetDTO planetDTO = new PlanetDTO(2, 2);
        PlanetResponseDTO planet = planetService.create(planetDTO);
        ProbeDTO probeDTO = new ProbeDTO(-1, 2, Direction.N, planet.getId());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        });

        String expectedMessage = "X value not allowed!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLandProbeWithNegativeY() {
        PlanetDTO planetDTO = new PlanetDTO(2, 2);
        PlanetResponseDTO planet = planetService.create(planetDTO);
        ProbeDTO probeDTO = new ProbeDTO(2, -1, Direction.N, planet.getId());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        });

        String expectedMessage = "Y value not allowed!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLandProbeWithoutPlanetSpaceAvailable() {
        PlanetDTO planetDTO = new PlanetDTO(1, 1);
        PlanetResponseDTO planet = planetService.create(planetDTO);
        ProbeDTO probeDTO = new ProbeDTO(1, 1, Direction.N, planet.getId());
        ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        ProbeDTO probeDTO2 = new ProbeDTO(1, 1, Direction.N, planet.getId());


        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ProbeResponseDTO probe2 = probeService.landProbe(probeDTO2);
        });

        String expectedMessage = "Is not possible land probe!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLandProbeInTheSameSpaceHaveAnotherProbe() {
        PlanetDTO planetDTO = new PlanetDTO(2, 2);
        PlanetResponseDTO planet = planetService.create(planetDTO);
        ProbeDTO probeDTO = new ProbeDTO(1, 1, Direction.N, planet.getId());
        ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        ProbeDTO probeDTO2 = new ProbeDTO(1, 1, Direction.N, planet.getId());


        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ProbeResponseDTO probe2 = probeService.landProbe(probeDTO2);
        });

        String expectedMessage = "Is not possible land probe!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testMoveProbe() {
        PlanetDTO planetDTO = new PlanetDTO(5, 5);
        PlanetResponseDTO planet = planetService.create(planetDTO);

        ProbeDTO probeDTO = new ProbeDTO(1, 1, Direction.N, planet.getId());
        ProbeResponseDTO probe = probeService.landProbe(probeDTO);

        MoveProbeDTO moveProbeDTO = new MoveProbeDTO(Arrays.asList(MoveCommandDTO.M, MoveCommandDTO.R, MoveCommandDTO.M,
                MoveCommandDTO.R, MoveCommandDTO.M, MoveCommandDTO.R, MoveCommandDTO.M, MoveCommandDTO.R));
        ProbeResponseDTO updatedProbe = probeService.moveProbe(probe.getId(), moveProbeDTO);

        AssertionsForClassTypes.assertThat(updatedProbe.getId()).isEqualTo(probe.getId());
        AssertionsForClassTypes.assertThat(updatedProbe.getX()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(updatedProbe.getY()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(updatedProbe.getDirection()).isEqualTo(Direction.N);
        AssertionsForClassTypes.assertThat(updatedProbe.getPlanetId()).isEqualTo(planet.getId());
    }

    @Test
    void testMoveProbeOutOfPlanet() {
        PlanetDTO planetDTO = new PlanetDTO(1, 1);
        PlanetResponseDTO planet = planetService.create(planetDTO);

        ProbeDTO probeDTO = new ProbeDTO(1, 1, Direction.N, planet.getId());
        ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        MoveProbeDTO moveProbeDTO = new MoveProbeDTO(Arrays.asList(MoveCommandDTO.M));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ProbeResponseDTO updatedProbe = probeService.moveProbe(probe.getId(), moveProbeDTO);
        });
        String expectedMessage = "Y value not allowed!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeleteById() {
        PlanetDTO planetDTO = new PlanetDTO(1, 1);
        PlanetResponseDTO planet = planetService.create(planetDTO);

        ProbeDTO probeDTO = new ProbeDTO(1, 1, Direction.N, planet.getId());
        ProbeResponseDTO probe = probeService.landProbe(probeDTO);
        probeService.deleteById(probe.getId());
        assertTrue(probeService.getById(probe.getId()).isEmpty());
    }

    @Test
    void testGetById() {
        PlanetDTO planetDTO = new PlanetDTO(1, 1);
        PlanetResponseDTO planet = planetService.create(planetDTO);

        ProbeDTO probeDTO = new ProbeDTO(1, 1, Direction.N, planet.getId());
        ProbeResponseDTO probe = probeService.landProbe(probeDTO);

        Optional<ProbeResponseDTO> optionalProbe = probeService.getById(probe.getId());

        AssertionsForClassTypes.assertThat(optionalProbe).isPresent();
        AssertionsForClassTypes.assertThat(optionalProbe.get().getId()).isEqualTo(probe.getId());
        AssertionsForClassTypes.assertThat(optionalProbe.get().getX()).isEqualTo(probe.getX());
        AssertionsForClassTypes.assertThat(optionalProbe.get().getY()).isEqualTo(probe.getY());
        AssertionsForClassTypes.assertThat(optionalProbe.get().getPlanetId()).isEqualTo(probe.getPlanetId());
        AssertionsForClassTypes.assertThat(optionalProbe.get().getDirection()).isEqualTo(probe.getDirection());
    }

    @Test
    void testGetProbesByPlanetId() {
        PlanetDTO planetDTO = new PlanetDTO(2, 2);
        PlanetResponseDTO planet = planetService.create(planetDTO);

        ProbeDTO probeDTO1 = new ProbeDTO(1, 1, Direction.N, planet.getId());
        ProbeResponseDTO probe1 = probeService.landProbe(probeDTO1);

        ProbeDTO probeDTO2 = new ProbeDTO(2, 2, Direction.S, planet.getId());
        ProbeResponseDTO probe2 = probeService.landProbe(probeDTO2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<ProbeResponseDTO> probes = probeService.getProbesByPlanetId(planet.getId(), pageable);

        AssertionsForClassTypes.assertThat(probes).isNotNull();
        assertThat(probes).hasSize(2);
    }
}
