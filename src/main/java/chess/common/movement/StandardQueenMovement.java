package chess.common.movement;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;
import chess.util.MovementHelper;

import java.util.List;

public class StandardQueenMovement implements MovementStrategy{

    private final Piece piece;


    public StandardQueenMovement(Piece piece) {
        this.piece = piece;
    }

    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        return MovementHelper.getCombinedMoves(chessBoard,piece);
    }

}
