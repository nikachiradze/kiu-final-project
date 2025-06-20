package DTOs;

import DTOs.PieceDTO;
import DTOs.SquareDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class BoardDTO{
    private final SquareDTO[][] board;
    private final List<PieceDTO> whitePieces;
    private final List<PieceDTO> blackPieces;

    private boolean whiteTurn = true;

    private PieceDTO currPiece;

    public BoardDTO(SquareDTO[][] board, List<PieceDTO> whitePieces, List<PieceDTO> blackPieces) {
        this.board = board;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
    }


}
