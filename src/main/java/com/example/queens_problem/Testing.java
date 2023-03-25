package com.example.queens_problem;

import com.example.queens_problem.logic.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Testing {
    public static void main(String[] args) {
        try {
            printInCSV(4, 25, Algorithm.DFS);
//            printInCSV(4, 25, Algorithm.BFS);
//            printInCSV(4, 25, Algorithm.NCH);
//            printInCSV(4, 25, Algorithm.CCF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void printInCSV(int start, int end, Algorithm algo) throws IOException {
        FileWriter fw = null;
        String fileName = "";
        switch (algo) {
            case DFS -> {
                fileName = "resultDFS.csv";
            }
            case BFS -> {
                fileName = "resultBFS.csv";
            }
            case CCF -> {
                fileName = "resultCCF.csv";
            }
            case NCH -> {
                fileName = "resultNCH.csv";
            }
        }

        fw = new FileWriter(fileName, true);


        ArrayList<Result> resultsArrayList = new ArrayList<>();


        for (int i = start; i <= end; i++) {
            Result results;
            NQueens nQueens = null;
            switch (algo) {
                case DFS -> {
                    nQueens = new NQueensDFS(i);
                }
                case BFS -> {
                    nQueens = new NQueensBFS(i);

                }
                case CCF -> {
                    nQueens = new NQueensCCF(i);

                }
                case NCH -> {
                    nQueens = new NQueensNCH(i);
                }
            }
            results = nQueens.solveAndGetResult();


            resultsArrayList.add(results);
        }
        fw.append(",");

        for (int j = 0; j < resultsArrayList.size(); j++) {
            if (j == resultsArrayList.size() - 1) {
                fw.append(String.valueOf(resultsArrayList.get(j).solution.length)).append("\n");
            } else {
                fw.append(String.valueOf(resultsArrayList.get(j).solution.length)).append(",");

            }
        }
        fw.append("Execution Time,");
        for (int j = 0; j < resultsArrayList.size(); j++) {
            if (j == resultsArrayList.size() - 1) {
                fw.append(String.valueOf(resultsArrayList.get(j).executionTime)).append("\n");
            } else {
                fw.append(String.valueOf(resultsArrayList.get(j).executionTime)).append(",");
            }
        }
        fw.append("Generated Nodes,");

        for (int j = 0; j < resultsArrayList.size(); j++) {
            if (j == resultsArrayList.size() - 1) {
                fw.append(String.valueOf(resultsArrayList.get(j).generatedNodes)).append("\n");
            } else {
                fw.append(String.valueOf(resultsArrayList.get(j).generatedNodes)).append(",");
            }
        }
        fw.append("Developed Nodes,");

        for (int j = 0; j < resultsArrayList.size(); j++) {
            if (j == resultsArrayList.size() - 1) {
                fw.append(String.valueOf(resultsArrayList.get(j).developedNodes)).append("\n");
            } else {
                fw.append(String.valueOf(resultsArrayList.get(j).developedNodes)).append(",");
            }
        }
        fw.close();
    }
}





