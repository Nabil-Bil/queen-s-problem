package com.example.queens_problem.logic;

public class Node {
    boolean[][] state;
    int depth;

    public Node(boolean[][] initialState, int i) {
        this.state=initialState;
        this.depth=i;
    }
}
