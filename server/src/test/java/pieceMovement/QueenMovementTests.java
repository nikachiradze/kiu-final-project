package pieceMovement;

import chess.model.Board;
import chess.model.Queen;
import Enums.PieceColor;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueenMovementTests {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = board.getSquareArray()[i][j];
                square.setOccupyingPiece(null);
            }
        }
        board.getWhitePieces().clear();
        board.getBlackPieces().clear();
    }

    @Test
    void testQueenCentralPositionAllMovesAvailable() {
        Square from = board.getSquare("d4");
        Queen queen = new Queen(PieceColor.WHITE, from);
        from.setOccupyingPiece(queen);

        List<Square> moves = queen.getLegalMoves(board);

        // Queen can move in 8 directions until the edge of the board
        String[] expectedMoves = {
                "d5", "d6", "d7", "d8", // up
                "d3", "d2", "d1",       // down
                "e4", "f4", "g4", "h4", // right
                "c4", "b4", "a4",       // left
                "e5", "f6", "g7", "h8", // up-right
                "c5", "b6", "a7",       // up-left
                "e3", "f2", "g1",       // down-right
                "c3", "b2", "a1"        // down-left
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(27, moves.size());
    }

    @Test
    void testQueenInCornerPosition() {
        Square from = board.getSquare("a1");
        Queen queen = new Queen(PieceColor.WHITE, from);
        from.setOccupyingPiece(queen);

        List<Square> moves = queen.getLegalMoves(board);

        String[] expectedMoves = {
                "a2", "a3", "a4", "a5", "a6", "a7", "a8", // vertical
                "b1", "c1", "d1", "e1", "f1", "g1", "h1", // horizontal
                "b2", "c3", "d4", "e5", "f6", "g7", "h8"  // diagonal
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(21, moves.size());
    }

    @Test
    void testQueenBlockedByFriendlyPieces() {
        Square from = board.getSquare("d4");
        Queen queen = new Queen(PieceColor.WHITE, from);
        from.setOccupyingPiece(queen);

        String[] friendlyPositions = {
                "d5", "e5", "e4", "e3", "d3", "c3", "c4", "c5"
        };

        for (String pos : friendlyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Queen(PieceColor.WHITE, board.getSquare(pos)));
        }

        List<Square> moves = queen.getLegalMoves(board);
        assertFalse(moves.contains(board.getSquare("d5")));
        assertEquals(0, moves.size(), "Queen shouldn't be able to move through friendly pieces");
    }

    @Test
    void testQueenCanCaptureEnemyPieces() {
        Square from = board.getSquare("d4");
        Queen queen = new Queen(PieceColor.WHITE, from);
        from.setOccupyingPiece(queen);

        String[] enemyPositions = {
                "d5", "e5", "e4", "e3", "d3", "c3", "c4", "c5"
        };

        for (String pos : enemyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Queen(PieceColor.BLACK, board.getSquare(pos)));
        }

        List<Square> moves = queen.getLegalMoves(board);

        for (String pos : enemyPositions) {
            assertTrue(moves.contains(board.getSquare(pos)), "Queen should be able to capture at " + pos);
        }

        assertEquals(8, moves.size());
    }

    @Test
    void testQueenAtEdgeLimitedMoves() {
        Square from = board.getSquare("h4");
        Queen queen = new Queen(PieceColor.WHITE, from);
        from.setOccupyingPiece(queen);

        List<Square> moves = queen.getLegalMoves(board);

        String[] expectedMoves = {
                "h5", "h6", "h7", "h8", // vertical up
                "h3", "h2", "h1",       // vertical down
                "g4", "f4", "e4", "d4", "c4", "b4", "a4", // horizontal left
                "g5", "f6", "e7", "d8", // diagonal up-left
                "g3", "f2", "e1"        // diagonal down-left
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(21, moves.size());
    }

    @Test
    void testQueenSurroundedByMixedPieces() {
        Square from = board.getSquare("d4");
        Queen queen = new Queen(PieceColor.WHITE, from);
        from.setOccupyingPiece(queen);

        String[] friendlyPositions = {"d5", "e4", "d3", "c4"};
        for (String pos : friendlyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Queen(PieceColor.WHITE, board.getSquare(pos)));
        }

        String[] enemyPositions = {"e5", "e3", "c3", "c5"};
        for (String pos : enemyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Queen(PieceColor.BLACK, board.getSquare(pos)));
        }

        List<Square> moves = queen.getLegalMoves(board);

        // Should contain enemy positions
        for (String pos : enemyPositions) {
            assertTrue(moves.contains(board.getSquare(pos)), "Queen should be able to capture at " + pos);
        }

        // Should not contain friendly positions
        for (String pos : friendlyPositions) {
            assertFalse(moves.contains(board.getSquare(pos)), "Queen shouldn't move to friendly piece square " + pos);
        }

        assertEquals(4, moves.size());
    }
}
