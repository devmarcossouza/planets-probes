package br.com.elo7.sonda.marcos.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PlanetDTO {
    @NotNull
    @Min(1)
    private int width;
    @NotNull
    @Min(1)
    private int height;

    public PlanetDTO(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
