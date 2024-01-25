package br.com.elo7.sonda.marcos.persistence;

import br.com.elo7.sonda.marcos.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
	Optional<Planet> findById(Long id);

}
