package com.example.queens_problem.logic;

import java.util.*;

public class NQueensMCV extends NQueens {

    public NQueensMCV(int n) {
        super(n);
    }

    public static int calculateCost(boolean[][] board) {
        int n = board.length;
        int cost = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                // If two queens are on the same row or diagonal
                if (board[i][j] && (i == j || Math.abs(i - j) == Math.abs(getQueenCol(board, i) - getQueenCol(board, j)))) {
                    cost++;
                }
            }
        }

        return cost;
    }


    private static int getQueenCol(boolean[][] board, int row) {
        for (int j = 0; j < board[row].length; j++) {
            if (board[row][j]) {
                return j;
            }
        }
        return -1;
    }

    @Override
    protected Result solve() {
        PriorityQueue<Node> open = new PriorityQueue<>(new NodeComparator());
        boolean[][] initial_board = new boolean[n][n];
        int developedNodes = 0;
        open.offer(new Node(initial_board, 0, 0, 0));
        while (!open.isEmpty()) {
            Node node = open.poll();
            developedNodes++;
            int c = node.depth;
            boolean[][] board = node.state;
            if (isSafe(board, n)) {
                if (c == n) {
                    return new Result(open.size() + developedNodes, developedNodes, board);
                } else {
                    for (int row = 0; row < n; row++) {
                        boolean[][] new_board = copyBoard(board);
                        new_board[row][c] = true;
                        if (isSafe(board, n)) {
                            open.offer(new Node(new_board, c + 1, c + 1, -evaluate(new_board, n)));
                        }
                    }
                }
            }
        }
        return new Result(developedNodes, developedNodes, null);
    }
}