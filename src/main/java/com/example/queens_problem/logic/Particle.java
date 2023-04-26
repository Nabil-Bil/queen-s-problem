package com.example.queens_problem.logic;

public class Particle extends Node{
    boolean[][] pbest;
    boolean[][] velocity;
    public Particle(boolean[][] initialState,int f, boolean[][] pbest,boolean[][] velocity) {
        super(initialState, 0, f);
        this.pbest=pbest;
        this.velocity=velocity;
    }

}
