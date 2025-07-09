package chess.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameInfo {

    private String id;
    private String whitePlayer;
    private String blackPlayer;
    private String moves;
    private String result;
}
