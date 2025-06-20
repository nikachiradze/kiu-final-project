package chess.common.adapter;

import DTOs.BoardDTO;
import DTOs.PieceDTO;
import DTOs.SquareDTO;
import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;

import java.util.LinkedList;
import java.util.List;

public class ModelToDtoConverter {
    public static PieceDTO convertModelToDto(Piece piece) {
        PieceDTO dto = new PieceDTO(piece.getColor(), piece.getPieceType());
        SquareDTO pos = convertModelToDto(piece.getPosition());
        dto.setPosition(pos);
        dto.setHasMoved(piece.isHasMoved());
        return dto;
    }

    public static SquareDTO convertModelToDto(Square square) {
        SquareDTO dto = new SquareDTO(square.getPosition(), square.getColor());
        dto.setPieceDTO(null);
        return dto;
    }

    public static BoardDTO convertModelToDto(Board board) {
        List<PieceDTO> whitePieces = board.getWhitePieces().stream().map(ModelToDtoConverter::convertModelToDto).toList();
        List<PieceDTO> blackPieces = board.getBlackPieces().stream().map(ModelToDtoConverter::convertModelToDto).toList();
        SquareDTO[][] boardDTO = new SquareDTO[8][8];
        Square[][] brd = board.getSquareArray();
        for (int i = 0; i < brd.length; i++) {
            for (int j = 0; j < brd[i].length; j++) {
                boardDTO[i][j] = convertModelToDto(brd[i][j]);
            }
        }

        BoardDTO dto = new BoardDTO(boardDTO, whitePieces, blackPieces);
        return dto;
    }
}
