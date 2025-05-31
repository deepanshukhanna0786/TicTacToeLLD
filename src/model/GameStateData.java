package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import common.ai.ComputerAI;
import common.enums.Mark;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GameStateData {
    private Mark[][] board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;
    private ComputerAI computerAI;

    public void setPlayer1(Player player1) {
        this.player1 = new Player(player1.getName(), player1.getMark(), player1.getPlayerType());
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = new Player(player2.getName(), player2.getMark(), player2.getPlayerType());
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = new Player(currentPlayer.getName(), currentPlayer.getMark(), currentPlayer.getPlayerType());
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setBoard(Mark[][] board) {
        this.board = board;
    }

    public Mark[][] getBoard() {
        return board;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public ComputerAI getComputerAI() {
        return computerAI;
    }
    public void setComputerAI(ComputerAI computerAI) {
        this.computerAI= computerAI;
    }
}