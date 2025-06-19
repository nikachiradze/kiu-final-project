package chess.model;

import chess.common.moveExecutor.MoveExecutorStrategy;
import chess.model.enums.PieceColor;
import chess.common.movement.MovementStrategy;
import chess.util.PieceImageLoader;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public abstract class Piece {
    @Getter
    private final PieceColor color;
    @Getter
    @Setter
    private Square position;

    @Setter
    private BufferedImage img;
    @Setter
    private MovementStrategy movementStrategy;

    @Setter
    private MoveExecutorStrategy moveExecutorStrategy;

    @Getter
    @Setter
    private boolean hasMoved;

    public Piece(PieceColor color, Square initSq, String img_file) {
        this.color = color;
        this.position = initSq;


        this.img = PieceImageLoader.loadImage(img_file);
        this.movementStrategy = getMovementStrategy();
        this.moveExecutorStrategy = getMoveExecutorStrategy();
    }


    public boolean move(Square destination, Board board) {
      return moveExecutorStrategy.executeMove(board, destination );
    }

    protected abstract MovementStrategy getMovementStrategy();

    protected abstract MoveExecutorStrategy getMoveExecutorStrategy();


    public Image getImage() {
        return img;
    }

    public List<Square> getLegalMoves(Board b) {
        return movementStrategy.getLegalMoves(b);
    }


}