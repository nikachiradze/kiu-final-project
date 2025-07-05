package ui;

import DTOs.BoardDTO;
import DTOs.PieceDTO;
import DTOs.SquareDTO;
import chess.common.adapter.ModelToDtoConverter;
import chess.model.Board;
import chess.util.Clock;
import ui.mouseListener.BoardMouseListener;
import ui.mouseListener.MouseListenerImpl;
import ui.socket.ServerConnection;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;


public class GameWindow {
    private JFrame gameWindow;

    public Clock blackClock;
    public Clock whiteClock;

    private Timer timer;

    private Board board;

    private BoardRendering boardRendering;

    private ServerConnection serverConnection;


    public GameWindow(String blackName, String whiteName, int hh,
                      int mm, int ss) {

        blackClock = new Clock(hh, ss, mm);
        whiteClock = new Clock(hh, ss, mm);

        gameWindow = new JFrame("Chess");


        try {
            Image whiteImg = ImageIO.read(getClass().getResource("/wp.png"));
            gameWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            System.out.println("chess.app.Game file wp.png not found");
        }

        gameWindow.setLocation(100, 100);


        gameWindow.setLayout(new BorderLayout(20, 20));

        // chess.app.Game Data window
        JPanel gameData = gameDataPanel(blackName, whiteName, hh, mm, ss);
        gameData.setSize(gameData.getPreferredSize());
        gameWindow.add(gameData, BorderLayout.NORTH);

//        this.board = new Board(this);

        this.board = new Board();
        BoardDTO board = ModelToDtoConverter.convertModelToDto(this.board);
        List<PieceDTO> list = new java.util.ArrayList<>(board.getBlackPieces().stream().toList());
        list.addAll(board.getWhitePieces());
        setPiecesToTheSquares(list, board.getBoard());


        Arrays.stream(board.getBoard()).flatMap(x -> Arrays.stream(x)).forEach(x -> System.out.println(x.getOccupyingPiece()));


        boardRendering = new BoardRendering(board, this);
        gameWindow.add(boardRendering, BorderLayout.CENTER);

        gameWindow.add(buttons(), BorderLayout.SOUTH);

        gameWindow.setMinimumSize(gameWindow.getPreferredSize());
        gameWindow.setSize(gameWindow.getPreferredSize());
        gameWindow.setResizable(false);

        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



//        boardRendering.addMouseListener(new BoardMouseListener(new MouseListenerImpl(boardRendering.getChessBoard(),boardRendering)));

//        connectToServerAndListen();

        startConnection();
    }


    private void startConnection(){
        serverConnection = new ServerConnection("localhost", 8888, updateBoard());
        boardRendering.addMouseListener(new BoardMouseListener(new MouseListenerImpl(boardRendering.getChessBoard(),boardRendering, serverConnection)));
        new Thread(serverConnection).start();
    }

// Helper function to create data panel

    private void connectToServerAndListen() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8888);
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//                out.writeObject(ModelToDtoConverter.convertModelToDto(this.board));
                while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof BoardDTO boardDTO) {
                        System.out.println("received");
                        var list = new ArrayList<PieceDTO>(boardDTO.getBlackPieces());
                        list.addAll(boardDTO.getWhitePieces());
                        setPiecesToTheSquares(list, boardDTO.getBoard());
                        boardRendering.updateBoard(boardDTO);
                    }
                }
            } catch (Exception e) {
//                JOptionPane.showMessageDialog(this, "Connection error: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }


    private Consumer<BoardDTO> updateBoard() {
        return (board) -> {
            System.out.println("received");
            var list = new ArrayList<PieceDTO>(board.getBlackPieces());
            list.addAll(board.getWhitePieces());
            setPiecesToTheSquares(list, board.getBoard());
            boardRendering.updateBoard(board);
        };
    }





    private JPanel gameDataPanel(final String bn, final String wn,
                                 final int hh, final int mm, final int ss) {

        JPanel gameData = new JPanel();
        gameData.setLayout(new GridLayout(3, 2, 0, 0));


        // PLAYER NAMES

        JLabel w = new JLabel(wn);
        JLabel b = new JLabel(bn);

        w.setHorizontalAlignment(JLabel.CENTER);
        w.setVerticalAlignment(JLabel.CENTER);
        b.setHorizontalAlignment(JLabel.CENTER);
        b.setVerticalAlignment(JLabel.CENTER);

        w.setSize(w.getMinimumSize());
        b.setSize(b.getMinimumSize());

        gameData.add(w);
        gameData.add(b);

        // CLOCKS

        final JLabel bTime = new JLabel(blackClock.getTime());
        final JLabel wTime = new JLabel(whiteClock.getTime());

        bTime.setHorizontalAlignment(JLabel.CENTER);
        bTime.setVerticalAlignment(JLabel.CENTER);
        wTime.setHorizontalAlignment(JLabel.CENTER);
        wTime.setVerticalAlignment(JLabel.CENTER);

        if (!(hh == 0 && mm == 0 && ss == 0)) {
            timer = new Timer(1000, null);
            timer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean turn = board.getTurn();

                    if (turn) {
                        whiteClock.decr();
                        wTime.setText(whiteClock.getTime());

                        if (whiteClock.outOfTime()) {
                            timer.stop();
                            int n = JOptionPane.showConfirmDialog(
                                    gameWindow,
                                    bn + " wins by time! Play a new game? \n" +
                                            "Choosing \"No\" quits the game.",
                                    bn + " wins!",
                                    JOptionPane.YES_NO_OPTION);

                            if (n == JOptionPane.YES_OPTION) {
                                new GameWindow(bn, wn, hh, mm, ss);
                                gameWindow.dispose();
                            } else gameWindow.dispose();
                        }
                    } else {
                        blackClock.decr();
                        bTime.setText(blackClock.getTime());

                        if (blackClock.outOfTime()) {
                            timer.stop();
                            int n = JOptionPane.showConfirmDialog(
                                    gameWindow,
                                    wn + " wins by time! Play a new game? \n" +
                                            "Choosing \"No\" quits the game.",
                                    wn + " wins!",
                                    JOptionPane.YES_NO_OPTION);

                            if (n == JOptionPane.YES_OPTION) {
                                new GameWindow(bn, wn, hh, mm, ss);
                                gameWindow.dispose();
                            } else gameWindow.dispose();
                        }
                    }
                }
            });
            timer.start();
        } else {
            wTime.setText("Untimed game");
            bTime.setText("Untimed game");
        }

        gameData.add(wTime);
        gameData.add(bTime);

        gameData.setPreferredSize(gameData.getMinimumSize());

        return gameData;
    }

    private JPanel buttons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3, 10, 0));

        final JButton quit = new JButton("Quit");

        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        gameWindow,
                        "Are you sure you want to quit?",
                        "Confirm quit", JOptionPane.YES_NO_OPTION);

                if (n == JOptionPane.YES_OPTION) {
                    if (timer != null) timer.stop();
                    gameWindow.dispose();
                }
            }
        });

        final JButton nGame = new JButton("New game");

        nGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        gameWindow,
                        "Are you sure you want to begin a new game?",
                        "Confirm new game", JOptionPane.YES_NO_OPTION);

                if (n == JOptionPane.YES_OPTION) {
                    SwingUtilities.invokeLater(new StartMenu());
                    gameWindow.dispose();
                }
            }
        });

        final JButton instr = new JButton("How to play");

        instr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameWindow,
                        "Move the chess pieces on the board by clicking\n"
                                + "and dragging. The game will watch out for illegal\n"
                                + "moves. You can win either by your opponent running\n"
                                + "out of time or by checkmating your opponent.\n"
                                + "\nGood luck, hope you enjoy the game!",
                        "How to play",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        buttons.add(instr);
        buttons.add(nGame);
        buttons.add(quit);

        buttons.setPreferredSize(buttons.getMinimumSize());

        return buttons;
    }

    public void checkmateOccurred(int c) {
        if (c == 0) {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "White wins by checkmate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "White wins!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        } else {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Black wins by checkmate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "Black wins!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        }
    }


    private void setPiecesToTheSquares(List<PieceDTO> pieces, SquareDTO[][] squareStates) {
        Map<Integer, PieceDTO> map = new HashMap<>();
        for (PieceDTO ps : pieces) {
//            System.out.println(ps.getPosition().hashCode());
            map.put(ps.getPosition().getPosition().hashCode(), ps);
        }

        for (int i = 0; i < squareStates.length; i++) {
            for (int j = 0; j < squareStates[i].length; j++) {

//                System.out.println("from map" + map.getOrDefault(squareStates[i][j].getPosition().hashCode(), null));
                squareStates[i][j].setOccupyingPiece(map.getOrDefault(squareStates[i][j].getPosition().hashCode(), null));
            }
        }
    }

}
