package ui.abstracts;

;
import DTOs.MoveDTO;

import java.io.IOException;

public interface MoveSender {
    void sendMove(MoveDTO move) throws IOException;
}
