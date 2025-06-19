package chess.ui.handler;

import chess.controller.GameController;
import chess.model.Square;
import chess.ui.BoardRendering;
import chess.ui.SquareRendering;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardMouseHandler extends MouseAdapter {

    private final GameController controller;
    private final BoardRendering rendering;

    public BoardMouseHandler(GameController controller, BoardRendering rendering) {
        this.controller = controller;
        this.rendering = rendering;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        rendering.setCurrX(e.getX());
        rendering.setCurrY(e.getY());

        SquareRendering squareRendering = (SquareRendering) rendering.getComponentAt(new Point(e.getX(), e.getY()));
        Square square = squareRendering.getSquare();

        controller.handleMousePressed(square);

        rendering.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        SquareRendering squareRendering = (SquareRendering) rendering.getComponentAt(new Point(e.getX(), e.getY()));
        Square targetSquare = squareRendering.getSquare();

        controller.handleMouseReleased(targetSquare);

        rendering.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        rendering.setCurrX(e.getX() - 24);
        rendering.setCurrY(e.getY() - 24);
        rendering.repaint();
    }


}
