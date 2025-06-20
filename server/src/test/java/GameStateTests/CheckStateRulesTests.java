package GameStateTests;

import chess.common.checkmateDetector.CheckmateDetector;
import chess.common.checkmateDetector.StandardCheckmateDetector;
import chess.model.*;
import Enums.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckStateRulesTests {

    private Board board;
    private CheckmateDetector checkDetector;

    @BeforeEach
    void setUp() {
        board = new Board();
        // Clear board
        for (Square[] row : board.getSquareArray()) {
            for (Square square : row) {
                square.setOccupyingPiece(null);
            }
        }
        board.getWhitePieces().clear();
        board.getBlackPieces().clear();

        checkDetector = new StandardCheckmateDetector(board);
    }

    @Test
    void testWhiteKingInCheckByRook() {
        Square kingSquare = board.getSquare("e1");
        King whiteKing = new King(PieceColor.WHITE, kingSquare);
        kingSquare.setOccupyingPiece(whiteKing);

        Square rookSquare = board.getSquare("e8");
        Rook blackRook = new Rook(PieceColor.BLACK, rookSquare);
        rookSquare.setOccupyingPiece(blackRook);

        board.getWhitePieces().add(whiteKing);
        board.getBlackPieces().add(blackRook);

        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingNotInCheck() {
        Square kingSquare = board.getSquare("e1");
        King whiteKing = new King(PieceColor.WHITE, kingSquare);
        kingSquare.setOccupyingPiece(whiteKing);

        Square rookSquare = board.getSquare("a8");
        Rook blackRook = new Rook(PieceColor.BLACK, rookSquare);
        rookSquare.setOccupyingPiece(blackRook);

        board.getWhitePieces().add(whiteKing);
        board.getBlackPieces().add(blackRook);

        assertFalse(checkDetector.isInCheck(PieceColor.WHITE));
    }


    @Test
    void testWhiteKingNotInCheckByRookFarAway() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("a8", new Rook(PieceColor.BLACK, board.getSquare("a8")));
        assertFalse(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingInCheckByBishop() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("h4", new Bishop(PieceColor.BLACK, board.getSquare("h4")));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingInCheckByQueen() {
        setPiece("d1", new King(PieceColor.WHITE, board.getSquare("d1")));
        setPiece("d8", new Queen(PieceColor.BLACK, board.getSquare("d8")));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingInCheckByKnight() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("f3", new Knight(PieceColor.BLACK, board.getSquare("f3")));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingInCheckByPawn() {
        setPiece("e4", new King(PieceColor.WHITE, board.getSquare("e4")));
        setPiece("d5", new Pawn(PieceColor.BLACK, board.getSquare("d5")));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testBlackKingInCheckByWhiteQueen() {
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("e4", new Queen(PieceColor.WHITE, board.getSquare("e4")));
        assertTrue(checkDetector.isInCheck(PieceColor.BLACK));
    }

    @Test
    void testWhiteKingInDoubleCheck() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("e8", new Rook(PieceColor.BLACK, board.getSquare("e8")));
        setPiece("a5", new Bishop(PieceColor.BLACK, board.getSquare("a5")));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingSurroundedButNotInCheck() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1")));
        setPiece("d1", new Pawn(PieceColor.WHITE, board.getSquare("d1")));
        setPiece("f1", new Pawn(PieceColor.WHITE, board.getSquare("f1")));
        assertFalse(checkDetector.isInCheck(PieceColor.WHITE));
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

}
