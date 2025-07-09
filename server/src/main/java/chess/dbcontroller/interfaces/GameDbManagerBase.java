package chess.dbcontroller.interfaces;

public interface GameDbManagerBase {
    boolean insertGame(String whitePlayer, String blackPlayer, String pgn, String result);
}
