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
            clone.position=this.position.clone();
            clone.fitnessValue=this.fitnessValue;
            clone.pbest=this.pbest.clone();
            clone.velocity=this.velocity.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("fitness : "+this.getFitnessValue());
        for (boolean[] booleans : position) {
            for (int j = 0; j < position.length; j++) {
                stringBuilder.append(booleans[j] ? "1 " : "0 ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
