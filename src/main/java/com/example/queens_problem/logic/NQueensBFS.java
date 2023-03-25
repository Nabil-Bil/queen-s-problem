package com.example.queens_problem.logic;

import java.util.LinkedList;
import java.util.Queue;

public class NQueensBFS extends NQueens {


    public NQueensBFS(int n) {
        super(n);
    }

    protected Result solve() {
        Queue<Node> open = new LinkedList<>();
        int developedNodes = 0;
        boolean[][] initial_board = new boolean[n][n];
        open.offer(new Node(initial_board, 0));
        while (!open.isEmpty()) {
            Node node = open.poll();
            developedNodes++;
            boolean[][] board = node.state;
            int c = node.depth;
            if (isSafe(board)) {
                if (c == n) {
                    return new Result(open.size() + developedNodes, developedNodes, board);
                } else {
                    for (int row = 0; row < n; row++) {
                        boolean[][] new_board = copyBoard(board);
                        new_board[row][c] = true;
                        open.offer(new Node(new_board, c + 1));
                    }
                }
            }
        }
        return new Result(developedNodes, developedNodes, null);
    }


}

