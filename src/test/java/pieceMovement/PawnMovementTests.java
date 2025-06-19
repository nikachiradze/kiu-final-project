package pieceMovement;

import chess.model.Board;
import chess.model.Pawn;
import chess.model.Square;
import chess.model.enums.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PawnMovementTests {

    private Board board;

    @BeforeEach
    void setUp(){
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
    void testPawnLegalMoveForward() {

        Square from = board.getSquare("e2"); // e2
        Pawn pawn = new Pawn(PieceColor.WHITE,from,"/wpawn.png");
        from.setOccupyingPiece(pawn);
        List<Square> moves = pawn.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquare("e3"))); // e3
        assertTrue(moves.contains(board.getSquare("e4"))); // e4
    }

    @Test
    void testPawnBlockedByPiece() {
        Square from = board.getSquare("e2");
        Pawn pawn = new Pawn(PieceColor.WHITE, from, "/wpawn.png");
        from.setOccupyingPiece(pawn);

        Square blockingSquare = board.getSquare("e3");
        Pawn blocker = new Pawn(PieceColor.WHITE, blockingSquare, "/wpawn.png");
        blockingSquare.setOccupyingPiece(blocker);

        List<Square> moves = pawn.getLegalMoves(board);
        assertFalse(moves.contains(blockingSquare), "Pawn should not move forward if blocked");
    }

    @Test
    void testPawnCanCaptureDiagonally() {
        Square from = board.getSquare("d4");
        Pawn pawn = new Pawn(PieceColor.WHITE, from, "/wpawn.png");
        from.setOccupyingPiece(pawn);

        Square enemySquare = board.getSquare("e5");
        Pawn enemy = new Pawn(PieceColor.BLACK, enemySquare, "/bpawn.png");
        enemySquare.setOccupyingPiece(enemy);

        Square leftEnemySquare = board.getSquare("c5");
        Pawn leftEnemy = new Pawn(PieceColor.BLACK, leftEnemySquare, "/bpawn.png");
        leftEnemySquare.setOccupyingPiece(leftEnemy);

        List<Square> moves = pawn.getLegalMoves(board);

        assertTrue(moves.contains(enemySquare), "Pawn should be able to capture diagonally to the right");
        assertTrue(moves.contains(leftEnemySquare), "Pawn should be able to capture diagonally to the left");
    }

    @Test
    void testPawnCannotMoveBackward() {
        Square from = board.getSquare("d4");
        Pawn pawn = new Pawn(PieceColor.WHITE, from, "/wpawn.png");
        from.setOccupyingPiece(pawn);

        List<Square> moves = pawn.getLegalMoves(board);

        assertFalse(moves.contains(board.getSquare("d3")), "White pawn should not move backward");
    }

    @Test
    void testBlackPawnInitialMove() {
        Square from = board.getSquare("d7"); // black pawn position
        Pawn pawn = new Pawn(PieceColor.BLACK, from, "/bpawn.png");
        from.setOccupyingPiece(pawn);

        List<Square> moves = pawn.getLegalMoves(board);

        assertTrue(moves.contains(board.getSquare("d6")));
        assertTrue(moves.contains(board.getSquare("d5")));
    }


    @Test
    void testBlackPawnBlocked() {
        Square from = board.getSquare("d7");
        Pawn pawn = new Pawn(PieceColor.BLACK, from, "/bpawn.png");
        from.setOccupyingPiece(pawn);

        Square blocking = board.getSquare("d6");
        blocking.setOccupyingPiece(new Pawn(PieceColor.WHITE, blocking, "/wpawn.png")); // white pawn blocks

        List<Square> moves = pawn.getLegalMoves(board);
        assertFalse(moves.contains(blocking), "Black pawn should not move forward when blocked");
    }



    @Test
    void testPawnCannotCaptureEmptyDiagonal() {
        Square from = board.getSquare("d4");
        Pawn pawn = new Pawn(PieceColor.WHITE, from, "/wpawn.png");
        from.setOccupyingPiece(pawn);

        List<Square> moves = pawn.getLegalMoves(board);

        assertFalse(moves.contains(board.getSquare("e5")), "Pawn should not move diagonally if no enemy");
        assertFalse(moves.contains(board.getSquare("c5")), "Pawn should not move diagonally if no enemy");
    }

    @Test
    void testBlackPawnCannotDoubleMoveAfterFirstMove() {
        Square from = board.getSquare("e7"); // not initial rank
        Pawn pawn = new Pawn(PieceColor.BLACK, from, "/bpawn.png");
        from.setOccupyingPiece(pawn);

        pawn.move(board.getSquare("e6"),board);

        List<Square> moves = pawn.getLegalMoves(board);

        assertTrue(moves.contains(board.getSquare("e5"))); // can still move one forward
        assertFalse(moves.contains(board.getSquare("e4"))); // double move not allowed
    }


    @Test
    void testPawnCannotCaptureFriendlyPiece() {
        Square from = board.getSquare("d4");
        Pawn pawn = new Pawn(PieceColor.WHITE, from, "/wpawn.png");
        from.setOccupyingPiece(pawn);

        Square rightAlly = board.getSquare("e5");
        rightAlly.setOccupyingPiece(new Pawn(PieceColor.WHITE, rightAlly, "/wpawn.png"));

        Square leftAlly = board.getSquare("c5");
        leftAlly.setOccupyingPiece(new Pawn(PieceColor.WHITE, leftAlly, "/wpawn.png"));

        List<Square> moves = pawn.getLegalMoves(board);

        assertFalse(moves.contains(rightAlly), "Pawn should not capture friendly piece to the right");
        assertFalse(moves.contains(leftAlly), "Pawn should not capture friendly piece to the left");
    }


    @Test
    void testPawnCannotMoveOffBoard() {
        Square from = board.getSquare("a8");
        Pawn pawn = new Pawn(PieceColor.WHITE, from, "/wpawn.png");
        from.setOccupyingPiece(pawn);

        List<Square> moves = pawn.getLegalMoves(board);

        assertTrue(moves.isEmpty(), "Pawn should have no legal moves off board at a8");
    }








}
