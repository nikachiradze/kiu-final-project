package ui.mouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BoardMouseMotionListener extends MouseMotionAdapter {
    private final CustomMouseListener mouseListener;

    public BoardMouseMotionListener(CustomMouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseListener.handleMouseDragged(e);
    }
}
