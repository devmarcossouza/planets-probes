package br.com.elo7.sonda.marcos.model;

import javax.persistence.*;

@Entity
@Table(name = "PROBE")
public class Probe {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private int x;
	@Column
	private int y;
	@Enumerated(EnumType.STRING)
	private Direction direction;

	@ManyToOne
	@JoinColumn(name = "planet_id", nullable = false)
	private Planet planet;

	public Long getId() {
		return id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		if(x > planet.getHeight() || x < 0)
			throw new IllegalArgumentException("X value not allowed!");
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		if(y > planet.getWidth() || y < 0)
			throw new IllegalArgumentException("Y value not allowed!");
		this.y = y;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public Planet getPlanet() {
		return planet;
	}

	public Probe() {}
	public Probe(int x, int y, Direction direction, Planet planet) {
		this.planet = planet;
		setX(x);
		setY(y);
		this.direction = direction;
	}
}
