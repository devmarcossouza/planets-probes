package br.com.elo7.sonda.marcos.service;

import br.com.elo7.sonda.marcos.dto.MoveCommandDTO;
import br.com.elo7.sonda.marcos.model.Direction;
import br.com.elo7.sonda.marcos.model.Planet;
import br.com.elo7.sonda.marcos.model.Probe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveCommandTest {

    @Test
    void should_change_probe_direction_from_N_To_W_when_receive_the_command_L() {
        Probe probe = new Probe();
        probe.setDirection(Direction.N);
        MoveCommand.L.execute(probe);
        assertEquals(Direction.W, probe.getDirection());
    }

    @Test
    void should_change_probe_direction_from_W_To_S_when_receive_the_command_L() {
        Probe probe = new Probe();
        probe.setDirection(Direction.W);
        MoveCommand.L.execute(probe);
        assertEquals(Direction.S, probe.getDirection());
    }

    @Test
    void should_change_probe_direction_from_S_To_E_when_receive_the_command_L() {
        Probe probe = new Probe();
        probe.setDirection(Direction.S);
        MoveCommand.L.execute(probe);
        assertEquals(Direction.E, probe.getDirection());
    }

    @Test
    void should_change_probe_direction_from_E_To_N_when_receive_the_command_L() {
        Probe probe = new Probe();
        probe.setDirection(Direction.E);
        MoveCommand.L.execute(probe);
        assertEquals(Direction.N, probe.getDirection());
    }

    @Test
    void should_change_probe_direction_from_N_To_E_when_receive_the_command_R() {
        Probe probe = new Probe();
        probe.setDirection(Direction.N);
        MoveCommand.R.execute(probe);
        assertEquals(Direction.E, probe.getDirection());
    }

    @Test
    void should_change_probe_direction_from_E_To_S_when_receive_the_command_R() {
        Probe probe = new Probe();
        probe.setDirection(Direction.E);
        MoveCommand.R.execute(probe);
        assertEquals(Direction.S, probe.getDirection());
    }

    @Test
    void should_change_probe_direction_from_S_To_W_when_receive_the_command_R() {
        Probe probe = new Probe();
        probe.setDirection(Direction.S);
        MoveCommand.R.execute(probe);
        assertEquals(Direction.W, probe.getDirection());
    }

    @Test
    void should_change_probe_direction_from_W_To_N_when_receive_the_command_R() {
        Probe probe = new Probe();
        probe.setDirection(Direction.W);
        MoveCommand.R.execute(probe);
        assertEquals(Direction.N, probe.getDirection());
    }

    @Test
    void should_change_probe_position_from_1_1_N_To_1_2_N_when_receive_the_command_M() {
        Probe probe = new Probe(1,1,Direction.N, fakePlanet());
        MoveCommand.M.execute(probe);
        assertEquals(2, probe.getY());
        assertEquals(1, probe.getX());
        assertEquals(Direction.N, probe.getDirection());
    }

    @Test
    void should_change_probe_position_from_1_1_S_To_1_0_S_when_receive_the_command_M() {
        Probe probe = new Probe(1,1,Direction.S, fakePlanet());
        MoveCommand.M.execute(probe);
        assertEquals(0, probe.getY());
        assertEquals(1, probe.getX());
        assertEquals(Direction.S, probe.getDirection());
    }

    @Test
    void should_change_probe_position_from_1_1_W_To_0_1_W_when_receive_the_command_M() {
        Probe probe = new Probe(1,1,Direction.W, fakePlanet());
        MoveCommand.M.execute(probe);
        assertEquals(0, probe.getX());
        assertEquals(1, probe.getY());
        assertEquals(Direction.W, probe.getDirection());
    }

    @Test
    void should_change_probe_position_from_1_1_E_To_2_1_E_when_receive_the_command_M() {
        Probe probe = new Probe(1,1,Direction.E, fakePlanet());
        MoveCommand.M.execute(probe);
        assertEquals(2, probe.getX());
        assertEquals(1, probe.getY());
        assertEquals(Direction.E, probe.getDirection());
    }

    @Test
    void testFromDTOL() {
        MoveCommandDTO moveCommandDTO = MoveCommandDTO.L;
        MoveCommand expected = MoveCommand.L;
        MoveCommand actual = MoveCommand.fromDTO(moveCommandDTO);
        assertEquals(expected, actual);
    }

    @Test
    void testFromDTOR() {
        MoveCommandDTO moveCommandDTO = MoveCommandDTO.R;
        MoveCommand expected = MoveCommand.R;
        MoveCommand actual = MoveCommand.fromDTO(moveCommandDTO);
        assertEquals(expected, actual);
    }

    @Test
    void testFromDTOM() {
        MoveCommandDTO moveCommandDTO = MoveCommandDTO.M;
        MoveCommand expected = MoveCommand.M;
        MoveCommand actual = MoveCommand.fromDTO(moveCommandDTO);
        assertEquals(expected, actual);
    }

    private Planet fakePlanet() {
        return new Planet(2,2);
    }

}