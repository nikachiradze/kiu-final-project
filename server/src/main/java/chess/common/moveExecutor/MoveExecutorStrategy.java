package chess.common.moveExecutor;

import chess.model.Board;
import chess.model.Square;

public interface MoveExecutorStrategy {
    boolean executeMove(Board board, Square destination);
}
