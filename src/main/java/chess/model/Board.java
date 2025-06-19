package chess.model;

import chess.model.enums.PieceColor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private final Square[][] board;

    @Getter
    private final LinkedList<Piece> whitePieces = new LinkedList<>();

    @Getter
    private final LinkedList<Piece> blackPieces = new LinkedList<>();


    @Getter
    private boolean whiteTurn = true;
    @Setter
    @Getter
    private Piece currPiece;


    private static final String RESOURCES_WBISHOP_PNG = "/wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = "/bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = "/wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = "/bknight.png";
    private static final String RESOURCES_WROOK_PNG = "/wrook.png";
    private static final String RESOURCES_BROOK_PNG = "/brook.png";
    private static final String RESOURCES_WKING_PNG = "/wking.png";
    private static final String RESOURCES_BKING_PNG = "/bking.png";
    private static final String RESOURCES_BQUEEN_PNG = "/bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = "/wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = "/wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = "/bpawn.png";

    public Board() {
        board = new Square[8][8];
        initializeBoardSquares();
        initializePieces();
    }







    public Square[][] getSquareArray() {
        return board;
    }

    public void toggleTurn() {
        whiteTurn = !whiteTurn;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void capturePiece(Square square, Piece capturingPiece) {
        Piece capturedPiece = square.getOccupyingPiece();
        if (capturedPiece != null) {
            if (capturedPiece.getColor() == PieceColor.BLACK) blackPieces.remove(capturedPiece);
            else whitePieces.remove(capturedPiece);
        }
        square.setOccupyingPiece(capturingPiece);
    }


    public King getWhiteKing() {
        return (King) whitePieces.stream().filter(x -> x instanceof King).findFirst().get();
    }

    public King getBlackKing() {
        return (King) blackPieces.stream().filter(x -> x instanceof King).findFirst().get();
    }




    public boolean isSquareUnderThreat(Square position, PieceColor color){
        return switch (color) {
            case WHITE -> blackPieces.stream().anyMatch(piece -> piece.getLegalMoves(this).contains(position));
            case BLACK -> whitePieces.stream().anyMatch(piece -> piece.getLegalMoves(this).contains(position));
        };
    }


    public Square getSquare(String notation) {
        if (notation.length() != 2) {
            throw new IllegalArgumentException("Invalid square notation: " + notation);
        }

        char file = notation.charAt(0); // 'a' to 'h'
        char rank = notation.charAt(1); // '1' to '8'

        int x = file - 'a';          // e.g. 'e' - 'a' = 4
        int y = 8 - Character.getNumericValue(rank); // e.g. '2' = 6

        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Invalid square position: " + notation);
        }

        return board[y][x];
    }





    private void initializePieces() {
        // Add pawns
        for (int x = 0; x < 8; x++) {
            board[1][x].put(new Pawn(PieceColor.BLACK, board[1][x], RESOURCES_BPAWN_PNG));
            board[6][x].put(new Pawn(PieceColor.WHITE, board[6][x], RESOURCES_WPAWN_PNG));
        }

        // Add kings and queens
        board[0][4].put(new King(PieceColor.BLACK, board[0][4], RESOURCES_BKING_PNG));
        board[7][4].put(new King(PieceColor.WHITE, board[7][4], RESOURCES_WKING_PNG));
        board[0][3].put(new Queen(PieceColor.BLACK, board[0][3], RESOURCES_BQUEEN_PNG));
        board[7][3].put(new Queen(PieceColor.WHITE, board[7][3], RESOURCES_WQUEEN_PNG));

        // Add other pieces (rooks, knights, bishops)
        board[0][0].put(new Rook(PieceColor.BLACK, board[0][0], RESOURCES_BROOK_PNG));
        board[0][7].put(new Rook(PieceColor.BLACK, board[0][7], RESOURCES_BROOK_PNG));
        board[7][0].put(new Rook(PieceColor.WHITE, board[7][0], RESOURCES_WROOK_PNG));
        board[7][7].put(new Rook(PieceColor.WHITE, board[7][7], RESOURCES_WROOK_PNG));

        board[0][1].put(new Knight(PieceColor.BLACK, board[0][1], RESOURCES_BKNIGHT_PNG));
        board[0][6].put(new Knight(PieceColor.BLACK, board[0][6], RESOURCES_BKNIGHT_PNG));
        board[7][1].put(new Knight(PieceColor.WHITE, board[7][1], RESOURCES_WKNIGHT_PNG));
        board[7][6].put(new Knight(PieceColor.WHITE, board[7][6], RESOURCES_WKNIGHT_PNG));

        board[0][2].put(new Bishop(PieceColor.BLACK, board[0][2], RESOURCES_BBISHOP_PNG));
        board[0][5].put(new Bishop(PieceColor.BLACK, board[0][5], RESOURCES_BBISHOP_PNG));
        board[7][2].put(new Bishop(PieceColor.WHITE, board[7][2], RESOURCES_WBISHOP_PNG));
        board[7][5].put(new Bishop(PieceColor.WHITE, board[7][5], RESOURCES_WBISHOP_PNG));

        // Add to Bpieces and Wpieces
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                blackPieces.add(board[y][x].getOccupyingPiece());
                whitePieces.add(board[7 - y][x].getOccupyingPiece());
            }
        }
    }

    private void initializeBoardSquares() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                PieceColor color = ((x + y) % 2 == 0) ? PieceColor.WHITE : PieceColor.BLACK;
                board[y][x] = new Square(color, x, y);
            }
        }
    }

}
