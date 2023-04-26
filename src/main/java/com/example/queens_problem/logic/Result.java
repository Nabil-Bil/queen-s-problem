package com.example.queens_problem.logic;

public class Result {
    public double executionTime;
    public int generatedNodes;
    public int developedNodes;
    public boolean[][] solution;

    public int fitness;


    public Result(int generatedNodes, int developedNodes, boolean[][] solution) {
        this.generatedNodes = generatedNodes;
        this.developedNodes = developedNodes;
        this.solution = solution;
    }
    public Result(boolean[][] solution,int fitness){
        this.fitness=fitness;
        this.solution = solution;
    }
    public Result(double executionTime,boolean[][] solution,int fitness){
        this.executionTime=executionTime;
        this.fitness=fitness;
        this.solution = solution;
    }

    public Result(double executionTime, int generatedNodes, int developedNodes, boolean[][] solution) {
        this.executionTime = executionTime;
        this.generatedNodes = generatedNodes;
        this.developedNodes = developedNodes;
        this.solution = solution;
    }

}
