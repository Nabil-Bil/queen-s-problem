package com.example.queens_problem.logic;

public class Individual {
    boolean[][] solution;
    public  int fitnessValue;

    public Individual(boolean[][]solution,int fitnessValue){
        this.solution=solution;
        this.fitnessValue=fitnessValue;
    }

    public int getFitnessValue() {
        return fitnessValue;
    }
}
