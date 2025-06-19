package chess.common.movement;

import chess.model.Board;
import chess.model.Square;

import java.util.List;

public interface MovementStrategy {
    List<Square> getLegalMoves(Board board);
}
