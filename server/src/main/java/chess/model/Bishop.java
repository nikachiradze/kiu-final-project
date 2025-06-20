package chess.model;

import Enums.PieceType;
import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import Enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardBishopMovement;

public class Bishop extends Piece {

    public Bishop(PieceColor color, Square initSq) {
        super(color, initSq);
        setPieceType(color == PieceColor.WHITE ? PieceType.WHITE_BISHOP : PieceType.BLACK_BISHOP);
    }



    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardBishopMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new BasicMoveExecutor(this);
    }


}
