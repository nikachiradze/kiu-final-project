package DTOs;

import Enums.PieceColor;

import java.io.Serializable;

public class GameInitDTO implements Serializable {
    private final PieceColor playerColor;

    public GameInitDTO(PieceColor playerColor) {
        this.playerColor = playerColor;
    }

    public PieceColor getPlayerColor() {
        return playerColor;
    }

    @Override
    public String toString() {
        return "You are playing as " + playerColor;
    }
}
