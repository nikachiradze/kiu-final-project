package DTOs;

import Enums.PieceColor;
import Enums.PieceType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PieceDTO implements Serializable {
    private final PieceColor color;
    private SquareDTO position;
    private final PieceType pieceType;
    private boolean hasMoved;

    public PieceDTO(PieceColor color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }
}
