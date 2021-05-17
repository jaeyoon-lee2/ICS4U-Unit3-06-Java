/*
* Tic Tac Toe (X's and O's) program.
*
* @author Julie Nguyen & Mr. Coxall & Jay Lee
* @version 1.0
* @since Dec 2018 / Sep 2020 / 2020-05-13
*
*/
// package ca.mths.ics4u.unit3.unit06.java.tictactoe;

import java.util.Scanner;

public final class TicTacToe {
    private TicTacToe() {
        // Prevent instantiation
        // Optional: throw an exception e.g. AssertionError
        // if this ever *is* called
        throw new IllegalStateException("Cannot be instantiated");
    }

    // /** size of magic square array. */
    // public static final int BOARD_SIZE = 9;

    /**
    * This method get user's input "X" and cmputer's input "O"
    * from a function called compNextMove and shows the board.
    * @param args
    */
    public static void main(final String[] args) {
        // main stub, get user input here
        boolean boardFull = false;
        boolean checkWinnerX = false;
        boolean checkWinnerO = false;
        // int OsMove = -1;

        Scanner input = new Scanner(System.in);
        String[] board = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        System.out.println("Welcome to tic tac toe!");

        do {
            printBoard(board);
            System.out.print("\nWhich space would you like to the X? ");
            if (input.hasNextInt()) {
                int space = input.nextInt();

                if (space > board.length || space < 1) {
                    System.out.println("That spot is invalid!");
                } else if (board[space - 1].equalsIgnoreCase("X")
                           || board[space - 1].equalsIgnoreCase("O")) {
                    System.out.println("That spot's taken!");
                } else if (isNumeric(board[space - 1])) {
                    board[space - 1] = "X";
                    // place a function call here to get the best move for O
                    if (!isFull(board)) {
                        int goodComputerMove = compNextMove(board);
                        board[goodComputerMove] = "O";
                    } else {
                        printBoard(board);
                        System.out.println("\nTie.");
                    }
                } else {
                    System.out.println("Error");
                    break;
                }
            } else {
                System.out.println("Error");
                break;
            }

            if (winOrLost(board, "X")) {
                printBoard(board);
                System.out.println("\nX has won.");
                break;
            } else if (winOrLost(board, "O")) {
                printBoard(board);
                System.out.println("\nO has won.");
                break;
            }

            boardFull = isFull(board);
        } while (!boardFull);

        System.out.println("\nGame Over.");
    }

    /** middle left index of 3x3 1D Array. */
    private static final int MIDDLE_LEFT = 3;
    /** middle centre index of 3x3 1D Array. */
    public static final int MIDDLE_CENTRE = 4;
    /** middle right index of 3x3 1D Array. */
    public static final int MIDDLE_RIGHT = 5;
    /** bottom left index of 3x3 1D Array. */
    private static final int BOTTOM_LEFT = 6;
    /** bottom centre index of 3x3 1D Array. */
    public static final int BOTTOM_CENTRE = 7;
    /** bottom right index of 3x3 1D Array. */
    public static final int BOTTOM_RIGHT = 8;

    /**
    * This method returns true or false for whether
    * or not inputted array is a magic square.
    * @param board
    * @param takenSpace
    * @return boolean
    */
    public static boolean winOrLost(final String[] board,
                                    final String takenSpace) {
        return (board[0] == takenSpace && board[1] == takenSpace
                                       && board[2] == takenSpace)
            || (board[MIDDLE_LEFT] == takenSpace
                && board[MIDDLE_CENTRE] == takenSpace
                && board[MIDDLE_RIGHT] == takenSpace)
            || (board[BOTTOM_LEFT] == takenSpace
                && board[BOTTOM_CENTRE] == takenSpace
                && board[BOTTOM_RIGHT] == takenSpace)
            || (board[0] == takenSpace
                && board[MIDDLE_LEFT] == takenSpace
                && board[BOTTOM_LEFT] == takenSpace)
            || (board[1] == takenSpace
                && board[MIDDLE_CENTRE] == takenSpace
                && board[BOTTOM_CENTRE] == takenSpace)
            || (board[2] == takenSpace
                && board[MIDDLE_RIGHT] == takenSpace
                && board[BOTTOM_RIGHT] == takenSpace)
            || (board[0] == takenSpace
                && board[MIDDLE_CENTRE] == takenSpace
                 && board[BOTTOM_RIGHT] == takenSpace)
            || (board[2] == takenSpace
                && board[MIDDLE_CENTRE] == takenSpace
                && board[BOTTOM_LEFT] == takenSpace);
    }

    /**
    * This method calculates the best next move
    * for computer based on current board using minimax function.
    * @param currentBoard
    * @return goodMove
    */
    public static int compNextMove(final String[] currentBoard) {
        String[] newBoard = currentBoard;
        // boolean didOWin = winOrLost(newBoard, "O");
        int bestScore = Integer.MIN_VALUE;
        int goodMove = -1;

        if (isNumeric(newBoard[MIDDLE_CENTRE])) {
            // Always take middle if possible
            return MIDDLE_CENTRE;

        } else {
            for (int index = 0; index < newBoard.length; index++) {
                if (isNumeric(newBoard[index])) {
                    newBoard[index] = "O";
                    int score = minimax(newBoard, false);
                    newBoard[index] = String.valueOf(index + 1);
                    if (score > bestScore) {
                        bestScore = score;
                        goodMove = index;
                    }
                }
            }
        }
        return goodMove;
    }

    /**
    * This method calculates the best next move
    * for computer based on current board using minimax algorithm.
    * @param board
    * @param isMaximizing
    * @return goodMove
    */
    public static int minimax(final String[] board,
                              final boolean isMaximizing) {
        String player;
        int bestScore;
        if (winOrLost(board, "O")) {
            return 1;
        } else if (winOrLost(board, "X")) {
            return -1;
        } else if (isFull(board)) {
            return 0;
        }
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            player = "O";
        } else {
            bestScore = Integer.MAX_VALUE;
            player = "X";
        }
        for (int index = 0; index < board.length; index++) {
            if (isNumeric(board[index])) {
                board[index] = player;
                int score = minimax(board, !(isMaximizing));
                board[index] = String.valueOf(index + 1);
                if (isMaximizing) {
                    bestScore = Math.max(score, bestScore);
                } else {
                    bestScore = Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    /**
    * This method returns whether board is full or not.
    * @param presentBoard
    * @return full
    */
    public static boolean isFull(final String[] presentBoard) {
        boolean full = true;
        for (int counter = 0; counter < presentBoard.length; counter++) {
            if (isNumeric(presentBoard[counter])) {
                full = false;
                break;
            }
        }
        return full;
    }

    /**
    * This method prints current game state.
    * @param theBoard
    */
    public static void printBoard(final String[] theBoard) {
        System.out.println("\n----+----+----");
        for (int count = 0; count < theBoard.length; count++) {
            if (count % MIDDLE_LEFT - 2 == 0) {
                System.out.print("| " + theBoard[count] + " |\n");
                System.out.println("----+----+----");
            } else {
                System.out.print("| " + theBoard[count] + " ");
            }
        }
    }

    /**
    * This method checks the string is numeric.
    * @param strNum
    * @return boolean
    */
    public static boolean isNumeric(final String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
