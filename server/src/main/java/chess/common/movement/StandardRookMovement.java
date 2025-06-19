package chess.common.movement;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;
import chess.util.MovementHelper;

import java.util.List;

public class StandardRookMovement implements MovementStrategy{

    private final Piece rook;

    public StandardRookMovement(Piece rook) {
        this.rook = rook;
    }


    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        return MovementHelper.getLinearMoves(chessBoard,rook);
    }

}
