package com.example.queens_problem.logic;

import java.util.List;

public abstract class NQueens {
    int n;
    public NQueens(int n){
        this.n=n;
    }

    protected int evaluate(boolean[][] board) {
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j]) {
                    for (int k = 0; k < n; k++) {
                        if (board[i][k] && k != j) {
                            count++;
                            break;
                        }
                        if (board[k][j] && k != i) {
                            count++;
                            break;
                        }
                        int r = i - k;
                        int c = j - k;
                        if (r >= 0 && c >= 0 && board[r][c] && r != i) {
                            count++;
                            break;
                        }
                        r = i + k;
                        c = j - k;
                        if (r >= 0 && r < n && c >= 0 && c < n && board[r][c] && r != i && c != j) {
                            count++;
                            break;
                        }
                    }
                }
            }
        }
        return count;
    }
    protected boolean isSafe(boolean[][] board) {
        return  evaluate(board)==0;
    }

    protected boolean[][] copyBoard(boolean[][] original) {
        boolean[][] copy = new boolean[n][n];
        for (int i = 0; i < n; i++) {

            System.arraycopy(original[i], 0, copy[i], 0, n);
        }
        return copy;
    }


    public Result solveAndGetResult(){
        double begin=System.currentTimeMillis();
        Result result=solve();
        double end=System.currentTimeMillis();
        double executionTime=(end-begin)/1000;
        return new Result(executionTime,result.generatedNodes,result.developedNodes, result.solution);

    }

    protected abstract Result solve();


}
