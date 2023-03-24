package com.example.queens_problem.logic;

import java.util.*;

public class NQueensCCF extends NQueens {

    public NQueensCCF(int n) {
        super(n);
    }

    protected int countNonAttackingQueens(boolean[][] board) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!board[i][j]) {
                    boolean canPlaceQueen = true;
                    // Check row and column
                    for (int k = 0; k < n; k++) {
                        if (board[i][k] || board[k][j]) {
                            canPlaceQueen = false;
                            break;
                        }
                    }
                    // Check diagonals
                    if (canPlaceQueen) {
                        for (int k = 1; k < n; k++) {
                            if (i - k >= 0 && j - k >= 0 && board[i - k][j - k]) {
                                canPlaceQueen = false;
                                break;
                            }
                            if (i + k < n && j + k < n && board[i + k][j + k]) {
                                canPlaceQueen = false;
                                break;
                            }
                            if (i - k >= 0 && j + k < n && board[i - k][j + k]) {
                                canPlaceQueen = false;
                                break;
                            }
                            if (i + k < n && j - k >= 0 && board[i + k][j - k]) {
                                canPlaceQueen = false;
                                break;
                            }
                        }
                    }
                    if (canPlaceQueen) {
                        count++;
                    }
                }
            }
        }
        return count;
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
                        open.offer(new Node(new_board, c + 1, -c - 1, -countNonAttackingQueens(new_board)));
                    }
                }
            }
        }
        return new Result(developedNodes, developedNodes, null);
    }
}