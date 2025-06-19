package chess.common.movement;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;
import chess.util.MovementHelper;

import java.util.List;

public class StandardBishopMovement implements MovementStrategy{

    private final Piece bishop;

    public StandardBishopMovement(Piece piece) {
        this.bishop = piece;
    }

    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        return MovementHelper.getDiagonalMoves(chessBoard, bishop);
    }


}
