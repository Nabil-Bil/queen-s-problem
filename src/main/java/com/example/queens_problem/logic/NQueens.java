package com.example.queens_problem.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class NQueens {
    enum Selection {
        Elitist
    }

    int n;

    public NQueens(int n) {
        this.n = n;
    }
    public static int calculateFitness(boolean[][] board) {
        int n = board.length;
        int fitness = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col]) {
                    // Compter les attaques pour chaque reine
                    fitness += countAttacks(board, row, col);
                }
            }
        }
        return fitness / 2;
    }
    private static int countAttacks(boolean[][] board, int row, int col) {
        int n = board.length;
        int attacks = 0;

        // Diagonales descendantes
        for (int i = 1; i < n; i++) {
            if ((row + i < n) && (col + i < n) && board[row + i][col + i]) {
                attacks++;
            }
            if ((row - i >= 0) && (col + i < n) && board[row - i][col + i]) {
                attacks++;
            }
            if ((row + i < n) && (col - i >= 0) && board[row + i][col - i]) {
                attacks++;
            }
            if ((row - i >= 0) && (col - i >= 0) && board[row - i][col - i]) {
                attacks++;
            }
        }

        // Lignes et colonnes
        for (int i = 1; i < n; i++) {
            if ((row + i < n) && board[row + i][col]) {
                attacks++;
            }
            if ((row - i >= 0) && board[row - i][col]) {
                attacks++;
            }
            if ((col + i < n) && board[row][col + i]) {
                attacks++;
            }
            if ((col - i >= 0) && board[row][col - i]) {
                attacks++;
            }
        }

        return attacks;
    }

    protected int evaluate(boolean[][] board) {
        int count = 0;
        int i, j;
        Queue<Queen> counted = new LinkedList<>();
        for (int col = 0; col < n; col++) {
            for (int row = 0; row < n; row++) {
                if (board[row][col]) {
                    for (i = col + 1; i < n; i++) {
                        if (board[row][i]) {
                            if (!isCounted(counted, row, i)) {
                                count++;
                                counted.add(new Queen(row, i));
                                break;
                            }
                        }
                    }
                    for (i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
                        if (board[i][j]) {
                            //si la reine n'a pas déja été compté
                            if (!isCounted(counted, i, j)) {
                                count++;
                                counted.add(new Queen(i, j));
                                break;
                            }
                        }
                    }
                    for (i = row + 1, j = col + 1; i < n && j < n; i++, j++) {
                        if (board[i][j]) {
                            //si la reine n'a pas déja été compté
                            if (!isCounted(counted, i, j)) {
                                count++;
                                counted.add(new Queen(i, j));
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (count != 0) {
            count++;
        }
        return count;
    }

    protected boolean isCounted(Queue<Queen> counted, int row, int col) {
        for (Queen c : counted) {
            if (c.row == row && c.col == col) {
                return true;
            }
        }
        return false;
    }

    protected boolean isSafe(boolean[][] board) {
        return evaluate(board) == 0;
    }

    protected boolean[][] copyBoard(boolean[][] original) {
        boolean[][] copy = new boolean[n][n];
        for (int i = 0; i < n; i++) {

            System.arraycopy(original[i], 0, copy[i], 0, n);
        }
        return copy;
    }


    public Result solveAndGetResult() {
        double begin = System.currentTimeMillis();
        Result result = solve();
        double end = System.currentTimeMillis();
        result.executionTime= (end - begin) / 1000;
        return result;

    }

    protected abstract Result solve();


}
