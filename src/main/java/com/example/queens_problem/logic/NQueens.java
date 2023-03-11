package com.example.queens_problem.logic;

import java.util.List;

public abstract class NQueens {
    int n;
    boolean[][] board;
    protected boolean isSafe(boolean[][] board, int row, int col, int n) {
        int i, j;

        // Check the row on the left side
        for (i = 0; i < col; i++) {
            if (board[row][i])
                return false;
        }

        // Check the upper diagonal on the left side
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j])
                return false;
        }

        // Check the lower diagonal on the left side
        for (i = row, j = col; j >= 0 && i < n; i++, j--) {
            if (board[i][j])
                return false;
        }

        // If it passes all the checks, it is safe to place a queen
        return true;
    }

    protected static boolean[][] copyBoard(boolean[][] original, int n) {
        boolean[][] copy = new boolean[n][n];
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public abstract void solve(int n);


}
