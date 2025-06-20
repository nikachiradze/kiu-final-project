package controller;



import chess.common.checkmateDetector.CheckmateDetector;
import chess.common.checkmateDetector.StandardCheckmateDetector;
import chess.model.Board;
import chess.model.Move;
import chess.model.Piece;
import chess.model.Square;
import Enums.PieceColor;
import ui.GameWindow;

import java.util.List;

public class GameController {

    private final Board board;
    private final GameWindow gameWindow;

    public GameController(Board board, GameWindow gameWindow) {
        this.board = board;
        this.gameWindow = gameWindow;
    }

    public void handleMousePressed(Square square) {
        if (!square.isOccupied()) return;

        Piece piece = square.getOccupyingPiece();
        if ((piece.getColor() == PieceColor.BLACK && board.isWhiteTurn()) ||
                (piece.getColor() == PieceColor.WHITE && !board.isWhiteTurn())) {
            return;
        }

        board.setCurrPiece(piece);
        square.setDisplay(false);
    }

    public void handleMouseReleased(Square targetSquare) {
        Piece currPiece = board.getCurrPiece();
        if (currPiece == null) return;

        List<Square> legalMoves = currPiece.getLegalMoves(board);

        CheckmateDetector chd = new StandardCheckmateDetector(board);

        PieceColor color = board.getTurn()? PieceColor.BLACK : PieceColor.WHITE;

        PieceColor checkColor = board.getTurn()? PieceColor.WHITE : PieceColor.BLACK;
        if(legalMoves.contains(targetSquare)){

            Move move = new Move(currPiece,currPiece.getPosition(),targetSquare);

            if(targetSquare.isOccupied() && targetSquare.getColor() == color){
                move.setCapturePiece(targetSquare.getOccupyingPiece());
            }

            currPiece.move(targetSquare,board);
            if(chd.isInCheck(checkColor)){
                move.undo(board);
                System.out.println("check play another move");
                return;
            }





            if (chd.isCheckMate(color)){
                System.out.println("mate");
                return;
            }
            board.toggleTurn();
        }else{
            currPiece.getPosition().setDisplay(true);
        }

        board.setCurrPiece(null);
    }

}
