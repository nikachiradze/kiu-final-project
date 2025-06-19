package chess.util;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for calculating legal moves for chess pieces based on movement patterns.
 */
public class MovementHelper {

    /**
     * Direction vectors for cardinal directions (up, down, left, right)
     */
    private static final int[][] LINEAR_DIRECTIONS = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
    };

    /**
     * Direction vectors for diagonal directions
     */
    private static final int[][] DIAGONAL_DIRECTIONS = {
            {-1, -1}, // northwest
            {1, -1},  // southwest
            {1, 1},   // southeast
            {-1, 1}   // northeast
    };

    /**
     * Gets linear moves (horizontal and vertical) for a piece
     * @param chessBoard The current board state
     * @param piece The piece to calculate moves for
     * @return List of squares the piece can move to linearly
     */
    public static List<Square> getLinearMoves(Board chessBoard, Piece piece) {
        List<Square> legalSquares = new ArrayList<>();
        Square[][] board = chessBoard.getSquareArray();
        Square position = piece.getPosition();
        int x = position.getPosition().getX();
        int y = position.getPosition().getY();

        // Check all four directions
        for (int[] direction : LINEAR_DIRECTIONS) {
            int dy = direction[0];
            int dx = direction[1];

            int currentY = y + dy;
            int currentX = x + dx;

            while (isValidPosition(currentY, currentX)) {
                Square targetSquare = board[currentY][currentX];

                if (targetSquare.isOccupied()) {
                    // If enemy piece, add and stop in this direction
                    if (targetSquare.getOccupyingPiece().getColor() != piece.getColor()) {
                        legalSquares.add(targetSquare);
                    }
                    break; // Stop at any piece (friendly or enemy)
                }

                // Empty square, add it
                legalSquares.add(targetSquare);

                // Continue in the same direction
                currentY += dy;
                currentX += dx;
            }
        }

        return legalSquares;
    }

    /**
     * Gets diagonal moves for a piece
     * @param chessBoard The current board state
     * @param piece The piece to calculate moves for
     * @return List of squares the piece can move to diagonally
     */
    public static List<Square> getDiagonalMoves(Board chessBoard, Piece piece) {
        List<Square> legalSquares = new ArrayList<>();
        Square[][] board = chessBoard.getSquareArray();
        Square position = piece.getPosition();
        int x = position.getPosition().getX();
        int y = position.getPosition().getY();

        // Check all four diagonal directions
        for (int[] direction : DIAGONAL_DIRECTIONS) {
            int dy = direction[0];
            int dx = direction[1];

            int currentY = y + dy;
            int currentX = x + dx;

            while (isValidPosition(currentY, currentX)) {
                Square targetSquare = board[currentY][currentX];

                if (targetSquare.isOccupied()) {
                    // If enemy piece, add and stop in this direction
                    if (targetSquare.getOccupyingPiece().getColor() != piece.getColor()) {
                        legalSquares.add(targetSquare);
                    }
                    break; // Stop at any piece (friendly or enemy)
                }

                // Empty square, add it
                legalSquares.add(targetSquare);

                // Continue in the same direction
                currentY += dy;
                currentX += dx;
            }
        }

        return legalSquares;
    }

    /**
     * Checks if a position is within the chess board bounds
     * @param y Row coordinate
     * @param x Column coordinate
     * @return true if position is valid, false otherwise
     */
    private static boolean isValidPosition(int y, int x) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    /**
     * Gets the combined moves (linear and diagonal) for a piece like a queen
     * @param chessBoard The current board state
     * @param piece The piece to calculate moves for
     * @return List of squares the piece can move to
     */
    public static List<Square> getCombinedMoves(Board chessBoard, Piece piece) {
        List<Square> moves = new ArrayList<>();
        moves.addAll(getLinearMoves(chessBoard, piece));
        moves.addAll(getDiagonalMoves(chessBoard, piece));
        return moves;
    }
}