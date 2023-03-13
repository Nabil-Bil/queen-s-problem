package com.example.queens_problem.logic;

import java.util.*;

public class NQueensDFS extends NQueens {
    public NQueensDFS(int n) {
        super(n);
    }


    // Check if it is safe to place a queen at position (row, col)


    protected Result solve() {
        Stack<Node> open = new Stack<>();
        LinkedList<Node> closed=new LinkedList<>();
        boolean[][] initial_board = new boolean[n][n];
        open.push(new Node(initial_board,0));
        while (!open.isEmpty()) {
            Node node = open.pop();
            closed.push(node);
            int c=node.depth;
            boolean[][] board=node.state;
            if (c == n) {
                if(isSafe(board)){
                    return new Result(open.size()+ closed.size(),closed.size(),board);

                }
            } else {
                for (int row = n-1; row >=0 ; row--) {
                        boolean[][] new_board = copyBoard(board);
                        new_board[row][c] = true;
                        open.push(new Node(new_board,c+1));
                }
            }

        }
        return new Result( closed.size(),closed.size(),null);
    }



}