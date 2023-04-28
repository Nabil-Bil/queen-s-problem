package com.example.queens_problem.logic;

import java.util.*;

public class NQueensPSO extends NQueens{
    float theta;
    double c1;
    double c2;

    int populationSize;
    int iterationNumber;



    public NQueensPSO(int n){
        super(n);
    }
    public NQueensPSO(int n,int theta,double c1, double c2,int populationSize,int iterationNumber) {
        super(n);
        this.theta=theta;
        this.c1=c1;
        this.c2=c2;
        this.populationSize=populationSize;
        this.iterationNumber=iterationNumber;
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

    private Particle rechercheTabou(Particle particle, int maxIterations) {

        int iter=0;
        ArrayList<boolean[][]> tabuList=new ArrayList<>();
        Particle bestNeighbour=particle.clone();
        while (iter<maxIterations){
                ArrayList<Particle> neighbours;
                neighbours=generateNeighbours(bestNeighbour,tabuList);
                neighbours.sort(Comparator.comparingInt(Particle::getFitnessValue));
                Particle neighbour=neighbours.get(0);
                    if(neighbour.getFitnessValue() < bestNeighbour.getFitnessValue()){
                        bestNeighbour = neighbour;
                    }
                    tabuList.add(neighbour.position);
            iter++;
        }
        return bestNeighbour;
    }


    private ArrayList<Particle> generateNeighbours(Particle currentSolution,ArrayList<boolean[][]> tabuList) {
        ArrayList<Particle> neighbours=new ArrayList<>();
        for(int i=0;i<n;i++){
            int col=0;
            while (!currentSolution.position[i][col]){
                col++;
            }
            Particle neighbour = currentSolution.clone();
            int newCol = (int) (Math.random() * n);
            while (newCol ==col) {
                newCol= (int) (Math.random() * n);
            }
            neighbour.position[i][col] = false;
            neighbour.position[i][newCol] = true;
            neighbour.fitnessValue=calculateFitness(neighbour.position);
            neighbours.add(neighbour);
//            if(!isTabou(neighbour,tabuList))
//                neighbours.add(new Node(neighbour,0,calculateFitness(neighbour)));
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
    private boolean isTabou(boolean[][] neighbour, ArrayList<boolean[][]> tabuList) {
        for (boolean[][] tabuMove : tabuList) {
            if (Arrays.deepEquals(neighbour, tabuMove)) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected Result solve() {

        this.theta=1;
        this.c1=0.8;
        this.c2=0.3;
        this.iterationNumber=500;
        this.populationSize=500;
        double tabuSearchRate=0.2;
        double mutationRate=0.;
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
                if(Math.random()<tabuSearchRate){
                    Particle bestNeighbour=rechercheTabou(newParticle,100);
                    bestNeighbour.fitnessValue=calculateFitness(bestNeighbour.position);
                    if(bestNeighbour.getFitnessValue()<newParticle.getFitnessValue()){
                        newParticle=bestNeighbour;
                    }
                }
                if(Math.random()<mutationRate){
                    mutate(newParticle);
                    newParticle.fitnessValue=calculateFitness(newParticle.position);
                }
                if(newParticle.fitnessValue<calculateFitness(newParticle.pbest)){
                    newParticle.pbest=newParticle.position;
                }
                particles.set(i,newParticle);
            }
            iteration++;
            particles.sort(Comparator.comparingInt(Particle::getFitnessValue));
            gbest=particles.get(0);
            System.out.println("Iteration : "+iteration + " - "+gbest.getFitnessValue());
        }
        return new Result( gbest.position,gbest.getFitnessValue());
    }


}
