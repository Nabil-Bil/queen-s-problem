package com.example.queens_problem;

import com.example.queens_problem.logic.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

import java.util.Objects;


public class HomeController {


    @FXML
    private Canvas chess;

    @FXML
    private Label label;

    @FXML
    private Label runButton;

    @FXML
    private TextField sizeTextField;


    @FXML
    private Label bfs;

    @FXML
    private Label dfs;

    @FXML
    private Label time;

    @FXML
    private Label error;

    NQueens nQueens;
    int chessSize;

    @FXML
    private Ellipse circle;

    @FXML
    private Label ccf;

    @FXML
    protected Label noc;

    @FXML
    private Label developedNode;

    @FXML
    private Label generatedNode;


    Algorithm selectedAlgorithm = Algorithm.DFS;


    @FXML
    void onRunButtonClick(MouseEvent event) {
        try {
            chessSize = Integer.parseInt(sizeTextField.getText());

        } catch (Exception e) {
            error.setText("Enter a valid number");
            sizeTextField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

            return;
        }

        if (chessSize <= 3) {
            error.setText("Chess size must be greater than 3");
            sizeTextField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            return;

        } else {
            error.setText("");
            sizeTextField.setStyle("");

        }
        if (selectedAlgorithm == Algorithm.DFS) {
            nQueens = new NQueensDFS(chessSize);


        } else if (selectedAlgorithm == Algorithm.BFS) {
            nQueens = new NQueensBFS(chessSize);
        } else if (selectedAlgorithm == Algorithm.CCF) {
            nQueens = new NQueensCCF(chessSize);
        } else {
            nQueens = new NQueensNCH(chessSize);
        }
        Chess.drawChess(chessSize, chess);
        Result result = nQueens.solveAndGetResult();
        this.time.setText(String.format("%.2f(S)", result.executionTime));
        this.generatedNode.setText(String.valueOf(result.generatedNodes));
        this.developedNode.setText(String.valueOf(result.developedNodes));

        Chess.drawQueens(result.solution, chessSize, chess);
    }

    @FXML
    void chooseAlgorithm(MouseEvent event) {
        Control control = ((Control) event.getSource());
        String id = control.getId();
        double x = control.getLayoutX() + control.getWidth() / 2;
        double y = control.getLayoutY() + control.getHeight() / 2;

        if (Objects.equals(id, "dfs")) {
            selectedAlgorithm = Algorithm.DFS;


        } else if (Objects.equals(id, "bfs")) {
            selectedAlgorithm = Algorithm.BFS;


        } else if (Objects.equals(id, "ccf")) {
            selectedAlgorithm = Algorithm.CCF;
        } else {
            selectedAlgorithm = Algorithm.NCH;
        }
        circle.setLayoutX(x);
        circle.setLayoutY(y);
    }

}
