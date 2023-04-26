package com.example.queens_problem.logic;

import java.util.ArrayList;
import java.util.Comparator;

public class NQueensPSO extends NQueens{
    float theta;
    double c1;
    double r1;
    double c2;
    double r2;

    int populationSize;
    int iterationNumber;



    public NQueensPSO(int n){
        super(n);
    }
    public NQueensPSO(int n,int theta,double c1, double c2,double r1, double r2,int populationSize,int iterationNumber) {
        super(n);
        this.theta=theta;
        this.c1=c1;
        this.c2=c2;
        this.r1=r1;
        this.r2=r2;
        this.populationSize=populationSize;
        this.iterationNumber=iterationNumber;
    }
    private ArrayList<Particle> generateRandomParticles(int populationSize) {
        ArrayList<Particle> population = new ArrayList<>(populationSize);

        for (int i = 0; i < populationSize; i++) {
            // Generate a random position for each queen in this individual
            boolean[][] state = new boolean[n][n];
            boolean[][] velocity=new boolean[n][n];
            for (int j = 0; j < n; j++) {
                int randomPosition = (int) (Math.random() * n);
                int randomVelocity=(int) (Math.random() * n);
                for (int k = 0; k < n; k++) {
                    state[j][k] = k == randomPosition;
                    velocity[j][k]=k==randomVelocity;
                }
            }
            Particle particle = new Particle(state, calculateFitness(state),state,velocity);
            population.add(particle);
        }
        return population;
    }
    boolean sigmoid(double x) {
        double result= 1 / (1 + Math.exp(-x));
        return !(result > 0.5);
    }
    Particle getGBest(ArrayList<Particle> particles){
         Particle gbest=particles.get(0);
        for(int i=1;i<particles.size();i++){
            if(gbest.getF()>particles.get(i).getF()){
                gbest=particles.get(i);
            }
        }
        return gbest;
    }
    int fromBoolToInt(boolean b){
        if(b){
            return 1;
        }else{
            return 0;
        }
    }
    boolean[][] calculateVelocity(Particle particle,Particle gbest){
        boolean [][] velocity=new boolean[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                int pBestValue=fromBoolToInt(particle.pbest[i][j]);
                int gBestValue=fromBoolToInt(gbest.state[i][j]);
                int x=fromBoolToInt(particle.state[i][j]);
                int v=fromBoolToInt(particle.velocity[i][j]);
                velocity[i][j]=sigmoid(theta*v+c1*r1*(pBestValue-x)+c2*r2*(gBestValue-x));
            }
        }
        return velocity;
    }

    boolean [][] calculateNewPosition(Particle particle){
        boolean[][] newPosition=new boolean[n][n];
        for(int i=0;i<n;i++){
            int j=0;
            int k=0;
            while (j<n && !newPosition[i][k]){
                newPosition[i][j]=particle.state[i][j]^particle.velocity[i][j];
                k=j;
                j++;
            }
        }
        return newPosition;
    }
    @Override
    protected Result solve() {

        this.theta=1;
        this.c1=2;
        this.c2=1;
        this.r1=0.45;
        this.r2=0.28;
        this.iterationNumber=400;
        this.populationSize=100;
        ArrayList<Particle> particles= generateRandomParticles(populationSize);
        int iteration=0;
        while (iteration < iterationNumber) {
            System.out.println(iteration);
            // Sort the population by fitness
            particles.sort(Comparator.comparingInt(Particle::getF));
            Particle gbest=particles.get(0);

            // Check if the best individual is a solution
            if (gbest.getF() == 0) {
                return new Result( particles.get(0).state,particles.get(0).getF());
            }
            for(Particle particle:particles){
                particle.velocity=calculateVelocity(particle,gbest);
                boolean[][] newPos=calculateNewPosition(particle);

                if(calculateFitness(newPos)<calculateFitness(particle.state)){
                    particle.pbest=newPos;
                }
                particle.state=newPos;
            }
            iteration++;
        }
        particles.sort(Comparator.comparingInt(Particle::getF));
        return new Result( particles.get(0).state,particles.get(0).getF());




    }

}
