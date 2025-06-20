package chess.common.ThreatDetector;


import chess.model.Piece;
import chess.model.Square;
import Enums.PieceColor;

import java.util.List;


public interface ThreatDetector {

    boolean isSquareUnderTheThreat(Square square, PieceColor color);
    List<Piece> getThreatsTo(Square square,PieceColor color);
}
