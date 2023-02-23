package com.example.queens_problem;

import com.example.queens_problem.logic.QueensProblemDFS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;


public class HomeController {


    @FXML
    private Canvas chess;

    @FXML
    private Label label;

    @FXML
    private Button sizeButton;

    @FXML
    private TextField sizeTextField;

    @FXML
    private ImageView next;

    @FXML
    private ImageView previous;

    @FXML
    private Label nSolutions;

    int index;
    int numberOfSolution;
    QueensProblemDFS queensProblemDFS;
    List<boolean[][]> solutions;
    int chessSize;

    @FXML
    void onSizeButtonClick(ActionEvent event) {
        GraphicsContext gc = chess.getGraphicsContext2D();
        int chessSize = Integer.parseInt(sizeTextField.getText());
        queensProblemDFS = new QueensProblemDFS(chessSize);


        Chess.drawChess(chessSize, chess);
        solutions = new ArrayList<>();
        queensProblemDFS.solve(0, solutions);
        numberOfSolution = solutions.size();
        index = 1;
        nSolutions.setText(index + "/" + solutions.size());

        Chess.drawQueens(queensProblemDFS, chessSize, chess, solutions, index);
    }

    @FXML
    void onPreviousClick(MouseEvent event) {
        if (index > 1 && index <= numberOfSolution) {
            index--;
            nSolutions.setText(index + "/" + numberOfSolution);
            Chess.drawQueens(queensProblemDFS, chessSize, chess, solutions, index);
        }


    }

    @FXML
    void onNextClick(MouseEvent event) {
        if (index >= 1 && index < numberOfSolution) {
            index++;
            nSolutions.setText(index + "/" + numberOfSolution);

            Chess.drawQueens(queensProblemDFS, chessSize, chess, solutions, index);
        }
    }


}
