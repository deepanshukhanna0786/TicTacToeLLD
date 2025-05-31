package controller;

import common.enums.Mark;

public class GameRules {

    public Mark checkWinner(Mark[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        for (Mark[] marks : board) {
            if (marks[0] != Mark.EMPTY) {
                boolean win = true;
                for (int j = 1; j < cols; j++) {
                    if (marks[j] != marks[0]) {
                        win = false;
                        break;
                    }
                }
                if (win) return marks[0];
            }
        }

        for (int j = 0; j < cols; j++) {
            if (board[0][j] != Mark.EMPTY) {
                Mark first = board[0][j];
                boolean win = true;
                for (int i = 1; i < rows; i++) {
                    if (board[i][j] != first) {
                        win = false;
                        break;
                    }
                }
                if (win) return first;
            }
        }

        if (rows == cols) {
            if (board[0][0] != Mark.EMPTY) {
                Mark first = board[0][0];
                boolean win = true;
                for (int i = 1; i < rows; i++) {
                    if (board[i][i] != first) {
                        win = false;
                        break;
                    }
                }
                if (win) return first;
            }

            if (board[0][cols - 1] != Mark.EMPTY) {
                Mark first = board[0][cols - 1];
                boolean win = true;
                for (int i = 1; i < rows; i++) {
                    if (board[i][cols - 1 - i] != first) {
                        win = false;
                        break;
                    }
                }
                if (win) return first;
            }
        }

        return Mark.EMPTY;
    }

    public boolean isBoardFull(Mark[][] board) {
        for (Mark[] row : board) {
            for (Mark cell : row) {
                if (cell == Mark.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}