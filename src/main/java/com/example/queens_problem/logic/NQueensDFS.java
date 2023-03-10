package com.example.queens_problem.logic;

import java.util.*;

public class NQueensDFS extends NQueens {
    public NQueensDFS(int n) {
        this.n = n;
        board = new boolean[n][n];
    }

    // Check if it is safe to place a queen at position (row, col)


    public List<boolean[][]> solve(int n) {
        ArrayList<boolean[][]> solutions = new ArrayList<>();
        Stack<Node> open = new Stack<>();
        boolean[][] initial_board = new boolean[n][n];
        open.push(new Node(initial_board,0));
        while (!open.isEmpty()) {
            Node node = open.pop();
            int c=node.depth;
            boolean[][] board=node.state;
            if (c == n) {
                solutions.add(board);
            } else {
                for (int row = n-1; row >=0 ; row--) {
                    if (isSafe(board, row, c, n)) {
                        boolean[][] new_board = copyBoard(board,n);
                        new_board[row][c] = true;
                        open.push(new Node(new_board,c+1));
                    }
                }
            }
        }
        return solutions;
    }



}