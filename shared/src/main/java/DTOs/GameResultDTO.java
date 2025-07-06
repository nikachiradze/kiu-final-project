package DTOs;

import Enums.PieceColor;

import java.io.Serializable;

public class GameResultDTO implements Serializable {
    private String result;
    private PieceColor pieceColor;

    public GameResultDTO() {}

    public GameResultDTO(String result, PieceColor pieceColor) {
        this.result = result;
        this.pieceColor = pieceColor;
    }
}
