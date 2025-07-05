package ui.mouseListener;

import DTOs.BoardDTO;
import DTOs.MoveDTO;
import ui.BoardRendering;
import ui.abstracts.MoveSender;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MouseListenerImpl implements CustomMouseListener {
    private final BoardDTO boardDTO;
    private final BoardRendering boardRendering;
    private final MoveSender moveSender;

    private Point from = null;

    public MouseListenerImpl(BoardDTO boardDTO, BoardRendering boardRendering, MoveSender moveSender) {
        this.boardDTO = boardDTO;
        this.boardRendering = boardRendering;
        this.moveSender = moveSender;
    }

    @Override
    public void handleMousePressed(MouseEvent e) {
        from = getBoardCoordinate(e.getX(), e.getY());
        if (from != null) {
            boardDTO.setCurrPiece(boardDTO.getBoard()[from.y][from.x].getOccupyingPiece());
//            boardDTO.setCurrX(e.getX());
//            boardDTO.setCurrY(e.getY());
            boardRendering.repaint();
        }
    }

    @Override
    public void handleMouseReleased(MouseEvent e) {
        if (from == null) return;

        Point to = getBoardCoordinate(e.getX(), e.getY());
        if (to == null || from.equals(to)) {
            from = null;
            boardDTO.setCurrPiece(null);
            boardRendering.repaint();
            return;
        }

        String moveStr = toAlgebraic(from) + " " + toAlgebraic(to);

        MoveDTO moveDTO = new MoveDTO();
        moveDTO.setFrom(toAlgebraic(from));
        moveDTO.setTo(toAlgebraic(to));

        try {
            moveSender.sendMove(moveDTO);
        } catch (IOException ex) {
//            ex.printStackTrace();
            // Optionally: notify user about sending failure
        }

        from = null;
        boardDTO.setCurrPiece(null);
        boardRendering.repaint();
    }

    @Override
    public void handleMouseDragged(MouseEvent e) {
        boardDTO.setCurrX(e.getX());
        boardDTO.setCurrY(e.getY());
        boardRendering.repaint();
    }

    private Point getBoardCoordinate(int xPixel, int yPixel) {
        int squareSize = 50;  // Assuming square size of 50 pixels
        int col = xPixel / squareSize;
        int row = yPixel / squareSize;

        if (col >= 0 && col < 8 && row >= 0 && row < 8) {
            return new Point(col, row);
        }
        return null;
    }

    private String toAlgebraic(Point p) {
        char file = (char) ('a' + p.x);
        char rank = (char) ('8' - p.y);
        return "" + file + rank;
    }
}
