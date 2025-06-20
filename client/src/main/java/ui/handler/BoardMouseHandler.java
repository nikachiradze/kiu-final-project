//package ui.handler;
//
//import DTOs.SquareDTO;
//import chess.model.Square;
//import controller.GameController;
//import ui.BoardRendering;
//import ui.SquareRendering;
//
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class BoardMouseHandler extends MouseAdapter {
//
//    private final GameController controller;
//    private final BoardRendering rendering;
//
//    public BoardMouseHandler(GameController controller, BoardRendering rendering) {
//        this.controller = controller;
//        this.rendering = rendering;
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        rendering.setCurrX(e.getX());
//        rendering.setCurrY(e.getY());
//
//        SquareRendering squareRendering = (SquareRendering) rendering.getComponentAt(new Point(e.getX(), e.getY()));
//        SquareDTO square = squareRendering.getSquare();
//
//        controller.handleMousePressed(square);
//
//        rendering.repaint();
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        SquareRendering squareRendering = (SquareRendering) rendering.getComponentAt(new Point(e.getX(), e.getY()));
//        Square targetSquare = squareRendering.getSquare();
//
//        controller.handleMouseReleased(targetSquare);
//
//        rendering.repaint();
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//        rendering.setCurrX(e.getX() - 24);
//        rendering.setCurrY(e.getY() - 24);
//        rendering.repaint();
//    }
//
//
//}
