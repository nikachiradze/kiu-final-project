package ui.socket;

import DTOs.BoardDTO;
import DTOs.GameInitDTO;
import DTOs.MoveDTO;
import ui.abstracts.MoveSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class ServerConnection implements Runnable, MoveSender {
    private final String host;
    private final int port;
    private final Consumer<BoardDTO> boardStateListener;
//    private final Consumer<GameStatusDTO> gameStatusListener;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Consumer<GameInitDTO> pieceColrListener;
    private volatile boolean running = true;

    public ServerConnection(String host, int port, Consumer<BoardDTO> boardDTOListener) {
        this.host = host;
        this.port = port;
        this.boardStateListener = boardDTOListener;
//        this.gameStatusListener = gameStatusListener;
//        this.pieceColrListener = playerColorListener;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (running) {
                Object object = in.readObject();
                if (object instanceof BoardDTO boardDTO) {
                    boardStateListener.accept(boardDTO);
                    if (boardDTO.getGameStatus() != null) {
                        Thread.sleep(1000);
//                        gameStatusListener.accept(boardDTO.getGameStatus());
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Connection lost: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
//            closeConnection();
        }
    }

    @Override
    public void sendMove(MoveDTO move) throws IOException {
        if (out != null) {
            out.writeObject(move);
            out.flush();
        } else {
            throw new IOException("Output stream chaijva");
        }
    }

    public void closeConnection() {
        running = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException ignored) {
        }
    }
}
