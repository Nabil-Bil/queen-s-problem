package com.example.queens_problem.logic;

import java.util.PriorityQueue;

public class NQueensNCH extends NQueens {
    public NQueensNCH(int n) {
        super(n);
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
            if (isSafe(board)) {
                if (c == n) {
                    return new Result(open.size() + developedNodes, developedNodes, board);
                } else {
                    for (int row = 0; row < n; row++) {
                        boolean[][] new_board = copyBoard(board);
                        new_board[row][c] = true;
                        open.offer(new Node(new_board, c + 1, c + 1, -evaluate(new_board)));
                    }
                }
            }
        }
        return new Result(developedNodes, developedNodes, null);
    }
}
