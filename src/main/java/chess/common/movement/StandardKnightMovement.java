package chess.common.movement;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;

import java.util.LinkedList;
import java.util.List;

public class StandardKnightMovement implements MovementStrategy {

    private final Piece knight;

    public StandardKnightMovement(Piece knight) {
        this.knight = knight;
    }

    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        List<Square> legalMoves = new LinkedList<>();
        Square[][] board = chessBoard.getSquareArray();
        Square position = knight.getPosition();

        int x = position.getPosition().getX();
        int y = position.getPosition().getY();

        int[][] moves = {
                {2, 1}, {1, 2}, {-1, 2}, {-2, 1},
                {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
        };

        for (int[] move : moves) {
            int newX = x + move[0];
            int newY = y + move[1];

            if (isInBounds(newX, newY)) {
                Square targetSquare = board[newY][newX];
                if (!targetSquare.isOccupied() ||
                        targetSquare.getOccupyingPiece().getColor() != knight.getColor()) {
                    legalMoves.add(targetSquare);
                }
            }
        }

        return legalMoves;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}
