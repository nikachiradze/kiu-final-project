package chess.server;

import DTOs.BoardDTO;
import DTOs.MoveDTO;
import chess.common.adapter.ModelToDtoConverter;
import chess.model.Board;
import chess.model.Piece;
import chess.model.Square;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ChessSocketServer {

    private Board board = new Board();
    private List<Socket> clients = new LinkedList<>();

    public static void main(String[] args) {
        int port = 8888;

        ChessSocketServer server = new ChessSocketServer();

        System.out.println("Starting server on port " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (server.clients.size() < 2) {
                Socket client = serverSocket.accept();
                System.out.println("Accepted connection from " + client.getInetAddress().getHostAddress() + ":" + client.getPort());
                server.clients.add(client);
            }
            server. startGame();  // only called once
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void startGame() throws IOException, InterruptedException, ClassNotFoundException {
        Socket player1 = clients.get(0);
        Socket player2 = clients.get(1);
        ObjectOutputStream whiteOut = new ObjectOutputStream(player1.getOutputStream());
        ObjectOutputStream blackOut = new ObjectOutputStream(player2.getOutputStream());
        ObjectInputStream whiteIn = new ObjectInputStream(player1.getInputStream());
        ObjectInputStream blackIn = new ObjectInputStream(player2.getInputStream());

        whiteOut.writeObject("ddadas");
        board.getSquareArray()[0][0].setOccupyingPiece(null);
        SyncBoard(whiteOut, blackOut);

        while (true) {
            Thread.sleep(1000);
//            board.getWhitePieces().remove(0);
            if(board.getTurn()){
                Object obj = whiteIn.readObject();
                if(obj instanceof MoveDTO move){
                    if(processMove(move)){
                             //
                    }
                }
            }else{
                Object obj = blackIn.readObject();
                if(obj instanceof MoveDTO move){
                    if(processMove(move)){

                        // lukia yle gadmoitans pgn serviss da gaaketebs bazas romelshic sheinaxav gaaktebs entity da sheinaxavs
                    }
                }
            }

            System.out.println("removed");
        }
    }

    private void SyncBoard(ObjectOutputStream whiteOut, ObjectOutputStream blackOut) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                BoardDTO boardState = ModelToDtoConverter.convertModelToDto(board);

                whiteOut.writeObject(boardState);
                blackOut.writeObject(boardState);
            } catch (IOException e) {
                System.out.println("Failed to send board state: " + e.getMessage());
                scheduler.shutdown();  // stop sending if a client disconnects
            }
        }, 0, 50, TimeUnit.MILLISECONDS); // every 50 milliseconds expected error

    }

    private boolean processMove(MoveDTO moveDTO) {
        List<String> sq = board.getWhitePieces().stream().map(x -> x.getPosition().getPosition().toAlgebraic()).collect(Collectors.toList());
        Piece piece = getMovablePiece(moveDTO);
        board.setCurrPiece(piece);
        Square to = getToSquare(moveDTO);
        if(piece == null){
            return false;
        }
        boolean success = piece.move(to, board);
        if(!success){
            return false;
        }
        board.setCurrPiece(null);
        board.toggleTurn();
        return true;
    }

    private Piece getMovablePiece(MoveDTO moveDTO) {
        if(board.getTurn()){
            return board.getWhitePieces().stream().filter(x->x.getPosition().getPosition().toAlgebraic().equals(moveDTO.getFrom())).findFirst().orElse(null);
        }else{
            return board.getBlackPieces().stream().filter(x->x.getPosition().getPosition().toAlgebraic().equals(moveDTO.getFrom())).findFirst().orElse(null);
        }
    }

    private Square getToSquare(MoveDTO moveDTO) {
        return Arrays.stream(board.getSquareArray())
                .flatMap(Arrays::stream)
                .filter(x -> x.getPosition().toAlgebraic().equals(moveDTO.getTo()))
                .findFirst()
                .orElse(null);
    }
}
