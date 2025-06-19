package chess.model;

import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import chess.model.enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardBishopMovement;

public class Bishop extends Piece {

    public Bishop(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
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
