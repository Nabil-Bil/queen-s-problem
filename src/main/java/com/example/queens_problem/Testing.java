package com.example.queens_problem;

import com.example.queens_problem.logic.NQueensPSO;
import com.example.queens_problem.logic.Result;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class Testing {
    public static void main(String[] args) {
////       20 , 40, 60, 80,100
//        int[] problemSizes = {100};
//        int[] mutationRates = {30,40, 50,60, 70, 80};
//        int[] maxIterations = {100, 250, 500,750, 1000,1250};
//        int[] PopulationSizes = {100, 500,750, 1000,1250};
//        String GAFilePath = "GAResults.csv";
//        CSVWriter gaResultsWriter = null;
//        try {
//            gaResultsWriter = new CSVWriter(new FileWriter(GAFilePath));
//            String[] header = {"problem size", "Rate", "Iterations", "Population size","fitness moyenne", "Excution time", "taux de reussite"};
//            gaResultsWriter.writeNext(header);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (int problemSize : problemSizes)
//        {
//            for (int rate : mutationRates) {
//                for (int iterations : maxIterations)
//                {
//                    for (int PopulationSize: PopulationSizes)
//                    {
//                        long tempMoy = 0;
//                        int tauxResussite = 0;
//                        int fitnesseMoy = 0;
//                        System.out.println(problemSize+" , "+rate+" , "+ iterations+" , "+PopulationSize);
//                        for (int i=0;i<5;i++)
//                        {
//                            System.out.println("iteration "+ (i+1));
//                            long startTime = System.currentTimeMillis();
//                            NQueensGA nQueens = new NQueensGA(problemSize,PopulationSize,iterations,rate);
//                            Result result = nQueens.solveAndGetResult();
//                            long endTime = System.currentTimeMillis();
//                            long durationInMillis = (endTime - startTime);
//                            tempMoy += durationInMillis;
//                            if(result.fitness == 0)
//                            {
//                                tauxResussite++;
//                            }
//                            fitnesseMoy += result.fitness;
//                        }
//                        tempMoy = tempMoy/5;
//                        tauxResussite = tauxResussite*20;
//                        fitnesseMoy = fitnesseMoy/5;
//                        String[] row = {String.valueOf(problemSize), String.valueOf(rate),String.valueOf(iterations), String.valueOf(PopulationSize), String.valueOf(fitnesseMoy),String.valueOf(tempMoy),String.valueOf(tauxResussite)};
//                        gaResultsWriter.writeNext(row);
//                        try {
//                            gaResultsWriter.flush();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//
//        try {
//            gaResultsWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int[] problemSizesPSO = {20, 40, 60, 80, 100};
//        int[] maxIterationsPSO = {100, 250, 500, 750, 1000, 1250};
//        int[] mutationRatesPSO = {5, 10, 15, 20, 25, 30};
//        int[] PopulationSizesPSO = {100, 500, 750, 1000, 1250};
//        int[] c1 = {1, 2, 3, 4, 5};
//        int[] c2 = {1, 2, 3, 4, 5};
//        int[] theta = {1, 2, 3, 4, 5};
//        String PSOFilePath = "PSOResults.csv";
//        CSVWriter PSOResultsWriter = null;
//        try {
//            PSOResultsWriter = new CSVWriter(new FileWriter(PSOFilePath));
//            String[] header = {"problem size", "Iterations", "Population size", "Rate", "C1", "C2", "theta", "fitness moyenne", "Excution time", "taux de reussite"};
//            PSOResultsWriter.writeNext(header);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (int problemSize : problemSizesPSO) {
//            for (int PopulationSize : PopulationSizesPSO) {
//                for (int maxGeneration : maxIterationsPSO) {
//                    for (int rate : mutationRatesPSO) {
//                        for (int c : c1) {
//                            for (int cc : c2) {
//                                for (int t : theta) {
//                                    long tempMoy = 0;
//                                    int tauxResussite = 0;
//                                    int fitnesseMoy = 0;
//                                    System.out.println(problemSize + " , " + PopulationSize + " , " + maxGeneration + " , " + rate + " , " + c + " , " + cc + " , " + t);
//                                    for (int i = 0; i < 5; i++) {
//                                        System.out.println("iteration " + (i + 1));
//                                        long startTime = System.currentTimeMillis();
//                                        NQueensPSO nQueens = new NQueensPSO(problemSize, t, c, cc, PopulationSize, maxGeneration, rate);
//                                        Result result = nQueens.solveAndGetResult();
//                                        long endTime = System.currentTimeMillis();
//                                        long durationInMillis = (endTime - startTime);
//                                        tempMoy += durationInMillis;
//                                        if (result.fitness == 0) {
//                                            tauxResussite++;
//                                        }
//                                        fitnesseMoy += result.fitness;
//                                    }
//                                    tempMoy = tempMoy / 5;
//                                    tauxResussite = tauxResussite * 20;
//                                    fitnesseMoy = fitnesseMoy / 5;
//                                    String[] row = {String.valueOf(problemSize), String.valueOf(PopulationSize), String.valueOf(maxGeneration), String.valueOf(rate), String.valueOf(c), String.valueOf(cc), String.valueOf(t), String.valueOf(fitnesseMoy), String.valueOf(tempMoy), String.valueOf(tauxResussite)};
//                                    PSOResultsWriter.writeNext(row);
//                                    try {
//                                        PSOResultsWriter.flush();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//
//        try {
//            PSOResultsWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        int[] problemSizesPSO = {20, 40, 60};

        for (int psoSize : problemSizesPSO) {

            if (psoSize == 20) {
                int[] PopulationSizesPSO = {1000};
                int[] maxIterationsPSO = {750, 1000};
                double[] mutationRatesPSO = {0.05, 0.15, 0.30};
                double[] c1 = {0.5, 1, 2,};
                double[] c2 = {0.5, 1, 2};
                double[] theta = {0.1, 0.4, 1};
                MyThread thread = new MyThread(psoSize, PopulationSizesPSO,
                        maxIterationsPSO,
                        mutationRatesPSO,
                        c1,
                        c2,
                        theta
                );
                thread.start();

            } else if (psoSize == 40) {
                int[] maxIterationsPSO = {100, 250, 500, 750, 1000};

                int[] PopulationSizesPSO = {500, 750, 1000};
                double[] mutationRatesPSO = {0.05, 0.15, 0.30};
                double[] c1 = {0.5, 1, 2,};
                double[] c2 = {0.5, 1, 2};
                double[] theta = {0.1, 0.4, 1};
                MyThread thread = new MyThread(psoSize, PopulationSizesPSO,
                        maxIterationsPSO,
                        mutationRatesPSO,
                        c1,
                        c2,
                        theta
                );
                thread.start();

            } else {
                int[] maxIterationsPSO = {100, 250, 500, 750, 1000};

                int[] PopulationSizesPSO = {500, 750, 1000};
                double[] mutationRatesPSO = {0.05, 0.15, 0.30};
                double[] c1 = {0.5, 1, 2,};
                double[] c2 = {0.5, 1, 2};
                double[] theta = {0.1, 0.4, 1};
                MyThread thread = new MyThread(psoSize, PopulationSizesPSO,
                        maxIterationsPSO,
                        mutationRatesPSO,
                        c1,
                        c2,
                        theta
                );
                thread.start();

            }

        }


    }

}





