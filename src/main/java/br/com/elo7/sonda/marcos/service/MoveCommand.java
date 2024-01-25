package br.com.elo7.sonda.marcos.service;

import br.com.elo7.sonda.marcos.dto.MoveCommandDTO;
import br.com.elo7.sonda.marcos.model.Direction;
import br.com.elo7.sonda.marcos.model.Probe;

import static br.com.elo7.sonda.marcos.model.Direction.*;

public enum MoveCommand {
    L {
        @Override
        public void execute(Probe probe) {
            Direction newDirection = switch (probe.getDirection()) {
                case N -> W;
                case W -> S;
                case S -> E;
                case E -> N;
            };
            probe.setDirection(newDirection);
        }
    },
    M{
        @Override
        public void execute(Probe probe) {
            int newX = probe.getX();
            int newY = probe.getY();
            switch (probe.getDirection()) {
                case N -> newY++;
                case W -> newX--;
                case S -> newY--;
                case E -> newX++;
            }
            probe.setX(newX);
            probe.setY(newY);
        }
    },
    R{
        @Override
        public void execute(Probe probe) {
            Direction newDirection = switch (probe.getDirection()) {
                case N -> E;
                case E -> S;
                case S -> W;
                case W -> N;
            };
            probe.setDirection(newDirection);
        }
    };

    public abstract void execute(Probe probe);

    public static MoveCommand fromDTO(MoveCommandDTO moveCommandDTO) {
        return switch (moveCommandDTO) {
            case L -> L;
            case R -> R;
            case M -> M;
            default -> throw new IllegalArgumentException("Invalid move command: " + moveCommandDTO);
        };
    }
}
