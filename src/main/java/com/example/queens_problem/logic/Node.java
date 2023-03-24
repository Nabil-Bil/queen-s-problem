package com.example.queens_problem.logic;

public class Node {
    boolean[][] state;
    int depth;
    public int h;
    public int g;

    public Node(boolean[][] initialState, int i) {
        this.state = initialState;
        this.depth = i;
    }

    public Node(boolean[][] initialState, int i, int g, int h) {
        this.state = initialState;
        this.depth = i;
        this.g = g;
        this.h = h;
    }

    public int getF() {
        return g + h;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.g).append(" + ").append(h).append(" = ").append(this.getF()).append("\n");
        for (boolean[] booleans : state) {
            for (int j = 0; j < state.length; j++) {
                stringBuilder.append(booleans[j] ? "1 " : "0 ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
