package chess.server.services.pgn;

import chess.model.entity.GameInfo;

//package chess.server.services.pgn;
//
public interface PgnService {

    GameInfo save(GameInfo gameInfo);

    boolean addMove(String from, String to) throws Exception;
}
