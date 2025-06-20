package GameStateTests;
import chess.model.*;
import chess.common.checkmateDetector.CheckmateDetector;
import chess.common.checkmateDetector.StandardCheckmateDetector;
import chess.model.Board;
import Enums.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StalemateTests {

    private CheckmateDetector stalemateDetector;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        stalemateDetector = new StandardCheckmateDetector(board);
    }

    private void setPiece(String position, Piece piece) {
        Square square = board.getSquare(position);
        square.setOccupyingPiece(piece);
        if (piece.getColor() == PieceColor.WHITE) {
            board.getWhitePieces().add(piece);
        } else {
            board.getBlackPieces().add(piece);
        }
    }

    private void clearBoard() {
        board.getWhitePieces().clear();
        board.getBlackPieces().clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = board.getSquareArray()[i][j];
                square.setOccupyingPiece(null);
            }
        }
    }

    @Test
    void testBasicKingStalemate() {
        // Classic king stalemate position
        clearBoard();

        // White pieces
        setPiece("h1", new King(PieceColor.WHITE, board.getSquare("h1")));

        // Black pieces
        setPiece("f2", new King(PieceColor.BLACK, board.getSquare("f2")));
        setPiece("g3", new Queen(PieceColor.BLACK, board.getSquare("g3")));

        assertTrue(stalemateDetector.isStalemate(PieceColor.WHITE),
                "White should be in stalemate - king cannot move but is not in check");
        assertFalse(stalemateDetector.isStalemate(PieceColor.BLACK),
                "Black should not be in stalemate");
    }

    @Test
    void testCornerStalemate() {
        // King trapped in corner with no legal moves but not in check
        clearBoard();

        // White pieces
        setPiece("a1", new King(PieceColor.WHITE, board.getSquare("a1")));

        // Black pieces
        setPiece("c2", new King(PieceColor.BLACK, board.getSquare("c2")));
        setPiece("b3", new Queen(PieceColor.BLACK, board.getSquare("b3")));

        assertTrue(stalemateDetector.isStalemate(PieceColor.WHITE),
                "White should be in stalemate - king in corner cannot move");
    }

    @Test
    void testNotStalemateWhenInCheck() {
        // Position where king is in check - not stalemate
        clearBoard();

        // White pieces
        setPiece("h1", new King(PieceColor.WHITE, board.getSquare("h1")));

        // Black pieces
        setPiece("f2", new King(PieceColor.BLACK, board.getSquare("f2")));
        setPiece("h2", new Queen(PieceColor.BLACK, board.getSquare("h2"))); // Giving check

        assertFalse(stalemateDetector.isStalemate(PieceColor.WHITE),
                "Not stalemate - king is in check, should be checkmate");
    }

    @Test
    void testNotStalemateWithLegalMoves() {
        // Position where king has legal moves
        clearBoard();

        // White pieces
        setPiece("h1", new King(PieceColor.WHITE, board.getSquare("h1")));

        // Black pieces
        setPiece("f2", new King(PieceColor.BLACK, board.getSquare("f2")));
        setPiece("f1", new Queen(PieceColor.BLACK, board.getSquare("f1")));

        assertFalse(stalemateDetector.isStalemate(PieceColor.WHITE),
                "Not stalemate - king can move to g1");
    }

    @Test
    void testPawnStalemate() {
        // Edge case where a pawn is the only movable piece but is blocked
        clearBoard();

        // White pieces
        setPiece("a1", new King(PieceColor.WHITE, board.getSquare("a1")));
        setPiece("b2", new Pawn(PieceColor.WHITE, board.getSquare("b2"))); // Blocked by black bishop

        // Black pieces
        setPiece("d4", new King(PieceColor.BLACK, board.getSquare("d4")));
        setPiece("b3", new Bishop(PieceColor.BLACK, board.getSquare("b3"))); // Blocks pawn
        setPiece("c2", new Queen(PieceColor.BLACK, board.getSquare("c2"))); // Controls a3, b1

        assertTrue(stalemateDetector.isStalemate(PieceColor.WHITE),
                "White should be in stalemate - king and pawn cannot move");
    }

    @Test
    void testStalemateWithKnightAndBishop() {
        // Stalemate with knights and bishops that can't move
        clearBoard();

        // White pieces
        setPiece("a1", new King(PieceColor.WHITE, board.getSquare("a1")));
        setPiece("a2", new Knight(PieceColor.WHITE, board.getSquare("a2"))); // Pinned
        setPiece("b1", new Bishop(PieceColor.WHITE, board.getSquare("b1"))); // Pinned

        // Black pieces
        setPiece("d4", new King(PieceColor.BLACK, board.getSquare("d4")));
        setPiece("a8", new Rook(PieceColor.BLACK, board.getSquare("a8"))); // Pins knight
        setPiece("h7", new Bishop(PieceColor.BLACK, board.getSquare("h7"))); // Pins bishop
        setPiece("c1", new Queen(PieceColor.BLACK, board.getSquare("c2"))); // Controls b2

        assertTrue(stalemateDetector.isStalemate(PieceColor.WHITE),
                "White should be in stalemate with pinned knight and bishop");
    }

    @Test
    void testDeadPosition() {
        // Test insufficient material position (not technically stalemate, but no possible checkmate)
        clearBoard();

        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("c1", new Bishop(PieceColor.WHITE, board.getSquare("c1")));

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));

        // This isn't technically a stalemate (black can still move), but no checkmate is possible
        assertFalse(stalemateDetector.isStalemate(PieceColor.BLACK),
                "Not stalemate - king can still move even though checkmate is impossible");
    }

    @Test
    void testKingVsKingPosition() {
        // King vs King position - not stalemate if either can move
        clearBoard();

        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));

        // Black pieces
        setPiece("e3", new King(PieceColor.BLACK, board.getSquare("e3")));

        assertFalse(stalemateDetector.isStalemate(PieceColor.WHITE),
                "Not stalemate - white king can move");
        assertFalse(stalemateDetector.isStalemate(PieceColor.BLACK),
                "Not stalemate - black king can move");
    }
}
