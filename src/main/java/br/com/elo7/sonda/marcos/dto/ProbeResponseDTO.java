package br.com.elo7.sonda.marcos.dto;

import br.com.elo7.sonda.marcos.model.Direction;

public class ProbeResponseDTO extends ProbeDTO{

    public ProbeResponseDTO(int x, int y, Direction direction, Long planetId, Long id) {
        super(x, y, direction, planetId);
        this.id = id;
    }
    private Long id;
    public Long getId() {
        return id;
    }
}
