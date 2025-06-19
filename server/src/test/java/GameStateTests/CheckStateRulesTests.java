package GameStateTests;

import chess.common.checkmateDetector.CheckmateDetector;
import chess.common.checkmateDetector.StandardCheckmateDetector;
import chess.model.*;
import chess.model.enums.PieceColor;
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
        King whiteKing = new King(PieceColor.WHITE, kingSquare, "/wking.png");
        kingSquare.setOccupyingPiece(whiteKing);

        Square rookSquare = board.getSquare("e8");
        Rook blackRook = new Rook(PieceColor.BLACK, rookSquare, "/brook.png");
        rookSquare.setOccupyingPiece(blackRook);

        board.getWhitePieces().add(whiteKing);
        board.getBlackPieces().add(blackRook);

        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingNotInCheck() {
        Square kingSquare = board.getSquare("e1");
        King whiteKing = new King(PieceColor.WHITE, kingSquare, "/wking.png");
        kingSquare.setOccupyingPiece(whiteKing);

        Square rookSquare = board.getSquare("a8");
        Rook blackRook = new Rook(PieceColor.BLACK, rookSquare, "/brook.png");
        rookSquare.setOccupyingPiece(blackRook);

        board.getWhitePieces().add(whiteKing);
        board.getBlackPieces().add(blackRook);

        assertFalse(checkDetector.isInCheck(PieceColor.WHITE));
    }


    @Test
    void testWhiteKingNotInCheckByRookFarAway() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1"), "/wking.png"));
        setPiece("a8", new Rook(PieceColor.BLACK, board.getSquare("a8"), "/brook.png"));
        assertFalse(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingInCheckByBishop() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1"), "/wking.png"));
        setPiece("h4", new Bishop(PieceColor.BLACK, board.getSquare("h4"), "/bbishop.png"));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingInCheckByQueen() {
        setPiece("d1", new King(PieceColor.WHITE, board.getSquare("d1"), "/wking.png"));
        setPiece("d8", new Queen(PieceColor.BLACK, board.getSquare("d8"), "/bqueen.png"));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingInCheckByKnight() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1"), "/wking.png"));
        setPiece("f3", new Knight(PieceColor.BLACK, board.getSquare("f3"), "/bknight.png"));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingInCheckByPawn() {
        setPiece("e4", new King(PieceColor.WHITE, board.getSquare("e4"), "/wking.png"));
        setPiece("d5", new Pawn(PieceColor.BLACK, board.getSquare("d5"), "/bpawn.png"));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testBlackKingInCheckByWhiteQueen() {
        setPiece("e8", new King(PieceColor.BLACK, board.getSquare("e8"), "/bking.png"));
        setPiece("e4", new Queen(PieceColor.WHITE, board.getSquare("e4"), "/wqueen.png"));
        assertTrue(checkDetector.isInCheck(PieceColor.BLACK));
    }

    @Test
    void testWhiteKingInDoubleCheck() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1"), "/wking.png"));
        setPiece("e8", new Rook(PieceColor.BLACK, board.getSquare("e8"), "/brook.png"));
        setPiece("a5", new Bishop(PieceColor.BLACK, board.getSquare("a5"), "/bbishop.png"));
        assertTrue(checkDetector.isInCheck(PieceColor.WHITE));
    }

    @Test
    void testWhiteKingSurroundedButNotInCheck() {
        setPiece("e1", new King(PieceColor.WHITE, board.getSquare("e1"), "/wking.png"));
        setPiece("d1", new Pawn(PieceColor.WHITE, board.getSquare("d1"), "/wpawn.png"));
        setPiece("f1", new Pawn(PieceColor.WHITE, board.getSquare("f1"), "/wpawn.png"));
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
