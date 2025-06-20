package ui;

import DTOs.BoardDTO;
import DTOs.PieceDTO;
import DTOs.SquareDTO;
import Enums.PieceColor;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoardRendering extends JPanel{
    private  BoardDTO chessBoard;
    @Setter
    private int currX, currY;

    private SquareRendering[][] squareRenderings = new SquareRendering[8][8];

//    private final GameController gameController;

    public BoardRendering(BoardDTO board, GameWindow gameWindow) {
        this.chessBoard = board;
//        this.gameController = new GameController(board, gameWindow);

        setLayout(new GridLayout(8, 8, 0, 0));
        setPreferredSize(new Dimension(400, 400));
//        MouseAdapter mouseAdapter = new BoardMouseHandler(gameController,this);
//        addMouseListener(mouseAdapter);
//        addMouseMotionListener(mouseAdapter);

        drawInitialSquares();
    }

    private void drawInitialSquares() {
        SquareDTO[][] squares = chessBoard.getBoard();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                SquareRendering sqr = new SquareRendering(squares[y][x]);
                squareRenderings[y][x] = sqr;
                add(new SquareRendering(squares[y][x]));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        SquareDTO[][] squares = chessBoard.getBoard();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                squares[y][x].setDisplay(true);
            }
        }

        PieceDTO currPiece = chessBoard.getCurrPiece();
        if (currPiece != null) {
            if ((currPiece.getColor() == PieceColor.WHITE && chessBoard.isWhiteTurn()) ||
                    (currPiece.getColor() == PieceColor.BLACK && !chessBoard.isWhiteTurn())) {

                Image img = null;
                try {
                    PieceRendering pieceRendering = new PieceRendering(currPiece);
                    img = pieceRendering.getImage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (img != null) {
                    g.drawImage(img, currX, currY, null);
                }
            }
        }
    }

    public void updateBoard(BoardDTO newBoard) {
        this.chessBoard = newBoard;

        this.removeAll(); // 🧼 Clear all old squares

        SquareDTO[][] squares = newBoard.getBoard();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                SquareRendering sr = new SquareRendering(squares[y][x]);
                squareRenderings[y][x] = sr;
                this.add(sr);
            }
        }

        this.revalidate(); // 🔄 Re-layout new components
        this.repaint();    // 🎨 Trigger re-draw
    }


//    private void setImagesOnPieces(){
//        Square[][] board = chessBoard.getSquareArray();
//        // pawns
//        for (int x = 0; x < 8; x++) {
//            board[1][x].getOccupyingPiece()
//            board[6][x].put(new Pawn(PieceColor.WHITE, board[6][x], getImage(PieceColor.WHITE, Pawn.class)));
//        }
//
//        // Kings & Queens
//        board[0][4].put(new King(PieceColor.BLACK, board[0][4], getImage(PieceColor.BLACK, King.class)));
//        board[7][4].put(new King(PieceColor.WHITE, board[7][4], getImage(PieceColor.WHITE, King.class)));
//
//        board[0][3].put(new Queen(PieceColor.BLACK, board[0][3], getImage(PieceColor.BLACK, Queen.class)));
//        board[7][3].put(new Queen(PieceColor.WHITE, board[7][3], getImage(PieceColor.WHITE, Queen.class)));
//
//        // Rooks
//        board[0][0].put(new Rook(PieceColor.BLACK, board[0][0], getImage(PieceColor.BLACK, Rook.class)));
//        board[0][7].put(new Rook(PieceColor.BLACK, board[0][7], getImage(PieceColor.BLACK, Rook.class)));
//        board[7][0].put(new Rook(PieceColor.WHITE, board[7][0], getImage(PieceColor.WHITE, Rook.class)));
//        board[7][7].put(new Rook(PieceColor.WHITE, board[7][7], getImage(PieceColor.WHITE, Rook.class)));
//
//
//    }
//
//    private void setImagesOnPawns(){
//        Square[][] squares = board.getSquareArray();
//        for (int x = 0; x < 8; x++) {
//            squares[1][x].put(new Pawn(PieceColor.BLACK, squares[1][x], getImage(PieceColor.BLACK, Pawn.class)));
//            squares[6][x].put(new Pawn(PieceColor.WHITE, squares[6][x], getImage(PieceColor.WHITE, Pawn.class)));
//        }
//    }
//    private void setImagesOnRooks(){
//        Square[][] squares = board.getSquareArray();
//        for (int x = 0; x < 8; x++) {
//            squares[1][x].put(new Pawn(PieceColor.BLACK, squares[1][x], getImage(PieceColor.BLACK, Pawn.class)));
//            squares[6][x].put(new Pawn(PieceColor.WHITE, squares[6][x], getImage(PieceColor.WHITE, Pawn.class)));
//        }
//    }
//    private static final Map<PieceKey, String> pieceImages = Map.ofEntries(
//            Map.entry(new PieceKey(PieceColor.WHITE, Rook.class), "/wrook.png"),
//            Map.entry(new PieceKey(PieceColor.BLACK, Rook.class), "/brook.png"),
//            Map.entry(new PieceKey(PieceColor.WHITE, Knight.class), "/wknight.png"),
//            Map.entry(new PieceKey(PieceColor.BLACK, Knight.class), "/bknight.png"),
//            Map.entry(new PieceKey(PieceColor.WHITE, Bishop.class), "/wbishop.png"),
//            Map.entry(new PieceKey(PieceColor.BLACK, Bishop.class), "/bbishop.png"),
//            Map.entry(new PieceKey(PieceColor.WHITE, Queen.class), "/wqueen.png"),
//            Map.entry(new PieceKey(PieceColor.BLACK, Queen.class), "/bqueen.png"),
//            Map.entry(new PieceKey(PieceColor.WHITE, King.class), "/wking.png"),
//            Map.entry(new PieceKey(PieceColor.BLACK, King.class), "/bking.png"),
//            Map.entry(new PieceKey(PieceColor.WHITE, Pawn.class), "/wpawn.png"),
//            Map.entry(new PieceKey(PieceColor.BLACK, Pawn.class), "/bpawn.png")
//    );
//
//    private String getImage(PieceColor color, Class<? extends Piece> pieceClass) {
//        return pieceImages.get(new PieceKey(color, pieceClass));
//    }
//
//    private record PieceKey(PieceColor color, Class<? extends Piece> type) {}



}
