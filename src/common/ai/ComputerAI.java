package common.ai;

import controller.GameRules;
import common.enums.Mark;
import model.Board;
import model.Move;
import model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerAI {

    private final String difficulty;
    private final Random random;

    public ComputerAI(String difficulty) {
        this.difficulty = difficulty.toLowerCase();
        this.random = new Random();
    }

    public Move getNextMove(Board board, Player computerPlayer) {
        return switch (difficulty) {
            case "easy" -> easyMove(board);
            case "medium" -> mediumMove(board, computerPlayer);
            case "hard" -> hardMove(board, computerPlayer);
            default -> easyMove(board);
        };
    }

    private Move easyMove(Board board) {
        List<Move> availableMoves = getAvailableMoves(board);
        return availableMoves.get(random.nextInt(availableMoves.size()));
    }

    private Move mediumMove(Board board, Player computerPlayer) {
        Mark[][] b = board.getBoard();
        Mark computerMark = computerPlayer.getMark();
        Mark opponentMark = getOpponentMark(computerMark);
        GameRules rules = new GameRules();

        for (Move move : getAvailableMoves(board)) {
            b[move.getRow()][move.getCol()] = computerMark;
            if (rules.checkWinner(b) == computerMark) {
                b[move.getRow()][move.getCol()] = Mark.EMPTY;
                return move;
            }
            b[move.getRow()][move.getCol()] = Mark.EMPTY;
        }

        for (Move move : getAvailableMoves(board)) {
            b[move.getRow()][move.getCol()] = opponentMark;
            if (rules.checkWinner(b) == opponentMark) {
                b[move.getRow()][move.getCol()] = Mark.EMPTY;
                return move;
            }
            b[move.getRow()][move.getCol()] = Mark.EMPTY;
        }

        return easyMove(board);
    }

    private Move hardMove(Board board, Player computerPlayer) {
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Move move : getAvailableMoves(board)) {
            board.getBoard()[move.getRow()][move.getCol()] = computerPlayer.getMark();
            int score = minimax(board, 0, false, computerPlayer.getMark(), getOpponentMark(computerPlayer.getMark()));
            board.getBoard()[move.getRow()][move.getCol()] = Mark.EMPTY;

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimax(Board board, int depth, boolean isMax, Mark aiMark, Mark opponentMark) {
        GameRules rules = new GameRules();
        Mark winner = rules.checkWinner(board.getBoard());

        if (winner == aiMark) return 10 - depth;
        if (winner == opponentMark) return depth - 10;
        if (rules.isBoardFull(board.getBoard())) return 0;

        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Move move : getAvailableMoves(board)) {
            board.getBoard()[move.getRow()][move.getCol()] = isMax ? aiMark : opponentMark;
            int score = minimax(board, depth + 1, !isMax, aiMark, opponentMark);
            board.getBoard()[move.getRow()][move.getCol()] = Mark.EMPTY;
            bestScore = isMax ? Math.max(score, bestScore) : Math.min(score, bestScore);
        }

        return bestScore;
    }

    private List<Move> getAvailableMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        Mark[][] grid = board.getBoard();
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] == Mark.EMPTY)
                    moves.add(new Move(i, j));
        return moves;
    }

    private Mark getOpponentMark(Mark mark) {
        return mark == Mark.X ? Mark.O : Mark.X;
    }
}
