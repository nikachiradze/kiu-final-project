package pieceMovement;

import chess.model.Board;
import chess.model.King;
import chess.model.Rook;
import chess.model.enums.PieceColor;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KingMovementTests {

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
    void testKingCentralPosition() {
        Square from = board.getSquare("d4");
        King king = new King(PieceColor.WHITE, from, "/wking.png");
        from.setOccupyingPiece(king);

        List<Square> moves = king.getLegalMoves(board);

        String[] expectedMoves = {
                "c3", "c4", "c5",
                "d3",       "d5",
                "e3", "e4", "e5"
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(8, moves.size());
    }

    @Test
    void testKingCornerPosition() {
        Square from = board.getSquare("a1");
        King king = new King(PieceColor.WHITE, from, "/wking.png");
        from.setOccupyingPiece(king);

        List<Square> moves = king.getLegalMoves(board);

        String[] expectedMoves = {
                "a2", "b1", "b2"
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(3, moves.size());
    }

    @Test
    void testKingBlockedByFriendlyPieces() {
        Square from = board.getSquare("d4");
        King king = new King(PieceColor.WHITE, from, "/wking.png");
        from.setOccupyingPiece(king);

        String[] friendlyPositions = {
                "c3", "c4", "c5",
                "d3",       "d5",
                "e3", "e4", "e5"
        };

        for (String pos : friendlyPositions) {
            Square square = board.getSquare(pos);
            square.setOccupyingPiece(new King(PieceColor.WHITE, square, "/wking.png"));
        }

        List<Square> moves = king.getLegalMoves(board);
        assertEquals(0, moves.size(), "King shouldn't be able to move onto friendly pieces");
    }

    @Test
    void testKingCanCaptureEnemies() {
        Square from = board.getSquare("d4");
        King king = new King(PieceColor.WHITE, from, "/wking.png");
        from.setOccupyingPiece(king);

        String[] enemyPositions = {
                "c3", "c4", "c5",
                "d3",       "d5",
                "e3", "e4", "e5"
        };

        for (String pos : enemyPositions) {
            Square square = board.getSquare(pos);
            square.setOccupyingPiece(new King(PieceColor.BLACK, square, "/bking.png"));
        }

        List<Square> moves = king.getLegalMoves(board);

        for (String pos : enemyPositions) {
            assertTrue(moves.contains(board.getSquare(pos)), "King should be able to capture at " + pos);
        }

        assertEquals(8, moves.size());
    }

    @Test
    void testKingAtBoardEdge() {
        Square from = board.getSquare("h5");
        King king = new King(PieceColor.WHITE, from, "/wking.png");
        from.setOccupyingPiece(king);

        List<Square> moves = king.getLegalMoves(board);

        String[] expectedMoves = {
                "g4", "g5", "g6",
                "h4",       "h6"
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(5, moves.size());
    }

    @Test
    void testKingSurroundedByMixedPieces() {
        Square from = board.getSquare("d4");
        King king = new King(PieceColor.WHITE, from, "/wking.png");
        from.setOccupyingPiece(king);

        String[] friendly = { "c3", "c4", "d3", "e3" };
        String[] enemies = { "c5", "d5", "e4", "e5" };

        for (String pos : friendly) {
            Square square = board.getSquare(pos);
            square.setOccupyingPiece(new King(PieceColor.WHITE, square, "/wking.png"));
        }

        for (String pos : enemies) {
            Square square = board.getSquare(pos);
            square.setOccupyingPiece(new King(PieceColor.BLACK, square, "/bking.png"));
        }

        List<Square> moves = king.getLegalMoves(board);

        for (String pos : enemies) {
            assertTrue(moves.contains(board.getSquare(pos)), "King should capture enemy at " + pos);
        }

        for (String pos : friendly) {
            assertFalse(moves.contains(board.getSquare(pos)), "King should not move to friendly at " + pos);
        }

        assertEquals(4, moves.size());
    }


    @Test
    void testWhiteKingsideCastle() {
        Square kingSquare = board.getSquare("e1");
        Square rookSquare = board.getSquare("h1");
        King king = new King(PieceColor.WHITE, kingSquare, "/wking.png");
        Rook rook = new Rook(PieceColor.WHITE, rookSquare, "/wrook.png");

        kingSquare.setOccupyingPiece(king);
        rookSquare.setOccupyingPiece(rook);
        board.getWhitePieces().add(king);
        board.getWhitePieces().add(rook);

        List<Square> moves = king.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquare("g1")), "White kingside castling to g1 should be allowed");
    }

    @Test
    void testWhiteQueensideCastle() {
        Square kingSquare = board.getSquare("e1");
        Square rookSquare = board.getSquare("a1");
        King king = new King(PieceColor.WHITE, kingSquare, "/wking.png");
        Rook rook = new Rook(PieceColor.WHITE, rookSquare, "/wrook.png");

        kingSquare.setOccupyingPiece(king);
        rookSquare.setOccupyingPiece(rook);
        board.getWhitePieces().add(king);
        board.getWhitePieces().add(rook);

        List<Square> moves = king.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquare("c1")), "White queenside castling to c1 should be allowed");
    }

    @Test
    void testCannotCastleIfPathBlocked() {
        Square kingSquare = board.getSquare("e1");
        Square rookSquare = board.getSquare("h1");
        Square blocking = board.getSquare("f1");

        King king = new King(PieceColor.WHITE, kingSquare, "/wking.png");
        Rook rook = new Rook(PieceColor.WHITE, rookSquare, "/wrook.png");
        Rook blocker = new Rook(PieceColor.WHITE, blocking, "/wrook.png");

        kingSquare.setOccupyingPiece(king);
        rookSquare.setOccupyingPiece(rook);
        blocking.setOccupyingPiece(blocker);
        board.getWhitePieces().add(king);
        board.getWhitePieces().add(rook);
        board.getWhitePieces().add(blocker);

        List<Square> moves = king.getLegalMoves(board);
        assertFalse(moves.contains(board.getSquare("g1")), "Kingside castling should not be allowed if path is blocked");
    }

    @Test
    void testCannotCastleIfInCheck() {
        Square kingSquare = board.getSquare("e1");
        Square rookSquare = board.getSquare("h1");
        Square enemy = board.getSquare("e8");

        King king = new King(PieceColor.WHITE, kingSquare, "/wking.png");
        Rook rook = new Rook(PieceColor.WHITE, rookSquare, "/wrook.png");
        Rook blackRook = new Rook(PieceColor.BLACK, enemy, "/brook.png");

        kingSquare.setOccupyingPiece(king);
        rookSquare.setOccupyingPiece(rook);
        enemy.setOccupyingPiece(blackRook);
        board.getWhitePieces().add(king);
        board.getWhitePieces().add(rook);
        board.getBlackPieces().add(blackRook);

        List<Square> moves = king.getLegalMoves(board);
        assertFalse(moves.contains(board.getSquare("g1")), "Castling not allowed while in check");
    }



}
