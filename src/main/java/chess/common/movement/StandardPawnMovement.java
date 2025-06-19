package chess.common.movement;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;
import chess.model.enums.PieceColor;

import java.util.LinkedList;
import java.util.List;

public class StandardPawnMovement implements MovementStrategy{


    private final Piece pawn;

    private final boolean wasMoved;

    public StandardPawnMovement(Piece pawn, boolean wasMoved) {
        this.pawn = pawn;
        this.wasMoved = wasMoved;
    }


    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        List<Square> legalMoves = new LinkedList<>();
        Square[][] board = chessBoard.getSquareArray();
        Square position = pawn.getPosition();
        int x = position.getPosition().getX();
        int y = position.getPosition().getY();
        PieceColor color = pawn.getColor();

        int dir = (color == PieceColor.WHITE) ? -1 : 1;

        if (y + dir >= 0 && y + dir < 8 && !board[y + dir][x].isOccupied()) {
            legalMoves.add(board[y + dir][x]);

            if (!wasMoved && y + 2 * dir >= 0 && y + 2 * dir < 8 && !board[y + 2 * dir][x].isOccupied()) {
                legalMoves.add(board[y + 2 * dir][x]);
            }
        }

        // Diagonal captures
        for (int dx : new int[]{-1, 1}) {
            int nx = x + dx;
            int ny = y + dir;

            if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
                Square diag = board[ny][nx];
                if (diag.isOccupied() && diag.getOccupyingPiece().getColor() != color) {
                    legalMoves.add(diag);
                }
            }
        }

        return legalMoves;
    }


}
