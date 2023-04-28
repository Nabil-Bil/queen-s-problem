package com.example.queens_problem.logic;

import java.util.*;
public class NQueensGA extends NQueens {
    int populationSize;
    int maxGenerations;
    int rate;
    public NQueensGA(int n,int populationSize,int maxGenerations,int rate) {
        super(n);
        this.populationSize=populationSize;
        this.maxGenerations=maxGenerations;
        this.rate=rate;
    }

    private ArrayList<Individual> generateRandomPopulation(int populationSize) {
        ArrayList<Individual> population = new ArrayList<>(populationSize);

        for (int i = 0; i < populationSize; i++) {
            // Generate a random position for each queen in this individual
            boolean[][] solution = new boolean[n][n];
            for (int j = 0; j < n; j++) {
                int randomPosition = (int) (Math.random() * n);
                solution[j][randomPosition]=true;
            }
            Individual node = new Individual(solution, calculateFitness(solution));
            population.add(node);
        }
        return population;
    }

    private ArrayList<Individual> elitistSelection(ArrayList<Individual> population, int M) {
        PriorityQueue<Individual> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Individual::getFitnessValue));
        priorityQueue.addAll(population);
        ArrayList<Individual> selectedIndividuals = new ArrayList<>(M);

        for (int i = 0; i < M; i++) {
            selectedIndividuals.add(priorityQueue.poll());
        }
        return selectedIndividuals;

    }

    private ArrayList<Individual> selection(ArrayList<Individual> population, int rate) {
        int size = (population.size() * rate) / 100;
        ArrayList<Individual> selectedIndividuals;

        selectedIndividuals = elitistSelection(population, size);


        Collections.shuffle(selectedIndividuals);

        return selectedIndividuals;
    }

    private ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
        boolean[][] child1 = new boolean[n][n];
        boolean[][] child2 = new boolean[n][n];
        int crossoverPoint = (int) (Math.random() * (n - 1)) + 1;

        // Copy first part of parent 1 into both children
        for (int i = 0; i < crossoverPoint; i++) {
            for (int j = 0; j < n; j++) {
                child1[i][j] = parent1.solution[i][j];
                child2[i][j] = parent2.solution[i][j];
            }
        }

        // Copy second part of parent 2 into both children
        for (int i = crossoverPoint; i < n; i++) {
            for (int j = 0; j < n; j++) {
                child1[i][j] = parent2.solution[i][j];
                child2[i][j] = parent1.solution[i][j];
            }
        }
        ArrayList<Individual> children = new ArrayList<>();

        children.add(new Individual(child1, calculateFitness(child1)));
        children.add(new Individual(child2,calculateFitness(child2)));
        return children;
    }

    private ArrayList<Individual> crossoverPopulation(ArrayList<Individual> selectedIndividual) {
        Stack<Individual> nodeStack = new Stack<>();
        ArrayList<Individual> crossoverPopulation = new ArrayList<>();
        nodeStack.addAll(selectedIndividual);
        if (nodeStack.size() % 2 != 0) {
            nodeStack.pop();
        }

        for (int i = 0; i < nodeStack.size() / 2; i++) {
            Individual parent1 = nodeStack.pop();
            Individual parent2 = nodeStack.pop();
            crossoverPopulation.addAll(crossover(parent1, parent2));
        }
        Collections.shuffle(crossoverPopulation);
        return crossoverPopulation;
    }

    private Individual mutate(boolean[][] individual) {
        boolean[][] copyIndividual = copyBoard(individual);
        Random random = new Random();
        int row = random.nextInt(n);
        int currentQueenPos = -1;
        for (int col = 0; col < n; col++) {
            if (copyIndividual[row][col]) {
                currentQueenPos = col;
                break;
            }
        }
        int newQueenPos;
        do {
            newQueenPos = random.nextInt(n);
        } while (newQueenPos == currentQueenPos);

        copyIndividual[row][currentQueenPos] = false;
        copyIndividual[row][newQueenPos] = true;

        return new Individual(copyIndividual, calculateFitness(copyIndividual));
    }

    ArrayList<Individual> mutateChildren(ArrayList<Individual> children) {
        ArrayList<Individual> mutatedChildren = new ArrayList<>();
        for (Individual child : children) {
            mutatedChildren.add(mutate(child.solution));
        }
        return mutatedChildren;
    }

    public ArrayList<Individual> replacePopulation(ArrayList<Individual> currentPopulation, ArrayList<Individual> children, ArrayList<Individual> mutatedChildren) {
        // Sort current population and children population in ascending order of f values
        ArrayList<Individual> allPopulation = new ArrayList<>();
        allPopulation.addAll(currentPopulation);
        allPopulation.addAll(children);
        allPopulation.addAll(mutatedChildren);

        allPopulation.sort(Comparator.comparingInt(Individual::getFitnessValue));
        ArrayList<Individual> newPopulation = new ArrayList<>();
        for (int i = 0; i < currentPopulation.size(); i++) {
            newPopulation.add(allPopulation.get(i));
        }
        return newPopulation;
    }

    @Override
    protected Result solve() {
        // Generate initial random population
        ArrayList<Individual> population = generateRandomPopulation(populationSize);
        int generationCount = 0;

        // Keep iterating until a solution is found or the maximum number of generations is reached
        while (generationCount < maxGenerations) {
            // Sort the population by fitness
            population.sort(Comparator.comparingInt(Individual::getFitnessValue));

            // Check if the best individual is a solution
            if (population.get(0).getFitnessValue() == 0) {
                return new Result(population.get(0).solution,population.get(0).getFitnessValue());
            }

            // Select the best individuals for crossover
            ArrayList<Individual> selectedPopulation = selection(population, rate);

            // Perform crossover on selected individuals
            ArrayList<Individual> children = crossoverPopulation(selectedPopulation);
            ArrayList<Individual> mutatedChildren = mutateChildren(children);
            // Replace the old population with the new one
            population = replacePopulation(population, children, mutatedChildren);
            generationCount++;
        }
        population.sort(Comparator.comparingInt(Individual::getFitnessValue));
        return new Result(population.get(0).solution,population.get(0).getFitnessValue());
    }
}
