package com.example.queens_problem.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class NQueens {
    int n;
    public NQueens(int n){
        this.n=n;
    }

    protected static int evaluate(boolean[][] board, int n) {
        int count = 0;
        int i,j;
        Queue<Queen> counted = new LinkedList<>();
        for(int col = 0; col<n; col++)
        {
            for(int row = 0; row<n; row++)
            {
                if(board[row][col])
                {
                    for(i=col+1; i<n; i++)
                    {
                        if(board[row][i])
                        {
                            if(!isCounted(counted,row,i))
                            {
                                count ++;
                                counted.add(new Queen(row,i));
                                break;
                            }
                        }
                    }
                    for(i = row-1,j = col+1; i>0 && j<n;i--,j++)
                    {
                        if(board[i][j])
                        {
                            //si la reine n'a pas déja été compté
                            if(!isCounted(counted,i,j))
                            {
                                count ++;
                                counted.add(new Queen(i,j));
                                break;
                            }
                        }
                    }
                    for(i = row+1,j = col+1; i<n && j<n;i++,j++)
                    {
                        if(board[i][j])
                        {
                            //si la reine n'a pas déja été compté
                            if(!isCounted(counted,i,j))
                            {
                                count ++;
                                counted.add(new Queen(i,j));
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(count!=0)
        {
            count ++;
        }
        return count;
    }
    protected static boolean isCounted(Queue<Queen> counted, int row , int col) {
        for(Queen c : counted)
        {
            if (c.row == row && c.col == col){
                return true;
            }
        }
        return false;
    }
    protected boolean isSafe(boolean[][] board, int n) {
        return  evaluate(board, n)==0;
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
