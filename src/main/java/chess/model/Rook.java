package chess.model;

import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import chess.model.enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardRookMovement;

public class Rook extends Piece {

    public Rook(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }


    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardRookMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new BasicMoveExecutor(this);
    }



}
