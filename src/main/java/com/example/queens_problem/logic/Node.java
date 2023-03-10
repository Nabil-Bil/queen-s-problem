package com.example.queens_problem.logic;

public class Node {
    boolean[][] state;
    int depth;
    int h;

    public Node(boolean[][] initialState, int i) {
        this.state=initialState;
        this.depth=i;
    }
    public Node(boolean[][] initialState, int i,int h) {
        this.state=initialState;
        this.depth=i;
        this.h=h;
    }

    public int getF() {
        return depth+h;
    }
}
