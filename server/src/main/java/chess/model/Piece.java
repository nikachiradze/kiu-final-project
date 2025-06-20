package chess.model;

import Enums.PieceType;
import chess.common.moveExecutor.MoveExecutorStrategy;
import Enums.PieceColor;
import chess.common.movement.MovementStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public abstract class Piece {
    @Getter
    private final PieceColor color;
    @Getter
    @Setter
    private Square position;

    @Getter
    @Setter
    private PieceType pieceType;
    @Setter
    private MovementStrategy movementStrategy;

    @Setter
    private MoveExecutorStrategy moveExecutorStrategy;

    @Getter
    @Setter
    private boolean hasMoved;

    public Piece(PieceColor color, Square initSq) {
        this.color = color;
        this.position = initSq;


        this.movementStrategy = getMovementStrategy();
        this.moveExecutorStrategy = getMoveExecutorStrategy();
    }


    public boolean move(Square destination, Board board) {
      return moveExecutorStrategy.executeMove(board, destination );
    }

    protected abstract MovementStrategy getMovementStrategy();

    protected abstract MoveExecutorStrategy getMoveExecutorStrategy();



    public List<Square> getLegalMoves(Board b) {
        return movementStrategy.getLegalMoves(b);
    }


}