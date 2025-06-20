package chess.model;

import Enums.PieceType;
import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import Enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardRookMovement;

public class Rook extends Piece {

    public Rook(PieceColor color, Square initSq) {
        super(color, initSq);
        setPieceType(color == PieceColor.WHITE ? PieceType.WHITE_ROOK : PieceType.BLACK_ROOK);

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
