package com.example.queens_problem;

import com.example.queens_problem.logic.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Testing {
    public static void main(String[] args) {
        try {
//            printInCSV(27, 30, Algorithm.DFS);
//            printInCSV(13, 13, Algorithm.BFS);
//            printInCSV(30, 30, Algorithm.NCH);
//            printInCSV(29, 30, Algorithm.CCF);

//            printSolutions(29, 30, Algorithm.CCF);
            printSolutions(30, 30, Algorithm.NCH);
//            printSolutions(4, 21, Algorithm.DFS);
//            printSolutions(23, 26, Algorithm.DFS);
//            printSolutions(4, 12, Algorithm.BFS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void printSolutions(int start, int end, Algorithm algo) throws IOException {
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
        fw.append("Solutions,");
        for (int j = 0; j < resultsArrayList.size(); j++) {
            fw.append('{');
            boolean[][] solution = resultsArrayList.get(j).solution;
            for (int i = 0; i < solution.length; i++) {
                for (int h = 0; h < solution[i].length; h++) {
                    if (solution[i][h]) {
                        if (i == solution.length - 1) {
                            fw.append(String.valueOf(h));

                        } else {
                            fw.append(String.valueOf(h)).append(';');
                        }
                    }
                }
            }
            fw.append("},");
        }
        fw.close();
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





