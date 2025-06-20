package chess.model;

import Enums.PieceType;
import chess.common.moveExecutor.BasicMoveExecutor;
import chess.common.moveExecutor.MoveExecutorStrategy;
import Enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.common.movement.StandardPawnMovement;

public class Pawn extends Piece {


    public Pawn(PieceColor color, Square initSq) {
        super(color, initSq);
        setPieceType(color == PieceColor.WHITE ? PieceType.WHITE_PAWN : PieceType.BLACK_PAWN);

    }


    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardPawnMovement(this,false);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new BasicMoveExecutor(this);
    }


    @Override
    public boolean move(Square fin,Board board) {
        setMovementStrategy(new StandardPawnMovement(this,true));
        return super.move(fin,board);
    }


}
