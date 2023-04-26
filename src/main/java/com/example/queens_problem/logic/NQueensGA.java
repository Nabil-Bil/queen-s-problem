package com.example.queens_problem.logic;

import java.util.*;
public class NQueensGA extends NQueens {
    int populationSize;
    int maxGenerations;
    int rate;
    public NQueensGA(int n,int populationSize,int maxGenerations) {
        super(n);
        this.populationSize=populationSize;
        this.maxGenerations=maxGenerations;
    }

    private ArrayList<Node> generateRandomPopulation(int populationSize) {
        ArrayList<Node> population = new ArrayList<>(populationSize);

        for (int i = 0; i < populationSize; i++) {
            // Generate a random position for each queen in this individual
            boolean[][] state = new boolean[n][n];
            for (int j = 0; j < n; j++) {
                int randomPosition = (int) (Math.random() * n);
                for (int k = 0; k < n; k++) {
                    state[j][k] = k == randomPosition;
                }
            }
            Node node = new Node(state, 0, calculateFitness(state));
            population.add(node);
        }
        return population;
    }

    private ArrayList<Node> elitistSelection(ArrayList<Node> population, int M) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        priorityQueue.addAll(population);
        ArrayList<Node> selectedIndividuals = new ArrayList<>(M);

        for (int i = 0; i < M; i++) {
            selectedIndividuals.add(priorityQueue.poll());
        }
        return selectedIndividuals;

    }

    private ArrayList<Node> selection(ArrayList<Node> population, int rate) {
        int size = (population.size() * rate) / 100;
        ArrayList<Node> selectedIndividuals = new ArrayList<>(size);

        selectedIndividuals = elitistSelection(population, size);


        Collections.shuffle(selectedIndividuals);

        return selectedIndividuals;
    }

    private ArrayList<Node> crossover(Node parent1, Node parent2) {
        boolean[][] child1 = new boolean[n][n];
        boolean[][] child2 = new boolean[n][n];
        int crossoverPoint = (int) (Math.random() * (n - 1)) + 1;

        // Copy first part of parent 1 into both children
        for (int i = 0; i < crossoverPoint; i++) {
            for (int j = 0; j < n; j++) {
                child1[i][j] = parent1.state[i][j];
                child2[i][j] = parent2.state[i][j];
            }
        }

        // Copy second part of parent 2 into both children
        for (int i = crossoverPoint; i < n; i++) {
            for (int j = 0; j < n; j++) {
                child1[i][j] = parent2.state[i][j];
                child2[i][j] = parent1.state[i][j];
            }
        }
        ArrayList<Node> children = new ArrayList<>();

        children.add(new Node(child1, 0, calculateFitness(child1)));
        children.add(new Node(child2, 0, calculateFitness(child2)));
        return children;
    }

    private ArrayList<Node> crossoverPopulation(ArrayList<Node> selectedIndividual) {
        Stack<Node> nodeStack = new Stack<>();
        ArrayList<Node> crossoverPopulation = new ArrayList<>();
        nodeStack.addAll(selectedIndividual);
        if (nodeStack.size() % 2 != 0) {
            nodeStack.pop();
        }

        for (int i = 0; i < nodeStack.size() / 2; i++) {
            Node parent1 = nodeStack.pop();
            Node parent2 = nodeStack.pop();
            crossoverPopulation.addAll(crossover(parent1, parent2));
        }
        Collections.shuffle(crossoverPopulation);
        return crossoverPopulation;
    }

    private Node mutate(boolean[][] individual) {
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

        return new Node(copyIndividual, 0, calculateFitness(copyIndividual));
    }

    ArrayList<Node> mutateChildren(ArrayList<Node> children) {
        ArrayList<Node> mutatedChildren = new ArrayList<>();
        for (Node child : children) {
            mutatedChildren.add(mutate(child.state));
        }
        return mutatedChildren;
    }

    public ArrayList<Node> replacePopulation(ArrayList<Node> currentPopulation, ArrayList<Node> children, ArrayList<Node> mutatedChildren) {
        // Sort current population and children population in ascending order of f values
        ArrayList<Node> allPopulation = new ArrayList<>();
        allPopulation.addAll(currentPopulation);
        allPopulation.addAll(children);
        allPopulation.addAll(mutatedChildren);

        allPopulation.sort(Comparator.comparingInt(Node::getF));
        ArrayList<Node> newPopulation = new ArrayList<>();
        for (int i = 0; i < currentPopulation.size(); i++) {
            newPopulation.add(allPopulation.get(i));
        }
        return newPopulation;
    }

    @Override
    protected Result solve() {
        // Generate initial random population
        ArrayList<Node> population = generateRandomPopulation(populationSize);
        int generationCount = 0;

        // Keep iterating until a solution is found or the maximum number of generations is reached
        while (generationCount < maxGenerations) {
            // Sort the population by fitness
            population.sort(Comparator.comparingInt(Node::getF));

            // Check if the best individual is a solution
            if (population.get(0).getF() == 0) {
                return new Result(population.get(0).state,population.get(0).getF());
            }

            // Select the best individuals for crossover
            ArrayList<Node> selectedPopulation = selection(population, rate);

            // Perform crossover on selected individuals
            ArrayList<Node> children = crossoverPopulation(selectedPopulation);
            ArrayList<Node> mutatedChildren = mutateChildren(children);
            // Replace the old population with the new one
            population = replacePopulation(population, children, mutatedChildren);
            generationCount++;
        }
        population.sort(Comparator.comparingInt(Node::getF));
        return new Result(population.get(0).state,population.get(0).getF());
    }
}
