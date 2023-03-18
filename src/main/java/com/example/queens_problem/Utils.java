package com.example.queens_problem;

import com.example.queens_problem.logic.Result;

enum ChessColor {
    White,
    Black
}

enum Algorithm{
    DFS,
    BFS,
    MCV,
    NOC
}

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

interface ResultFuncrion{
    Result apply();
}