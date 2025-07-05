package DTOs;

import Enums.PieceColor;
import Enums.PieceType;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class SquareDTO implements Serializable {
    private final Position position;
    private final PieceColor color;
    ;

    private PieceDTO pieceDTO;

    private boolean display = true;

    private PieceDTO occupyingPiece;

    public SquareDTO(Position position, PieceColor color) {
        this.position = position;
        this.color = color;
    }

    public boolean getDisplay() {
        return display;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
