package chess.common.checkmateDetector;

import chess.model.enums.PieceColor;


// already exist CheckMateDetector Should Have clean interface
public interface CheckmateDetector {
    boolean isInCheck(PieceColor color);
    boolean isCheckMate(PieceColor color);

    boolean isStalemate(PieceColor color);

}
