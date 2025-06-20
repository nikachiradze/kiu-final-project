package chess.common.movement;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Rook;
import chess.model.Square;
import Enums.PieceColor;

import java.util.LinkedList;
import java.util.List;

public class StandardKingMovement implements MovementStrategy{

    private Piece king;

    public StandardKingMovement(Piece king) {
        this.king = king;
    }


    @Override
    public List<Square> getLegalMoves(Board board) {
        LinkedList<Square> squares = new LinkedList<Square>();

        Square[][] board1 = board.getSquareArray();

        Square position = king.getPosition();
        int x = position.getPosition().getX();
        int y = position.getPosition().getY();


        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if (!(i == 0 && k == 0)) {
                    try {
                        if (!board1[y + k][x + i].isOccupied() ||
                                board1[y + k][x + i].getOccupyingPiece().getColor()
                                        != king.getColor()) {
                            squares.add(board1[y + k][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }


        if (!king.isHasMoved()) {
            if (king.getColor() == PieceColor.WHITE) {
                // Kingside castling
                Square e1 = board.getSquare("e1");
                Square f1 = board.getSquare("f1");
                Square g1 = board.getSquare("g1");
                Square h1 = board.getSquare("h1");
                if (h1.getOccupyingPiece() instanceof Rook
                        && !h1.getOccupyingPiece().isHasMoved()
                        && !f1.isOccupied()
                        && !g1.isOccupied()){
//                        && !board.isSquareUnderThreat(e1, PieceColor.WHITE)
//                        && !board.isSquareUnderThreat(f1, PieceColor.WHITE)
//                        && !board.isSquareUnderThreat(g1, PieceColor.WHITE)) {
                    squares.add(g1); // King would move to g1
                }

                // Queenside castling
                Square d1 = board.getSquare("d1");
                Square c1 = board.getSquare("c1");
                Square b1 = board.getSquare("b1");
                Square a1 = board.getSquare("a1");
                if (a1.getOccupyingPiece() instanceof Rook
                        && !a1.getOccupyingPiece().isHasMoved()
                        && !d1.isOccupied()
                        && !c1.isOccupied()
                        && !b1.isOccupied()){
//                        && !board.isSquareUnderThreat(e1, PieceColor.WHITE)
//                        && !board.isSquareUnderThreat(d1, PieceColor.WHITE)
//                        && !board.isSquareUnderThreat(c1, PieceColor.WHITE)) {
                    squares.add(c1); // King would move to c1
                }
            } else {
                // Kingside castling
                Square e8 = board.getSquare("e8");
                Square f8 = board.getSquare("f8");
                Square g8 = board.getSquare("g8");
                Square h8 = board.getSquare("h8");
                if (h8.getOccupyingPiece() instanceof Rook
                        && !h8.getOccupyingPiece().isHasMoved()
                        && !f8.isOccupied()
                        && !g8.isOccupied()){
//                        && !board.isSquareUnderThreat(e8, PieceColor.BLACK)
//                        && !board.isSquareUnderThreat(f8, PieceColor.BLACK)
//                        && !board.isSquareUnderThreat(g8, PieceColor.BLACK)) {
                    squares.add(g8); // King would move to g8
                }

                // Queenside castling
                Square d8 = board.getSquare("d8");
                Square c8 = board.getSquare("c8");
                Square b8 = board.getSquare("b8");
                Square a8 = board.getSquare("a8");
                if (a8.getOccupyingPiece() instanceof Rook
                        && !a8.getOccupyingPiece().isHasMoved()
                        && !d8.isOccupied()
                        && !c8.isOccupied()
                        && !b8.isOccupied()){
//                        && !board.isSquareUnderThreat(e8, PieceColor.BLACK)
//                        && !board.isSquareUnderThreat(d8, PieceColor.BLACK)
//                        && !board.isSquareUnderThreat(c8, PieceColor.BLACK)) {
                    squares.add(c8); // King would move to c8
                }
            }


            return squares;
        }

        return squares;
    }

}
