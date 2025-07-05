package DTOs;

import Enums.GameStatusType;
import Enums.PieceColor;

import java.io.Serializable;

public record GameStatusDTO(GameStatusType statusType, PieceColor affectedPlayer) implements Serializable {
}
