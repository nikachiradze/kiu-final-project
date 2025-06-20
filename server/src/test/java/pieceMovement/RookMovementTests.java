package pieceMovement;

import chess.model.Board;
import chess.model.Rook;
import chess.model.Square;
import Enums.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RookMovementTests {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.getSquareArray()[i][j].setOccupyingPiece(null);
            }
        }
        board.getWhitePieces().clear();
        board.getBlackPieces().clear();
    }

    @Test
    void testRookUnblockedMovement() {
        Square from = board.getSquare("d4");
        Rook rook = new Rook(PieceColor.WHITE, from);
        from.setOccupyingPiece(rook);

        List<Square> moves = rook.getLegalMoves(board);

        // Rook can move in all 4 straight-line directions
        assertTrue(moves.contains(board.getSquare("d1")));
        assertTrue(moves.contains(board.getSquare("d8")));
        assertTrue(moves.contains(board.getSquare("a4")));
        assertTrue(moves.contains(board.getSquare("h4")));
    }

    @Test
    void testRookBlockedByFriendlyPiece() {
        Square from = board.getSquare("d4");
        Rook rook = new Rook(PieceColor.WHITE, from);
        from.setOccupyingPiece(rook);

        Square blockSquare = board.getSquare("d6");
        blockSquare.setOccupyingPiece(new Rook(PieceColor.WHITE, blockSquare));

        List<Square> moves = rook.getLegalMoves(board);

        assertFalse(moves.contains(blockSquare));
        assertFalse(moves.contains(board.getSquare("d7")));
        assertFalse(moves.contains(board.getSquare("d8")));
    }

    @Test
    void testRookCanCaptureEnemy() {
        Square from = board.getSquare("d4");
        Rook rook = new Rook(PieceColor.WHITE, from);
        from.setOccupyingPiece(rook);

        Square enemySquare = board.getSquare("d6");
        enemySquare.setOccupyingPiece(new Rook(PieceColor.BLACK, enemySquare));

        List<Square> moves = rook.getLegalMoves(board);

        assertTrue(moves.contains(enemySquare), "Rook should be able to capture enemy");
        assertFalse(moves.contains(board.getSquare("d7")), "Rook should not go past captured piece");
    }

    @Test
    void testRookAtCorner() {
        Square from = board.getSquare("a1");
        Rook rook = new Rook(PieceColor.WHITE, from);
        from.setOccupyingPiece(rook);

        List<Square> moves = rook.getLegalMoves(board);

        assertTrue(moves.contains(board.getSquare("a2")));
        assertTrue(moves.contains(board.getSquare("a8")));
        assertTrue(moves.contains(board.getSquare("b1")));
        assertTrue(moves.contains(board.getSquare("h1")));
    }


    @Test
    void testRookSurroundedByFriendlyPieces() {
        Square from = board.getSquare("d4");
        Rook rook = new Rook(PieceColor.WHITE, from);
        from.setOccupyingPiece(rook);

        board.getSquare("d5").setOccupyingPiece(new Rook(PieceColor.WHITE, board.getSquare("d5")));
        board.getSquare("d3").setOccupyingPiece(new Rook(PieceColor.WHITE, board.getSquare("d3")));
        board.getSquare("c4").setOccupyingPiece(new Rook(PieceColor.WHITE, board.getSquare("c4")));
        board.getSquare("e4").setOccupyingPiece(new Rook(PieceColor.WHITE, board.getSquare("e4")));

        List<Square> moves = rook.getLegalMoves(board);

        assertTrue(moves.isEmpty(), "Rook surrounded by friendly pieces should have no legal moves");
    }

    @Test
    void testRookSurroundedByEnemyPieces() {
        Square from = board.getSquare("d4");
        Rook rook = new Rook(PieceColor.WHITE, from);
        from.setOccupyingPiece(rook);

        board.getSquare("d5").setOccupyingPiece(new Rook(PieceColor.BLACK, board.getSquare("d5")));
        board.getSquare("d3").setOccupyingPiece(new Rook(PieceColor.BLACK, board.getSquare("d3")));
        board.getSquare("c4").setOccupyingPiece(new Rook(PieceColor.BLACK, board.getSquare("c4")));
        board.getSquare("e4").setOccupyingPiece(new Rook(PieceColor.BLACK, board.getSquare("e4")));

        List<Square> moves = rook.getLegalMoves(board);

        assertTrue(moves.contains(board.getSquare("d5")));
        assertTrue(moves.contains(board.getSquare("d3")));
        assertTrue(moves.contains(board.getSquare("c4")));
        assertTrue(moves.contains(board.getSquare("e4")));
        assertEquals(4, moves.size(), "Rook should only be able to capture the 4 enemy pieces");
    }

    @Test
    void testRookWithMixedBlocks() {
        Square from = board.getSquare("d4");
        Rook rook = new Rook(PieceColor.WHITE, from);
        from.setOccupyingPiece(rook);

        board.getSquare("d5").setOccupyingPiece(new Rook(PieceColor.WHITE, board.getSquare("d5")));
        board.getSquare("d3").setOccupyingPiece(new Rook(PieceColor.BLACK, board.getSquare("d3")));
        board.getSquare("c4").setOccupyingPiece(new Rook(PieceColor.BLACK, board.getSquare("c4")));
        board.getSquare("e4").setOccupyingPiece(new Rook(PieceColor.WHITE, board.getSquare("e4")));

        List<Square> moves = rook.getLegalMoves(board);

        assertFalse(moves.contains(board.getSquare("d5"))); // friendly block
        assertTrue(moves.contains(board.getSquare("d3"))); // enemy capture
        assertTrue(moves.contains(board.getSquare("c4"))); // enemy capture
        assertFalse(moves.contains(board.getSquare("e4"))); // friendly block
    }

    @Test
    void testRookCannotMoveDiagonally() {
        Square from = board.getSquare("d4");
        Rook rook = new Rook(PieceColor.WHITE, from);
        from.setOccupyingPiece(rook);

        List<Square> moves = rook.getLegalMoves(board);

        assertFalse(moves.contains(board.getSquare("c3")));
        assertFalse(moves.contains(board.getSquare("e5")));
        assertFalse(moves.contains(board.getSquare("e3")));
        assertFalse(moves.contains(board.getSquare("c5")));
    }

    @Test
    void testBlackRookSymmetricMovement() {
        Square from = board.getSquare("f6");
        Rook rook = new Rook(PieceColor.BLACK, from);
        from.setOccupyingPiece(rook);

        List<Square> moves = rook.getLegalMoves(board);

        assertTrue(moves.contains(board.getSquare("f1")));
        assertTrue(moves.contains(board.getSquare("f8")));
        assertTrue(moves.contains(board.getSquare("a6")));
        assertTrue(moves.contains(board.getSquare("h6")));
    }

}
