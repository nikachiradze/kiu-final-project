package ui.mouseListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class BoardMouseListener extends MouseAdapter {
    private final CustomMouseListener mouseListener;

    public BoardMouseListener(CustomMouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseListener.handleMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            mouseListener.handleMouseReleased(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
