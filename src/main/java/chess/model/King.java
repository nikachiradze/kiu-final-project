package chess.model;

import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import chess.common.moveExecutor.WithCastleMoveExecutor;
import chess.model.enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardKingMovement;

public class King extends Piece {

    public King(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
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
