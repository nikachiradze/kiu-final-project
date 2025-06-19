package chess.controller;

import chess.common.checkmateDetector.CheckmateDetector;
import chess.common.checkmateDetector.StandardCheckmateDetector;
import chess.model.Board;
import chess.model.Move;
import chess.model.Piece;
import chess.model.Square;
import chess.model.enums.PieceColor;
import chess.ui.GameWindow;

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

//        if (legalMoves.contains(targetSquare) &&
//                allowed.contains(targetSquare) &&
//                board.getCheckmateDetector().testMove(currPiece, targetSquare)) {
//
//
//            currPiece.move(targetSquare,board);
//            board.getCheckmateDetector().update();
//
//            if (board.getCheckmateDetector().blackCheckMated()) {
//                gameWindow.checkmateOccurred(0);
//            } else if (board.getCheckmateDetector().whiteCheckMated()) {
//                gameWindow.checkmateOccurred(1);
//            } else {
//                board.toggleTurn();
//            }
//        } else {
//            currPiece.getPosition().setDisplay(true);
//        }
//
//        board.setCurrPiece(null);

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
