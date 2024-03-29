package br.com.elo7.sonda.marcos.controller;

import br.com.elo7.sonda.marcos.dto.MoveProbeDTO;
import br.com.elo7.sonda.marcos.dto.ProbeDTO;
import br.com.elo7.sonda.marcos.dto.ProbeResponseDTO;
import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.model.Probe;
import br.com.elo7.sonda.marcos.service.ProbeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/probes")
public class ProbeController {
	private final ProbeService probeService;

	public ProbeController(ProbeService probeService) {
		this.probeService = probeService;
	}

	@Operation(description = "Register the probe to the planet, in accordance with the specifications received")
	@PostMapping
	public ResponseEntity<ProbeResponseDTO> landProbe(@RequestBody ProbeDTO probeDTO) {
		try {
			ProbeResponseDTO probeResponseDTO = probeService.landProbe(probeDTO);
			return new ResponseEntity<>(probeResponseDTO, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(description = "Moves the probe according to received commands")
	@PatchMapping("/{id}")
	public ResponseEntity<ProbeResponseDTO> moveProbe(@PathVariable Long id, @RequestBody MoveProbeDTO moveProbeDTO) {
		try {
			return ResponseEntity.ok(probeService.moveProbe(id, moveProbeDTO));
		} catch (IllegalArgumentException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<ProbeResponseDTO> getById(@PathVariable Long id) {
		var probeOptional = probeService.getById(id);
		return probeOptional.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	@GetMapping("/probesByPlanet/{planetId}")
	public ResponseEntity<Page<ProbeResponseDTO>> getProbesByPlanetId(
			@PathVariable("planetId") Long planetId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ProbeResponseDTO> probes = probeService.getProbesByPlanetId(planetId, pageable);
		return ResponseEntity.ok(probes);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		probeService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
