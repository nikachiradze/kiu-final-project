package chess.server.services.pgn;

import DTOs.MoveDTO;
import chess.dbcontroller.GameDbManager;
import chess.model.*;
import chess.model.entity.GameInfo;

import java.util.Arrays;
import java.util.Objects;

public class PgnServiceImplementation implements PgnService {

    private final GameDbManager dbManager;
    private final Board board;
    private final StringBuilder result = new StringBuilder();
    private int moveCounter = 1;

    public PgnServiceImplementation(GameDbManager dbManager, Board board) {
        this.dbManager = dbManager;
        this.board = board;
    }


    @Override
    public GameInfo save(GameInfo gameInfo) {
        gameInfo.setMoves(result.toString());
        dbManager.insertGame("chira", "yazo", result.toString(), Objects.requireNonNullElse(gameInfo.getResult(), "1-0"));
        return gameInfo;
    }

    @Override
    public boolean addMove(MoveDTO moveDTO) throws Exception {
       String move;
       try{
           move = buildPgnMove(moveDTO);
       }catch (Exception e){
           return false;
       }

        if(board.getTurn()){
            result.append(moveCounter).append(".").append(move).append(" ");
        }else{
            result.append(move).append(" ");
            moveCounter++;
        }
        return true;
    }



    private String buildPgnMove(MoveDTO moveDTO) {
        String from = moveDTO.getFrom();
        String to = moveDTO.getTo();
        Piece piece = getMovablePiece(moveDTO);
        Square targetSquare = getToSquare(moveDTO);

        String piecePGNSymbol = pieceToPgnSymbol(piece);
        String currentFrom = piece.getPosition().getPosition().toAlgebraic();

        if (piecePGNSymbol.equals("K") && Math.abs(from.charAt(0) - to.charAt(0)) == 2) {
            return (to.charAt(0) == 'g') ? "O-O" : "O-O-O";
        }

        StringBuilder result = new StringBuilder();
        result.append(piecePGNSymbol);

        var sameColorPieces = board.isWhiteTurn()
                ? board.getWhitePieces()
                : board.getBlackPieces();

        Piece possibleOtherPiece = sameColorPieces.stream()
                .filter(x -> x != piece && pieceToPgnSymbol(x).equals(piecePGNSymbol))
                .findFirst()
                .orElse(null);

        if (possibleOtherPiece != null && possibleOtherPiece.getLegalMoves(board)
                .stream()
                .map(x->x.getPosition().toAlgebraic())
                .toList()
                .contains(targetSquare.getPosition().toAlgebraic())) {
            String otherFrom = possibleOtherPiece.getPosition().getPosition().toAlgebraic();
            if (currentFrom.charAt(0) == otherFrom.charAt(0)) {
                result.append(currentFrom.charAt(1)); // disambiguate by rank
            } else {
                result.append(currentFrom.charAt(0)); // disambiguate by file
            }
        }

        // ♟️ Handle en passant (treated like a capture)
        boolean isEnPassant = piecePGNSymbol.equals("") &&
                !targetSquare.isOccupied() &&
                Math.abs(from.charAt(0) - to.charAt(0)) == 1;

        boolean isCapture = targetSquare.isOccupied() || isEnPassant;

        if (isCapture) {
            // For pawn captures: prepend file
            if (piecePGNSymbol.equals("")) {
                result.append(from.charAt(0));
            }
            result.append("x");
        }

        result.append(targetSquare.getPosition().toAlgebraic());

        return result.toString();



    }

    private Piece getMovablePiece(MoveDTO moveDTO) {
        if(board.getTurn()){
            return board.getWhitePieces().stream().filter(x->x.getPosition().getPosition().toAlgebraic().equals(moveDTO.getFrom())).findFirst().orElse(null);
        }else{
            return board.getBlackPieces().stream().filter(x->x.getPosition().getPosition().toAlgebraic().equals(moveDTO.getFrom())).findFirst().orElse(null);
        }
    }

    private Square getToSquare(MoveDTO moveDTO) {
        return Arrays.stream(board.getSquareArray())
                .flatMap(Arrays::stream)
                .filter(x -> x.getPosition().toAlgebraic().equals(moveDTO.getTo()))
                .findFirst()
                .orElse(null);
    }

    private String pieceToPgnSymbol(Piece piece){

       if(piece instanceof Pawn){
           return "";
       }
       if(piece instanceof Rook){
           return "R";
       }
       if(piece instanceof Knight){
           return "N";
       }
       if(piece instanceof Bishop){
           return "B";
       }
       if(piece instanceof Queen){
           return "Q";
       }
       if(piece instanceof King){
           return "K";
       }
       return "";
    }


}
