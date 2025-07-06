package chess.server.services.pgn;

import DTOs.MoveDTO;
import chess.model.entity.GameInfo;

//package chess.server.services.pgn;
//
public interface PgnService {

    GameInfo save(GameInfo gameInfo);

    boolean addMove(MoveDTO moveDTO) throws Exception;
}
