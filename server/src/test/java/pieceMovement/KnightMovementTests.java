package pieceMovement;

import chess.model.Board;
import chess.model.Knight;

import Enums.PieceColor;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnightMovementTests {

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
    void testKnightCentralPositionAllMovesAvailable() {
        Square from = board.getSquare("d4");
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        List<Square> moves = knight.getLegalMoves(board);

        String[] expectedMoves = {"c6", "e6", "f5", "f3", "e2", "c2", "b3", "b5"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(8, moves.size());
    }

    @Test
    void testKnightInCornerPosition() {
        Square from = board.getSquare("a1");
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        List<Square> moves = knight.getLegalMoves(board);

        assertTrue(moves.contains(board.getSquare("b3")));
        assertTrue(moves.contains(board.getSquare("c2")));
        assertEquals(2, moves.size());
    }

    @Test
    void testKnightBlockedByFriendlyPieces() {
        Square from = board.getSquare("d4");
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        // Surround knight with friendly pieces at valid L-move positions
        String[] friendlyPositions = {"c6", "e6", "f5", "f3", "e2", "c2", "b3", "b5"};
        for (String pos : friendlyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Knight(PieceColor.WHITE, board.getSquare(pos)));
        }

        List<Square> moves = knight.getLegalMoves(board);
        assertTrue(moves.isEmpty(), "Knight shouldn't be able to move to squares occupied by friendly pieces");
    }

    @Test
    void testKnightCanCaptureEnemyPieces() {
        Square from = board.getSquare("d4");
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        String[] enemyPositions = {"c6", "e6", "f5", "f3", "e2", "c2", "b3", "b5"};
        for (String pos : enemyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Knight(PieceColor.BLACK, board.getSquare(pos)));
        }

        List<Square> moves = knight.getLegalMoves(board);
        assertEquals(8, moves.size(), "Knight should be able to capture all enemy pieces");

        for (String pos : enemyPositions) {
            assertTrue(moves.contains(board.getSquare(pos)), "Knight should be able to capture at " + pos);
        }
    }

    @Test
    void testBlackKnightSymmetricMoves() {
        Square from = board.getSquare("e5");
        Knight knight = new Knight(PieceColor.BLACK, from);
        from.setOccupyingPiece(knight);

        List<Square> moves = knight.getLegalMoves(board);

        String[] expectedMoves = {"d7", "f7", "g6", "g4", "f3", "d3", "c4", "c6"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(8, moves.size());
    }


    @Test
    void testKnightAtEdgeNotInCorner() {
        Square from = board.getSquare("a4");  // Position at edge, but not corner
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        List<Square> moves = knight.getLegalMoves(board);

        // Expected moves, knight should be able to move two squares forward and one to the sides, etc.
        String[] expectedMoves = {"b6", "c5", "c3", "b2"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(4, moves.size());  // Only three possible moves from this position
    }

    @Test
    void testKnightAtEdgeWithLimitedMoves() {
        Square from = board.getSquare("h4");  // Position at edge of the board
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        List<Square> moves = knight.getLegalMoves(board);

        List<String> tostringPos = moves.stream().map(x->x.getPosition().toAlgebraic()).toList();

        // Knight can only move in available positions that stay within board boundaries
        String[] expectedMoves = {"f5", "g6", "g2", "f3"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(4, moves.size());
    }

    @Test
    void testKnightSurroundedByFriendlyPieces() {
        Square from = board.getSquare("d4");
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        // Surround knight with friendly pieces at valid L-move positions
        String[] friendlyPositions = {"c6", "e6", "f5", "f3", "e2", "c2", "b3", "b5"};
        for (String pos : friendlyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Knight(PieceColor.WHITE, board.getSquare(pos)));
        }

        List<Square> moves = knight.getLegalMoves(board);

        // Should be no valid moves because all positions are blocked by friendly pieces
        assertTrue(moves.isEmpty(), "Knight shouldn't be able to move to squares occupied by friendly pieces");
    }

    @Test
    void testKnightSurroundedByFriendlyAndEnemyPieces() {
        Square from = board.getSquare("d4");
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        // Friendly pieces on some of the L-move positions
        String[] friendlyPositions = {"c6", "e6", "f5", "f3", "e2", "c2"};
        for (String pos : friendlyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Knight(PieceColor.WHITE, board.getSquare(pos)));
        }

        // Enemy pieces on other L-move positions
        String[] enemyPositions = {"b3", "b5"};
        for (String pos : enemyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Knight(PieceColor.BLACK, board.getSquare(pos)));
        }

        List<Square> moves = knight.getLegalMoves(board);

        // The knight should be able to move to squares occupied by enemy pieces
        for (String pos : enemyPositions) {
            assertTrue(moves.contains(board.getSquare(pos)), "Knight should be able to capture at " + pos);
        }

        // The knight shouldn't be able to move to squares occupied by friendly pieces
        for (String pos : friendlyPositions) {
            assertFalse(moves.contains(board.getSquare(pos)), "Knight shouldn't move to friendly piece square " + pos);
        }

        // Total valid moves should be 2, since knight can capture at 2 enemy positions
        assertEquals(2, moves.size());
    }

    @Test
    void testKnightOutOfBounds() {
        Square from = board.getSquare("a1");
        Knight knight = new Knight(PieceColor.WHITE, from);
        from.setOccupyingPiece(knight);

        // Knight is in the corner, check if out-of-bounds moves are ignored
        List<Square> moves = knight.getLegalMoves(board);

        // Only valid moves are within board boundaries (a1 -> b3, c2)
        String[] expectedMoves = {"b3", "c2"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(2, moves.size());
    }
}
