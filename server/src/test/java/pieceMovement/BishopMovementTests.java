package pieceMovement;

import chess.model.Board;
import chess.model.Bishop;
import chess.model.enums.PieceColor;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BishopMovementTests {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = board.getSquareArray()[i][j];
                square.setOccupyingPiece(null);
            }
        }
        board.getWhitePieces().clear();
        board.getBlackPieces().clear();
    }

    @Test
    void testBishopCentralPositionAllMovesAvailable() {
        Square from = board.getSquare("d4");  // Central position
        Bishop bishop = new Bishop(PieceColor.WHITE, from, "/wbishop.png");
        from.setOccupyingPiece(bishop);

        List<Square> moves = bishop.getLegalMoves(board);


        // Diagonal moves in all directions
        String[] expectedMoves = {"c5", "b6", "a7", "e5", "f6", "g7", "h8", "e3", "f2", "g1", "c3", "b2", "a1"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(13, moves.size());  // Total 13 possible diagonal moves from this central position
    }

    @Test
    void testBishopInCornerPosition() {
        Square from = board.getSquare("a1");  // Corner position
        Bishop bishop = new Bishop(PieceColor.WHITE, from, "/wbishop.png");
        from.setOccupyingPiece(bishop);

        List<Square> moves = bishop.getLegalMoves(board);

        assertTrue(moves.contains(board.getSquare("b2")));
        assertTrue(moves.contains(board.getSquare("c3")));
    }

    @Test
    void testBishopBlockedByFriendlyPieces() {
        Square from = board.getSquare("d4");  // Central position
        Bishop bishop = new Bishop(PieceColor.WHITE, from, "/wbishop.png");
        from.setOccupyingPiece(bishop);

        // Surround bishop with friendly pieces at diagonal positions
        String[] friendlyPositions = {"c5", "b6", "a7", "e5", "f6", "g7", "h8", "e3", "f2", "g1", "c3", "b2", "a1"};
        for (String pos : friendlyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Bishop(PieceColor.WHITE, board.getSquare(pos), "/wbishop.png"));
        }

        List<Square> moves = bishop.getLegalMoves(board);

        assertTrue(moves.isEmpty(), "Bishop shouldn't be able to move to squares occupied by friendly pieces");
    }

    @Test
    void testBishopCanCaptureEnemyPieces() {
        Square from = board.getSquare("d4");  // Central position
        Bishop bishop = new Bishop(PieceColor.WHITE, from, "/wbishop.png");
        from.setOccupyingPiece(bishop);

        // Place enemy pieces along diagonals
        String[] enemyPositions = {"c5", "e5", "c3", "e3"};
        for (String pos : enemyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Bishop(PieceColor.BLACK, board.getSquare(pos), "/bbishop.png"));
        }

        List<Square> moves = bishop.getLegalMoves(board);
        List<String> smovie = moves.stream().map(x->x.getPosition().toAlgebraic()).toList();

        // Bishop should be able to capture all enemy pieces along diagonals
        assertEquals(4, moves.size(), "Bishop should be able to capture all enemy pieces");

        for (String pos : enemyPositions) {
            assertTrue(moves.contains(board.getSquare(pos)), "Bishop should be able to capture at " + pos);
        }
    }

    @Test
    void testBlackBishopSymmetricMoves() {
        Square from = board.getSquare("e5");  // Symmetric position for Black Bishop
        Bishop bishop = new Bishop(PieceColor.BLACK, from, "/bbishop.png");
        from.setOccupyingPiece(bishop);

        List<Square> moves = bishop.getLegalMoves(board);

        // Diagonal moves in all directions
        String[] expectedMoves = {"d6", "c7", "b8", "f6", "g7", "h8", "f4", "g3", "h2", "d4", "c3", "b2", "a1"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(13, moves.size());  // Total 13 possible diagonal moves from this central position
    }

    @Test
    void testBishopAtEdgeNotInCorner() {
        Square from = board.getSquare("a4");  // Position at edge, but not corner
        Bishop bishop = new Bishop(PieceColor.WHITE, from, "/wbishop.png");
        from.setOccupyingPiece(bishop);

        List<Square> moves = bishop.getLegalMoves(board);

        // Expected diagonal moves
        String[] expectedMoves = {"b5", "c6", "b3", "c2"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

    }

    @Test
    void testBishopAtEdgeWithLimitedMoves() {
        Square from = board.getSquare("h4");
        Bishop bishop = new Bishop(PieceColor.WHITE, from, "/wbishop.png");
        from.setOccupyingPiece(bishop);

        List<Square> moves = bishop.getLegalMoves(board);

        String[] expectedMoves = {"g5", "f6", "g3", "f2"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

    }

    @Test
    void testBishopSurroundedByFriendlyPieces() {
        Square from = board.getSquare("d4");  // Central position
        Bishop bishop = new Bishop(PieceColor.WHITE, from, "/wbishop.png");
        from.setOccupyingPiece(bishop);

        String[] friendlyPositions = {"c5", "b6", "a7", "e5", "f6", "g7", "h8", "e3", "f2", "g1", "c3", "b2", "a1"};
        for (String pos : friendlyPositions) {
            board.getSquare(pos).setOccupyingPiece(new Bishop(PieceColor.WHITE, board.getSquare(pos), "/wbishop.png"));
        }

        List<Square> moves = bishop.getLegalMoves(board);

        assertTrue(moves.isEmpty(), "Bishop shouldn't be able to move to squares occupied by friendly pieces");
    }

    @Test
    void testBishopOutOfBounds() {
        Square from = board.getSquare("a1");  // Corner position
        Bishop bishop = new Bishop(PieceColor.WHITE, from, "/wbishop.png");
        from.setOccupyingPiece(bishop);

        List<Square> moves = bishop.getLegalMoves(board);

        String[] expectedMoves = {"b2", "c3"};

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(7, moves.size());
    }
}
