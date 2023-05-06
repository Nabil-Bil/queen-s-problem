package com.example.queens_problem;

import com.example.queens_problem.logic.NQueensPSO;
import com.example.queens_problem.logic.Result;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class MyThread extends Thread {
    private int n;
    int[] PopulationSizesPSO;
    int[] maxIterationsPSO;
    double[] mutationRatesPSO;
    double[] c1;
    double[] c2;
    double[] theta;


    public MyThread(int n, int[] PopulationSizesPSO,
                    int[] maxIterationsPSO,
                    double[] mutationRatesPSO,
                    double[] c1,
                    double[] c2,
                    double[] theta
    ) {
        this.n = n;
        this.PopulationSizesPSO = PopulationSizesPSO;
        this.maxIterationsPSO = maxIterationsPSO;
        this.mutationRatesPSO = mutationRatesPSO;
        this.c1 = c1;
        this.c2 = c2;
        this.theta = theta;
    }

    @Override
    public void run() {

        String PSOFilePath = "PSOResults" + n + ".csv";
        CSVWriter PSOResultsWriter = null;
        try {
            PSOResultsWriter = new CSVWriter(new FileWriter(PSOFilePath, true));
//            String[] header = {"problem size", "Iterations", "Population size", "Rate", "C1", "C2", "theta", "fitness moyenne", "Excution time", "taux de reussite"};
//            PSOResultsWriter.writeNext(header);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int PopulationSize : PopulationSizesPSO) {
            for (int maxGeneration : maxIterationsPSO) {
                for (double rate : mutationRatesPSO) {
                    for (double c : c1) {
                        for (double cc : c2) {
                            for (double t : theta) {
                                long tempMoy = 0;
                                int tauxResussite = 0;
                                int fitnesseMoy = 0;
                                System.out.println(n + " , " + PopulationSize + " , " + maxGeneration + " , " + rate + " , " + c + " , " + cc + " , " + t);
                                for (int i = 0; i < 5; i++) {
                                    System.out.println("iteration " + (i + 1));
                                    long startTime = System.currentTimeMillis();
                                    NQueensPSO nQueens = new NQueensPSO(n, t, c, cc, PopulationSize, maxGeneration, rate);
                                    Result result = nQueens.solveAndGetResult();
                                    long endTime = System.currentTimeMillis();
                                    long durationInMillis = (endTime - startTime);
                                    tempMoy += durationInMillis;
                                    if (result.fitness == 0) {
                                        tauxResussite++;
                                    }
                                    fitnesseMoy += result.fitness;
                                }
                                tempMoy = tempMoy / 5;
                                tauxResussite = tauxResussite * 20;
                                fitnesseMoy = fitnesseMoy / 5;
                                String[] row = {String.valueOf(n), String.valueOf(PopulationSize), String.valueOf(maxGeneration), String.valueOf(rate), String.valueOf(c), String.valueOf(cc), String.valueOf(t), String.valueOf(fitnesseMoy), String.valueOf(tempMoy), String.valueOf(tauxResussite)};
                                PSOResultsWriter.writeNext(row);
                                try {
                                    PSOResultsWriter.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

        }

        try {
            PSOResultsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
