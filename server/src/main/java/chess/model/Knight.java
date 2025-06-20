package chess.model;

import Enums.PieceType;
import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import Enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardKnightMovement;

public class Knight extends Piece {

    public Knight(PieceColor color, Square initSq) {
        super(color, initSq);
        setPieceType(color == PieceColor.WHITE ? PieceType.WHITE_KNIGHT : PieceType.BLACK_KNIGHT);

    }


    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardKnightMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new BasicMoveExecutor(this);
    }



}
