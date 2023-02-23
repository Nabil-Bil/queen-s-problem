package com.example.queens_problem.logic;

import java.util.ArrayList;
import java.util.List;

public class QueensProblemDFS {
    int n;
    boolean[][] board;

    public QueensProblemDFS(int n) {
        this.n = n;
        board = new boolean[n][n];
    }

    // Check if it is safe to place a queen at position (row, col)
    private boolean isSafe(int row, int col) {
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

    // Recursive function to solve the N-Queens problem using DFS algorithm
    public void solve(int col, List<boolean[][]> solutions) {
        // Base case: If all queens are placed, add the solution to the list and return
        if (col == n) {
            solutions.add(copyBoard(board));
            return;
        }

        for (int row = 0; row < n; row++) {
            // Check if it is safe to place a queen at position (row, col)
            if (isSafe(row, col)) {
                // Place the queen
                board[row][col] = true;

                // Recursively solve for the remaining columns
                solve(col + 1, solutions);

                // Backtrack and remove the queen
                board[row][col] = false;
            }
        }
    }

    // Utility function to create a copy of a 2D boolean array
    private boolean[][] copyBoard(boolean[][] original) {
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

    public void printSolution(boolean[][] solution) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (solution[i][j])
                    System.out.print("Q ");
                else
                    System.out.print("_ ");
            }
            System.out.println();
        }
        System.out.println();
    }



}