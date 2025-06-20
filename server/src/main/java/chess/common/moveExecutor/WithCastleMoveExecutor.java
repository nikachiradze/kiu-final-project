package chess.common.moveExecutor;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;
import Enums.PieceColor;

import java.util.List;


public class WithCastleMoveExecutor implements MoveExecutorStrategy{

    private Piece king;

    public WithCastleMoveExecutor(Piece king) {
        this.king = king;
    }

    @Override
    public boolean executeMove(Board board, Square destination) {
        List<Square> legalMoves = king.getLegalMoves(board);
        if (!legalMoves.contains(destination)) {
            return false;
        }

        String target = destination.getPosition().toAlgebraic();
        PieceColor color = king.getColor();

        // Handle castling moves
        switch (target) {
            case "g1" -> { // White kingside castling
                moveKingAndRook(board, "e1", "g1", "h1", "f1");
            }
            case "c1" -> { // White queenside castling
                moveKingAndRook(board, "e1", "c1", "a1", "d1");
            }
            case "g8" -> { // Black kingside castling
                moveKingAndRook(board, "e8", "g8", "h8", "f8");
            }
            case "c8" -> { // Black queenside castling
                moveKingAndRook(board, "e8", "c8", "a8", "d8");
            }
            default -> {
                // Normal king move
                Square current = king.getPosition();
                destination.put(king);
                current.setOccupyingPiece(null);
                king.setPosition(destination);
            }
        }

        king.setHasMoved(true);
        return true;
    }

    private void moveKingAndRook(Board board, String kingFrom, String kingTo, String rookFrom, String rookTo) {
        Square fromKingSquare = board.getSquare(kingFrom);
        Square toKingSquare = board.getSquare(kingTo);
        Square fromRookSquare = board.getSquare(rookFrom);
        Square toRookSquare = board.getSquare(rookTo);

        // Move King
        toKingSquare.put(king);
        fromKingSquare.setOccupyingPiece(null);
        king.setPosition(toKingSquare);

        // Move Rook
        Piece rook = fromRookSquare.getOccupyingPiece();
        toRookSquare.put(rook);
        fromRookSquare.setOccupyingPiece(null);
        rook.setPosition(toRookSquare);
        rook.setHasMoved(true);
    }


}
