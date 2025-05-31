package model;

import common.enums.Mark;
import common.listener.BoardObserver;
import common.listener.BoardSubject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board extends BoardSubject {
    private Mark[][] board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private boolean gameOver = false;
    private final List<BoardObserver> observers = new ArrayList<>();

    public Board(Mark[][] board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    public void addObserver(BoardObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (BoardObserver observer : observers) {
            observer.update(this);
        }
    }

    public void refreshState() {
        notifyObservers();
    }

    public boolean makeMove(Move move) {
        if (gameOver) return false;
        if (move.getRow() < 0 || move.getRow() >= board.length ||
                move.getCol() < 0 || move.getCol() >= board[0].length ||
                board[move.getRow()][move.getCol()] != Mark.EMPTY) {
            return false;
        }

        board[move.getRow()][move.getCol()] = currentPlayer.getMark();
        return true;
    }

    public void switchPlayer() {
        if (!gameOver) {
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
            notifyObservers();
        }
    }

}