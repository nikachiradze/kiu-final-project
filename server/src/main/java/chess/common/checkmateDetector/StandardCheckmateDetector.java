package chess.common.checkmateDetector;

import chess.model.*;
import chess.model.enums.PieceColor;

import java.util.*;


public class StandardCheckmateDetector implements CheckmateDetector{

    private final Board board;

    public StandardCheckmateDetector(Board board) {
        this.board = board;


    }

    @Override
    public boolean isInCheck(PieceColor color) {
       King king = switch (color){
           case BLACK -> board.getBlackKing();
           case WHITE -> board.getWhiteKing();
       };
       var kingPosition = king.getPosition();
        return board.isSquareUnderThreat(kingPosition, color);
    }

    @Override
    public boolean isCheckMate(PieceColor color) {


        List<Move> moves = getAllPossibleMoves(color);

       boolean v = moves.stream().allMatch( move -> {
            move.getMovedPiece().move(move.getTo(),board);
            boolean b = isInCheck(color);
           System.out.println(move);
           System.out.println(b);

            move.undo(board);
            return b;
        });
       return v && isInCheck(color);
    }


    @Override
    public boolean isStalemate(PieceColor color) {
        List<Move> moves = getAllPossibleMoves(color);

        boolean v = moves.stream().allMatch( move -> {
            move.getMovedPiece().move(move.getTo(),board);
            boolean b = isInCheck(color);
            System.out.println(move);
            System.out.println(b);

            move.undo(board);
            return b;
        });
        var k = isInCheck(color);
        return v && !isInCheck(color);
    }




    private List<Move> getAllPossibleMoves(PieceColor color){
        List<Piece> pieces = switch (color){
            case BLACK -> board.getBlackPieces();
            case WHITE -> board.getWhitePieces();
        };

        return pieces.stream()
                .flatMap(piece -> piece.getLegalMoves(board).stream().filter(x->!x.isOccupied() || x.getOccupyingPiece().getColor() != color).map(square -> {
                    Square from = piece.getPosition();

                    var move = new Move(piece,from, square);
                    if(square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() != color){
                        move.setCapturePiece(square.getOccupyingPiece());
                    }
                    return move;
                }))
                .toList();
    }



}
