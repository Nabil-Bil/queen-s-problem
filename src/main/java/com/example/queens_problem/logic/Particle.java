package com.example.queens_problem.logic;

public class Particle implements Cloneable{
    boolean[][] pbest;
    int[] velocity;
    boolean[][] position;
    int fitnessValue;
    public Particle(boolean[][] initialPosition,int fitnessValue, boolean[][] pbest,int[] velocity) {
        this.pbest=pbest;
        this.velocity=velocity;
        this.fitnessValue=fitnessValue;
        this.position=initialPosition;
    }

    public int getFitnessValue() {
        return fitnessValue;
    }

    @Override
    public Particle clone() {
        try {
            Particle clone = (Particle) super.clone();
            clone.position=this.position;
            clone.fitnessValue=this.fitnessValue;
            clone.pbest=this.pbest;
            clone.velocity=this.velocity;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
