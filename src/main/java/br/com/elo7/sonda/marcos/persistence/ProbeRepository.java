package br.com.elo7.sonda.marcos.persistence;

import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.model.Probe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProbeRepository extends JpaRepository<Probe, Long> {
	Optional<Probe> findById(Long id);

	Optional<Probe> findByPlanetAndXAndY(Planet planet, int x, int y);

	Page<Probe> findByPlanetId(Long planetId, Pageable pageable);

}
