package br.com.elo7.sonda.marcos.dto;

public class PlanetResponseDTO extends PlanetDTO{

    private Long id;

    public PlanetResponseDTO(int width, int height, Long id) {
        super(width, height);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
