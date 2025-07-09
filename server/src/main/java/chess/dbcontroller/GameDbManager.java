package chess.dbcontroller;

import chess.dbcontroller.interfaces.GameDbManagerBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDbManager implements GameDbManagerBase {
    private static final String DB_URL = "jdbc:sqlite:/Users/nikolozchiradze/socket_chess";

    @Override
    public boolean insertGame(String whitePlayer, String blackPlayer, String moves, String result) {
        String sql = "INSERT INTO GameInfo (PlayerWhite, PlayerBlack, Pgn, Result) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, whitePlayer);
            pstmt.setString(2, blackPlayer);
            pstmt.setString(3, moves);
            pstmt.setString(4, result);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public static void main(String[] args) {
        new GameDbManager().insertGame("white", "black", "moves", "result");
    }
}
