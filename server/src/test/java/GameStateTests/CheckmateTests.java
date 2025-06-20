package GameStateTests;

import chess.common.checkmateDetector.StandardCheckmateDetector;
import chess.model.*;
import Enums.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckmateTests {

    private StandardCheckmateDetector checkmateDetector;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(); // starts with an empty board
        checkmateDetector = new StandardCheckmateDetector(board);
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

    @Test
    void testFoolsMate() {
        // A real chess opening that leads to checkmate in 2 moves
        // 1. f3 e5 2. g4 Qh4#
        clearBoard();
        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("d1", new Queen(PieceColor.WHITE, board.getSquare("d1")));
        setPiece("f1", new Bishop(PieceColor.WHITE, board.getSquare("f1")));
        setPiece("g1", new Knight(PieceColor.WHITE, board.getSquare("g1")));
        setPiece("h1", new Rook(PieceColor.WHITE, board.getSquare("h1")));
        setPiece("a1", new Rook(PieceColor.WHITE, board.getSquare("a1")));
        setPiece("c1", new Bishop(PieceColor.WHITE, board.getSquare("c1")));
        setPiece("b1", new Knight(PieceColor.WHITE, board.getSquare("b1")));
        setPiece("a2", new Pawn(PieceColor.WHITE, board.getSquare("a2")));
        setPiece("b2", new Pawn(PieceColor.WHITE, board.getSquare("b2")));
        setPiece("c2", new Pawn(PieceColor.WHITE, board.getSquare("c2")));
        setPiece("d2", new Pawn(PieceColor.WHITE, board.getSquare("d2")));
        setPiece("e2", new Pawn(PieceColor.WHITE, board.getSquare("e2")));
        setPiece("f3", new Pawn(PieceColor.WHITE, board.getSquare("f3"))); // Moved from f2
        setPiece("g4", new Pawn(PieceColor.WHITE, board.getSquare("g4"))); // Moved from g2
        setPiece("h2", new Pawn(PieceColor.WHITE, board.getSquare("h2")));

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("d8", new Queen(PieceColor.BLACK, board.getSquare("d8")));
        setPiece("f8", new Bishop(PieceColor.BLACK, board.getSquare("f8")));
        setPiece("g8", new Knight(PieceColor.BLACK, board.getSquare("g8")));
        setPiece("h8", new Rook(PieceColor.BLACK, board.getSquare("h8")));
        setPiece("a8", new Rook(PieceColor.BLACK, board.getSquare("a8")));
        setPiece("c8", new Bishop(PieceColor.BLACK, board.getSquare("c8")));
        setPiece("b8", new Knight(PieceColor.BLACK, board.getSquare("b8")));
        setPiece("a7", new Pawn(PieceColor.BLACK, board.getSquare("a7")));
        setPiece("b7", new Pawn(PieceColor.BLACK, board.getSquare("b7")));
        setPiece("c7", new Pawn(PieceColor.BLACK, board.getSquare("c7")));
        setPiece("d7", new Pawn(PieceColor.BLACK, board.getSquare("d7")));
        setPiece("e5", new Pawn(PieceColor.BLACK, board.getSquare("e5"))); // Moved from e7
        setPiece("f7", new Pawn(PieceColor.BLACK, board.getSquare("f7")));
        setPiece("g7", new Pawn(PieceColor.BLACK, board.getSquare("g7")));
        setPiece("h7", new Pawn(PieceColor.BLACK, board.getSquare("h7")));
        setPiece("h4", new Queen(PieceColor.BLACK, board.getSquare("h4"))); // Checkmate position

        // Test checkmate detection
        assertTrue(checkmateDetector.isCheckMate(PieceColor.WHITE), "White should be checkmated by Fool's Mate");
        assertFalse(checkmateDetector.isCheckMate(PieceColor.BLACK), "Black should not be in checkmate");
    }

    @Test
    void testBackRankMate() {
        // Common checkmate pattern where rook delivers checkmate on back rank
        clearBoard();

        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("f1", new Rook(PieceColor.WHITE, board.getSquare("f1")));
        setPiece("g1", new Rook(PieceColor.WHITE, board.getSquare("g1")));
        setPiece("d2", new Pawn(PieceColor.WHITE, board.getSquare("d2")));
        setPiece("e2", new Pawn(PieceColor.WHITE, board.getSquare("e2")));
        setPiece("f2", new Pawn(PieceColor.WHITE, board.getSquare("f2")));

        // Black pieces
        setPiece("h8", new King(PieceColor.BLACK, board.getSquare("h8")));
        setPiece("a1", new Rook(PieceColor.BLACK, board.getSquare("a1"))); // Delivers checkmate

        assertTrue(checkmateDetector.isCheckMate(PieceColor.WHITE), "White should be checkmated by back rank mate");
    }

    @Test
    void testSmotheredMate() {
        // Classic knight checkmate where king is surrounded by own pieces
        clearBoard();

        // White pieces
        setPiece("h1", new King(PieceColor.WHITE, board.getSquare("h1")));
        setPiece("g1", new Rook(PieceColor.WHITE, board.getSquare("g1")));
        setPiece("h2", new Pawn(PieceColor.WHITE, board.getSquare("h2")));
        setPiece("g2", new Pawn(PieceColor.WHITE, board.getSquare("g2")));

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("f2", new Knight(PieceColor.BLACK, board.getSquare("f2"))); // Delivers checkmate

        assertTrue(checkmateDetector.isCheckMate(PieceColor.WHITE), "White should be checkmated by smothered mate");
    }

    @Test
    void testScholarsMate() {
        // Real opening leading to quick checkmate (1.e4 e5 2.Bc4 Nc6 3.Qh5 Nf6?? 4.Qxf7#)
        clearBoard();

        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("c4", new Bishop(PieceColor.WHITE, board.getSquare("c4")));
        setPiece("e4", new Pawn(PieceColor.WHITE, board.getSquare("e4")));
        setPiece("f7", new Queen(PieceColor.WHITE, board.getSquare("f7"))); // Delivers checkmate

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("d8", new Queen(PieceColor.BLACK, board.getSquare("d8")));
        setPiece("c6", new Knight(PieceColor.BLACK, board.getSquare("c6")));
        setPiece("e5", new Pawn(PieceColor.BLACK, board.getSquare("e5")));
        setPiece("f6", new Knight(PieceColor.BLACK, board.getSquare("f6")));

        assertTrue(checkmateDetector.isCheckMate(PieceColor.BLACK), "Black should be checkmated by Scholar's mate");
    }

    @Test
    void testCheckButNotCheckmate_PieceCanBlockCheck() {
        // Setup where a piece can block the check
        clearBoard();

        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("d1", new Queen(PieceColor.WHITE, board.getSquare("d1"))); // Can block check
        setPiece("f2", new Pawn(PieceColor.WHITE, board.getSquare("f2")));

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("e3", new Rook(PieceColor.BLACK, board.getSquare("e3"))); // Giving check

        assertFalse(checkmateDetector.isCheckMate(PieceColor.WHITE), "Not checkmate - Queen can block check");
    }

    @Test
    void testCheckButNotCheckmate_KingCanEscape() {
        // Setup where king can move to escape check
        clearBoard();

        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("a1", new Rook(PieceColor.BLACK, board.getSquare("a1"))); // Giving check

        assertFalse(checkmateDetector.isCheckMate(PieceColor.WHITE), "Not checkmate - King can move to d2, e2, or f2");
    }

    @Test
    void testCheckButNotCheckmate_AttackingPieceCanBeCaptured() {
        // Setup where checking piece can be captured
        clearBoard();

        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("d2", new Queen(PieceColor.WHITE, board.getSquare("d2"))); // Can capture rook

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("e2", new Rook(PieceColor.BLACK, board.getSquare("e2"))); // Giving check but can be captured

        assertFalse(checkmateDetector.isCheckMate(PieceColor.WHITE), "Not checkmate - Queen can capture the Rook");
    }

    @Test
    void testLegalMovesInCheck() {
        // Test that a king in check can only make moves that get out of check
        clearBoard();

        // White pieces
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("h1", new Rook(PieceColor.WHITE, board.getSquare("h1")));

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("e2", new Queen(PieceColor.BLACK, board.getSquare("e2"))); // Giving check

        // Not checkmate because king can move to d1 or f1
        assertFalse(checkmateDetector.isCheckMate(PieceColor.WHITE), "Not checkmate - King can move to d1 or f1");
    }

    @Test
    void testCheckmateByDoublePieces() {
        // Setup where two pieces cooperate to deliver checkmate
        clearBoard();

        // White pieces
        setPiece("g1", new King(PieceColor.WHITE, board.getSquare("g1")));
        setPiece("g3", new Pawn(PieceColor.WHITE, board.getSquare("g3")));
        setPiece("h2", new Pawn(PieceColor.WHITE, board.getSquare("h2")));

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("f1", new Rook(PieceColor.BLACK, board.getSquare("f1"))); // Controls h-file
        setPiece("h3", new Bishop(PieceColor.BLACK, board.getSquare("h3"))); // Controls diagonal

        assertTrue(checkmateDetector.isCheckMate(PieceColor.WHITE), "White should be checkmated by Rook and Bishop");
    }

    @Test
    void testAnastasiaMate() {
        // Famous checkmate pattern with Knight and Rook
        clearBoard();

        // White pieces
        setPiece("h1", new King(PieceColor.WHITE, board.getSquare("h1")));
        setPiece("g2", new Pawn(PieceColor.WHITE, board.getSquare("g2")));
        setPiece("h2", new Pawn(PieceColor.WHITE, board.getSquare("h2")));

        // Black pieces
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("g3", new Knight(PieceColor.BLACK, board.getSquare("g3"))); // Controls escape squares
        setPiece("a1", new Rook(PieceColor.BLACK, board.getSquare("a1"))); // Delivers check

        assertTrue(checkmateDetector.isCheckMate(PieceColor.WHITE), "White should be checkmated by Anastasia's mate");
    }

    // Helper method to clear the board for more focused tests
    private void clearBoard() {
        board.getWhitePieces().clear();
        board.getBlackPieces().clear();
        Square[][] squares = board.getSquareArray();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = squares[i][j];
                square.setOccupyingPiece(null);
            }
        }
    }

}
