package ui;

import chess.model.Square;
import chess.model.enums.PieceColor;

import javax.swing.*;
import java.awt.*;

public class SquareRendering extends JComponent {

    private Square square;

    public Square getSquare() {
        return square;
    }

    public SquareRendering(Square square) {
        this.square = square;
        this.setBorder(BorderFactory.createEmptyBorder());
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (square.getColor() == PieceColor.WHITE) {
            g.setColor(new Color(221, 192, 127));  // White square color
        } else {
            g.setColor(new Color(101, 67, 33));  // Black square color
        }

        g.fillRect(0, 0, getWidth(), getHeight());  // Use the actual width and height of the component

        if (square.getOccupyingPiece() != null && square.getDisplay()) {
            PieceRendering pieceRendering = new PieceRendering(square.getOccupyingPiece());
            pieceRendering.draw(g, getWidth(), getHeight());
        }
    }


}
