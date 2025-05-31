package controller;

import common.ai.ComputerAI;
import common.enums.GameState;
import common.enums.Mark;
import common.listener.MoveListener;
import model.Board;
import model.GameStateData;
import model.Move;
import model.Player;
import common.repository.FileGenerator;
import common.repository.GameStateManager;
import view.BoardView;

public class TicTacToeController implements MoveListener {
    private final Board board;
    private final BoardView view;
    private final GameStateManager storage;
    private final FileGenerator fileGenerator;
    private final ComputerAI computerAI;
    private boolean gameOver = false;
    private final GameRules rules;

    public TicTacToeController(Board board, BoardView view, GameStateManager storage,
                               FileGenerator fileGenerator, ComputerAI computerAI) {
        this.board = board;
        this.view = view;
        this.storage = storage;
        this.fileGenerator = fileGenerator;
        this.computerAI = computerAI;
        this.rules = new GameRules();
        board.addObserver(view);
    }

    public void startGame() {
        fileGenerator.createFile();
        GameStateData loadedState = storage.loadGameState();

        if (loadedState == null) {
            view.displayMessage(GameState.GAME_START.getGameState() + "\n");
            initializeNewGame();
        }
        else{
            board.refreshState();
        }
    }

    private void initializeNewGame() {
        board.initializeBoard();
        board.setCurrentPlayer(board.getPlayer1());
        board.setGameOver(false);
        storage.clearGameState();
        board.refreshState();
        if (board.getCurrentPlayer().isComputer() && !gameOver) {
            makeComputerMove();
        }
    }

    @Override
    public void onMoveEntered(Move move) {
        if (board.isGameOver()) return;

        Player current = board.getCurrentPlayer();

        if (!board.makeMove(move)) {
            view.displayMessage("Invalid move! Try again.");
            board.refreshState();
            return;
        }

        storage.saveGameState(board);

        Mark winner = rules.checkWinner(board.getBoard());

        if (winner != Mark.EMPTY) {
            view.displayMessage("Player " + current.getName() + " wins!");
            board.setGameOver(true);
            storage.clearGameState();
            return;
        }

        else if (rules.isBoardFull(board.getBoard())) {
            view.displayMessage(GameState.GAME_TIED.getGameState());
            view.displayMessage("\n\n"+GameState.GAME_END.getGameState());
            board.setGameOver(true);
            storage.clearGameState();
            return;
        }
        if (!board.isGameOver()) {
            board.switchPlayer();
        }

        board.refreshState();

        if (board.getCurrentPlayer().isComputer() && !board.isGameOver()) {
            makeComputerMove();
        }
    }

    private void makeComputerMove() {
        Move move = computerAI.getNextMove(board, board.getCurrentPlayer());
        view.displayMessage("Computer chose: (" + (move.getRow() + 1) + ", " + (move.getCol() + 1) + ")");
        onMoveEntered(move);
    }
}
