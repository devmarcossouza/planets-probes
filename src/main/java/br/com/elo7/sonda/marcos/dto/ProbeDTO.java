package br.com.elo7.sonda.marcos.dto;

import br.com.elo7.sonda.marcos.model.Direction;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProbeDTO {

	@NotNull
	@Min(1)
	private int x;

	@NotNull
	@Min(1)
	private int y;
	@NotNull
	private Long planetId;
	@NotNull
	private Direction direction;

	public ProbeDTO(int x, int y, Direction direction, Long planetId) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.planetId = planetId;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Direction getDirection() {
		return direction;
	}

	public Long getPlanetId() {return planetId; }
}
