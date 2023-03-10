package com.example.queens_problem.logic;

import java.util.Comparator;
public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.getF(),o2.getF());
    }
}