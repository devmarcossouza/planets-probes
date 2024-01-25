package br.com.elo7.sonda.marcos.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MoveProbeDTO {
    public MoveProbeDTO(List<MoveCommandDTO> commands) {
        this.commands = commands;
    }

    public List<MoveCommandDTO> getCommands() {
        return commands;
    }

    @NotNull
    private List<MoveCommandDTO> commands;
}
