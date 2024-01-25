package br.com.elo7.sonda.marcos.service;

import br.com.elo7.sonda.marcos.dto.MoveCommandDTO;
import br.com.elo7.sonda.marcos.dto.MoveProbeDTO;
import br.com.elo7.sonda.marcos.dto.ProbeDTO;
import br.com.elo7.sonda.marcos.dto.ProbeResponseDTO;
import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.model.Probe;
import br.com.elo7.sonda.marcos.persistence.ProbeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Optional;


@Service
public class ProbeService {
	private final ProbeRepository probeRepository;

	private final PlanetService planetService;

	public ProbeService(ProbeRepository probeRepository, PlanetService planetService) {
		this.probeRepository = probeRepository;
		this.planetService = planetService;
	}

	@Transactional
	public ProbeResponseDTO landProbe(final ProbeDTO probeDTO) {
		Planet planet = planetService.getByIdEntity(probeDTO.getPlanetId())
				.orElseThrow(() -> new NoResultException("Planet not found!"));
		if(!isPossibleLandProbe(planet, probeDTO))
			throw new IllegalArgumentException("Is not possible land probe!");
		Probe probe = new Probe(probeDTO.getX(), probeDTO.getY(), probeDTO.getDirection(), planet);
		return convertEntityToDTO(probeRepository.save(probe));
	}

	private boolean isPossibleLandProbe(Planet planet, ProbeDTO probeDTO) {
		int planetHeight = planet.getHeight();
		int planetWidth = planet.getWidth();
        int maxProbes = planetHeight * planetWidth;
		return (planet.getProbes().size() < maxProbes) && probeRepository.findByPlanetAndXAndY(planet, probeDTO.getX(), probeDTO.getY()).isEmpty();
	}

	@Transactional
	public ProbeResponseDTO moveProbe(long probeId, MoveProbeDTO moveProbeDTO) {
		Probe probe = probeRepository.findById(probeId)
				.orElseThrow(() -> new NoResultException("Probe not found with id " + probeId));
		for (MoveCommandDTO commandDTO : moveProbeDTO.getCommands()) {
			MoveCommand command = MoveCommand.fromDTO(commandDTO);
			command.execute(probe);
		}
		return convertEntityToDTO(probeRepository.save(probe));
	}

	public Optional<ProbeResponseDTO> getById(final Long id) {
		return probeRepository.findById(id).map(this::convertEntityToDTO);
	}

	public Page<ProbeResponseDTO> getProbesByPlanetId(Long planetId, Pageable pageable) {
		Page<Probe> probes = probeRepository.findByPlanetId(planetId, pageable);
		return probes.map(this::convertEntityToDTO);
	}

	public void deleteById(final Long id) {
		probeRepository.findById(id).ifPresent(probeRepository::delete);
	}

	private ProbeResponseDTO convertEntityToDTO(Probe probe) {
		return new ProbeResponseDTO(probe.getX(), probe.getY(), probe.getDirection(),
				probe.getPlanet().getId(), probe.getId());
	}
}

