package DTOs;

import DTOs.PieceDTO;
import DTOs.SquareDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class BoardDTO implements Serializable {
    private final SquareDTO[][] board;
    private final List<PieceDTO> whitePieces;
    private final List<PieceDTO> blackPieces;

    private boolean whiteTurn = true;

    private PieceDTO currPiece;

    private int currX;
    private int currY;

    @Getter
    private GameStatusDTO gameStatus;

    public BoardDTO(SquareDTO[][] board, List<PieceDTO> whitePieces, List<PieceDTO> blackPieces) {
        this.board = board;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
    }

}
