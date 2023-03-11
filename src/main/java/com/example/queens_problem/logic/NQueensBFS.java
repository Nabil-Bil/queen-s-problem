package com.example.queens_problem.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NQueensBFS extends NQueens {


    public NQueensBFS(int n) {
        this.n = n;
        board = new boolean[n][n];
    }


    public void solve(int n) {
        Queue<Node> open = new LinkedList<>();
        boolean[][] initial_board = new boolean[n][n];
        open.offer(new Node(initial_board, 0));
        while (!open.isEmpty()) {
            Node node = open.poll();
            boolean[][] board = node.state;
            int c = node.depth;
            if (c == n) {
                this.board=board;
                return ;
            } else {
                for (int row = 0; row < n; row++) {
                    if (isSafe(board, row, c, n)) {
                        boolean[][] new_board = copyBoard(board, n);
                        new_board[row][c] = true;
                        open.offer(new Node(new_board, c + 1));
                    }
                }
            }
        }
    }


}

