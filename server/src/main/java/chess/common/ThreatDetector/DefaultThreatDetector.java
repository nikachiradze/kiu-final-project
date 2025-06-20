package chess.common.ThreatDetector;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;
import Enums.PieceColor;

import java.util.List;

public class DefaultThreatDetector implements ThreatDetector{

    private Board board;
    @Override
    public boolean isSquareUnderTheThreat(Square square, PieceColor color) {
        return false;
    }

    @Override
    public List<Piece> getThreatsTo(Square square, PieceColor color) {
        return null;
    }
}
