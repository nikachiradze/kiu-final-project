package ui.mouseListener;

import java.awt.event.MouseEvent;
import java.io.IOException;

public interface CustomMouseListener {
    void handleMouseReleased(MouseEvent e) throws IOException;

    void handleMousePressed(MouseEvent e);

    void handleMouseDragged(MouseEvent e);
}
