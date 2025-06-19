package chess.model;

import chess.model.enums.PieceColor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class Square {
    @Getter
    private final Position position;

    @Getter
    private final PieceColor color;

    @Getter @Setter
    private Piece occupyingPiece;

    @Getter @Setter
    private boolean display = true; // should be refactored moved out to

    public Square(PieceColor color, int x, int y) {
        this.color = color;
        this.position = new Position(x, y);
    }

    public Square(Square other){
        this.color = other.color;
        this.position = other.position;
        this.display = other.display;
    }

    public boolean isOccupied() {
        return occupyingPiece != null;
    }

    public void put(Piece piece) {
        this.occupyingPiece = piece;
        if (piece != null) {
            piece.setPosition(this);
        }
    }

    public Piece removePiece() {
        Piece piece = this.occupyingPiece;
        this.occupyingPiece = null;
        return piece;
    }

    public boolean getDisplay(){
        return display;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Square other = (Square) obj;
        return position.equals(other.position);
    }
}