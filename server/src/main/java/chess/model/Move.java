package chess.model;


import chess.model.enums.PieceColor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Move {

    private Piece movedPiece;
    private Square from;
    private Square to;
    private Piece capturePiece;

    public Move(Piece movedPiece, Square from, Square to) {
        this.movedPiece = movedPiece;
        this.from = from;
        this.to = to;
    }

    public void undo(Board board) {
        from.setOccupyingPiece(movedPiece);
        to.setOccupyingPiece(capturePiece);
        movedPiece.setPosition(from);
        if (capturePiece != null) {
            capturePiece.setPosition(to);
            if (capturePiece.getColor() == PieceColor.WHITE) {
                board.getWhitePieces().add(capturePiece);
            } else {
                board.getBlackPieces().add(capturePiece);
            }
        }
    }

    @Override
    public String toString() {
        return "Move{" +
                "movedPiece=" + movedPiece.getClass().toString() +
                ", from=" + from.getPosition().toAlgebraic() +
                ", to=" + to.getPosition().toAlgebraic() +
                '}';
    }
}