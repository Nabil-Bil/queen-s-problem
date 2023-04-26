package com.example.queens_problem.logic;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
       NQueensPSO nQueensPSO=new NQueensPSO(5);
       Result result=nQueensPSO.solve();
       System.out.println(Arrays.deepToString(result.solution));
    }

}
