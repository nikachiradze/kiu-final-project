package chess.model;

import Enums.PieceType;
import chess.common.moveExecutor.MoveExecutorStrategy;
import chess.common.moveExecutor.WithCastleMoveExecutor;
import Enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardKingMovement;

public class King extends Piece {

    public King(PieceColor color, Square initSq) {
        super(color, initSq);
        setPieceType(color == PieceColor.WHITE ? PieceType.WHITE_KING : PieceType.BLACK_KING);

    }



    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardKingMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new WithCastleMoveExecutor(this);
    }


}
