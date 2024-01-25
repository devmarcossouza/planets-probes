package br.com.elo7.sonda.marcos.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PLANET")
public class Planet {
	public static final String WIDTH_MUST_BE_GREATER_THAN_OR_EQUAL_1 = "Width must be greater than or equal 1";
	public static final String HEIGHT_MUST_BE_GREATER_THAN_OR_EQUAL_1 = "Height must be greater than or equal 1";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@Min(value = 1, message = WIDTH_MUST_BE_GREATER_THAN_OR_EQUAL_1)
	private int width;

	@Column
	@Min(value = 1, message = HEIGHT_MUST_BE_GREATER_THAN_OR_EQUAL_1)
	private int height;

	public Set<Probe> getProbes() {
		return probes;
	}

	@OneToMany(mappedBy = "planet")
	private Set<Probe> probes;

	public Planet() {
	}

	public Planet(final int height, final int width) {
		if (height < 1) {
			throw new IllegalArgumentException(HEIGHT_MUST_BE_GREATER_THAN_OR_EQUAL_1);
		}
		if (width < 1) {
			throw new IllegalArgumentException(WIDTH_MUST_BE_GREATER_THAN_OR_EQUAL_1);
		}
		this.height = height;

		this.width = width;
		this.probes = new HashSet<>();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Long getId() {
		return id;
	}

}
