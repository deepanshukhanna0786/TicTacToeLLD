package view;

import common.enums.Mark;
import common.listener.BoardObserver;
import common.listener.BoardSubject;
import common.listener.MoveListener;
import model.Board;
import model.Move;

import java.util.Scanner;

public class BoardView implements BoardObserver {
    private final Scanner scanner;
    private MoveListener moveListener;

    public BoardView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void attachMoveListener(MoveListener moveListener) {
        this.moveListener = moveListener;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void printBoard(Mark[][] board) {
        System.out.println("\nCurrent Board:");
        for (Mark[] row : board) {
            for (Mark cell : row) {
                System.out.print((cell == Mark.EMPTY ? "-" : cell.getSymbol()) + "\t");
            }
            System.out.println();
        }
    }


    private void promptUserMove(int rows, int cols) {
        while (true) {
            try {
                System.out.print("Enter row (1-" + rows + "): ");
                int row = scanner.nextInt() - 1;
                System.out.print("Enter column (1-" + cols + "): ");
                int col = scanner.nextInt() - 1;

                if (row >= 0 && row < rows && col >= 0 && col < cols) {
                    moveListener.onMoveEntered(new Move(row, col));
                    break;
                } else {
                    System.out.println("Invalid row or column. Try again.");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    @Override
    public void update(BoardSubject board) {
        if (board instanceof Board b) {
            printBoard(b.getBoard());
            System.out.println("Current Player: " + b.getCurrentPlayer().getName());
            if (!b.getCurrentPlayer().isComputer() && !b.isGameOver()) {
                promptUserMove(b.getBoard().length, b.getBoard()[0].length);
            }
        }
    }
}
