package chess.model;

import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import chess.model.enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardQueenMovement;

public class Queen extends Piece {

    public Queen(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
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
