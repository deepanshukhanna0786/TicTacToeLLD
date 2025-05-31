import controller.TicTacToeController;
import model.Board;
import model.GameStateData;
import model.Player;
import common.repository.FileGenerator;
import common.repository.GameStateManager;
import view.BoardView;
import common.enums.Mark;
import common.enums.PlayerType;
import common.ai.ComputerAI;

import java.util.Scanner;

public class TicTacToeMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameStateManager gameStateManager = new GameStateManager();
        FileGenerator fileGenerator = new FileGenerator();

        GameStateData loadedState = gameStateManager.loadGameState();

        Player player1;
        Player player2;
        ComputerAI computerAI = null;
        int rows;
        int cols;
        Board board;
        BoardView view;
        TicTacToeController controller;

        if (loadedState.getBoard()!=null) {
            System.out.println("Previous game state found. Do you want to resume? (yes/no)");
            String choice = scanner.nextLine().trim().toLowerCase();
            if ("yes".equals(choice)) {
                player1 = loadedState.getPlayer1();
                player2 = loadedState.getPlayer2();
                rows = loadedState.getBoard().length;
                cols = loadedState.getBoard()[0].length;
                board = new Board(new Mark[rows][cols], player1, player2);
                board.setBoard(loadedState.getBoard());
                board.setCurrentPlayer(loadedState.getCurrentPlayer());
                board.setGameOver(loadedState.isGameOver());
                view = new BoardView(scanner);
                controller = new TicTacToeController(
                        board, view, gameStateManager, fileGenerator, computerAI
                );
                view.attachMoveListener(controller);
                controller.startGame();
            } else {
                System.out.println("Starting a new game.");
                System.out.println("Enter name for Player 1:");
                player1 = new Player(scanner.nextLine(), Mark.X, PlayerType.Human);

                System.out.println("Enter 'p' for Player 2 or 'c' for Computer:");
                String choice2 = scanner.next().trim().toLowerCase();
                scanner.nextLine();

                if ("p".equals(choice2)) {
                    System.out.println("Enter name for Player 2:");
                    player2 = new Player(scanner.nextLine(), Mark.O, PlayerType.Human);
                } else {
                    System.out.println("Choose difficulty for computer (easy / medium / hard):");
                    String difficulty = scanner.nextLine().trim().toLowerCase();
                    player2 = new Player("Computer", Mark.O, PlayerType.Computer);
                    computerAI = new ComputerAI(difficulty);
                }

                System.out.println("Enter board size (rows and columns):");
                rows = scanner.nextInt();
                cols = scanner.nextInt();

                board = new Board(new Mark[rows][cols], player1, player2);
                view = new BoardView(scanner);
                controller = new TicTacToeController(
                        board, view, gameStateManager, fileGenerator, computerAI
                );
                view.attachMoveListener(controller);
                controller.startGame();
            }
        } else {
            System.out.println("No previous game state found. Starting a new game.");
            System.out.println("Enter name for Player 1:");
            player1 = new Player(scanner.nextLine(), Mark.X, PlayerType.Human);

            System.out.println("Enter 'p' for Player 2 or 'c' for Computer:");
            String choice2 = scanner.next().trim().toLowerCase();
            scanner.nextLine();

            if ("p".equals(choice2)) {
                System.out.println("Enter name for Player 2:");
                player2 = new Player(scanner.nextLine(), Mark.O, PlayerType.Human);
            } else {
                System.out.println("Choose difficulty for computer (easy / medium / hard):");
                String difficulty = scanner.nextLine().trim().toLowerCase();
                player2 = new Player("Computer", Mark.O, PlayerType.Computer);
                computerAI = new ComputerAI(difficulty);
            }

            System.out.println("Enter board size (rows and columns):");
            rows = scanner.nextInt();
            cols = scanner.nextInt();

            board = new Board(new Mark[rows][cols], player1, player2);
            view = new BoardView(scanner);
            controller = new TicTacToeController(
                    board, view, gameStateManager, fileGenerator, computerAI
            );
            view.attachMoveListener(controller);
            controller.startGame();
        }
    }
}