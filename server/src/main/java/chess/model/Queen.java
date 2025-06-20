package chess.model;

import Enums.PieceType;
import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import Enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardQueenMovement;

public class Queen extends Piece {

    public Queen(PieceColor color, Square initSq) {
        super(color, initSq);
        setPieceType(color == PieceColor.WHITE ? PieceType.WHITE_QUEEN : PieceType.BLACK_QUEEN);
    }


    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardQueenMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new BasicMoveExecutor(this);
    }



}
