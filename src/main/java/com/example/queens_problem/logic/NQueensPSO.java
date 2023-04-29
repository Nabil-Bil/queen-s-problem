package com.example.queens_problem.logic;

import java.util.*;

public class NQueensPSO extends NQueens{
    double theta;
    double c1;
    double c2;

    int populationSize;
    int iterationNumber;

    double mutationRate;


    public NQueensPSO(int n,double theta,double c1, double c2,int populationSize,int iterationNumber,double mutationRate) {
        super(n);
        this.theta=theta;
        this.c1=c1;
        this.c2=c2;
        this.populationSize=populationSize;
        this.iterationNumber=iterationNumber;
        this.mutationRate=mutationRate;
    }
    private ArrayList<Particle> generateRandomParticles(int populationSize) {
        ArrayList<Particle> population = new ArrayList<>(populationSize);

        for (int i = 0; i < populationSize; i++) {
            // Generate a random position for each queen in this individual
            boolean[][] position = new boolean[n][n];
            int[] velocity=new int[n];
            for (int j = 0; j < n; j++) {
                int randomPosition = (int) (Math.random() * (n));
                int randomVelocity=(int) (Math.random() * (n));
                velocity[j]=randomVelocity;
                position[j][randomPosition]=true;
            }
            Particle particle = new Particle(position, calculateFitness(position),position,velocity);
            population.add(particle);
        }
        return population;
    }

    int[] calculateVelocity(Particle particle,Particle gbest){
        int[] velocity=new int[n];
        for(int i=0;i<n;i++){
            int pBestValue=0;
            int gBestValue=0;
            int x=0;
            int v=particle.velocity[i];
            for(int j=0;j<n;j++){
                if(particle.pbest[i][j])
                    pBestValue=j;
                if(particle.position[i][j])
                    x=j;
                if(gbest.position[i][j])
                    gBestValue=j;
            }
            double r1 = Math.random();
            double r2 = Math.random();
            velocity[i] = (int) (theta *v  + c1 * r1 * (pBestValue - x) + c2 * r2 * (gBestValue - x));

        }
        return velocity;
    }



    public boolean[][] calculateNewPosition(Particle particle) {
        boolean[][] newPosition = new boolean[n][n];
        for(int i=0;i<n;i++){
            int j=0;
            while (j<n && !particle.position[i][j]){
                j++;
            }
            int newPosIndex=particle.velocity[i] + j;
            if(newPosIndex<0){
                newPosIndex=0;
            }
            if(newPosIndex>=n){
                newPosIndex=n-1;
            }
            newPosition[i][newPosIndex]=true;
        }
    return newPosition;
    }

    private Particle hillClimbing(Particle particle) {

        Particle bestNeighbour=particle.clone();
                ArrayList<Particle> neighbours=null;
                neighbours=generateNeighbours(bestNeighbour);
                neighbours.sort(Comparator.comparingInt(Particle::getFitnessValue));
                Particle neighbour=neighbours.get(0);
                    if(neighbour.getFitnessValue() < bestNeighbour.getFitnessValue()){
                        bestNeighbour = neighbour;
                    }
        return bestNeighbour;
    }


    private ArrayList<Particle> generateNeighbours(Particle currentSolution) {
        ArrayList<Particle> neighbours=new ArrayList<>();
        for(int i=0;i<n;i++){

            int col=0;
            while (!currentSolution.position[i][col]){
                col++;
            }
            int bestCol=col;
            int bestColConflicts=countAttacks(currentSolution.position,i,col);
            for(int j=0;j<n;j++){
                int conflicts=countAttacks(currentSolution.position,i,j);
                if(bestColConflicts>conflicts){
                    bestCol=j;
                    bestColConflicts=conflicts;
                }
            }
            Particle neighbour = currentSolution.clone();
            neighbour.position[i][col] = false;
            neighbour.position[i][bestCol] = true;
            neighbour.fitnessValue=calculateFitness(neighbour.position);
            neighbours.add(neighbour);
        }
        return neighbours;


    }
    private void mutate(Particle particule) {
        Random random = new Random();
        int row = random.nextInt(n);
        int currentQueenPos = -1;
        for (int col = 0; col < n; col++) {
            if (particule.position[row][col]) {
                currentQueenPos = col;
                break;
            }
        }
        int newQueenPos;
        do {
            newQueenPos = random.nextInt(n);
        } while (newQueenPos == currentQueenPos);
        particule.position[row][currentQueenPos] = false;
        particule.position[row][newQueenPos] = true;
    }
    @Override
    protected Result solve() {
        double hillClimbingRate=0.2;
        ArrayList<Particle> particles= generateRandomParticles(populationSize);
        int iteration=0;
        particles.sort(Comparator.comparingInt(Particle::getFitnessValue));
        Particle gbest=particles.get(0);
        while (iteration < iterationNumber && gbest.getFitnessValue()>0) {

            // Check if the best individual is a solution
            for(int i=0;i<particles.size();i++){
                Particle newParticle=particles.get(i).clone();
                newParticle.velocity=calculateVelocity(particles.get(i),gbest);
                newParticle.position=calculateNewPosition(newParticle);
                newParticle.fitnessValue=calculateFitness(newParticle.position);
                if(Math.random()<mutationRate){
                    mutate(newParticle);
                    newParticle.fitnessValue=calculateFitness(newParticle.position);
                }
                if(Math.random()<hillClimbingRate){
                    Particle bestNeighbour=hillClimbing(newParticle);
                    if(bestNeighbour.getFitnessValue()<newParticle.getFitnessValue()){
                        newParticle=bestNeighbour;
                    }
                }

                if(newParticle.fitnessValue<calculateFitness(newParticle.pbest)){
                    newParticle.pbest=newParticle.position;
                }
                particles.set(i,newParticle);
            }
            iteration++;
            particles.sort(Comparator.comparingInt(Particle::getFitnessValue));
            if(gbest.getFitnessValue()>particles.get(0).getFitnessValue())
                gbest=particles.get(0);
        }
        return new Result( gbest.position,gbest.getFitnessValue());
    }





}
