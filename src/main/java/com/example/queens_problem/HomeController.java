package com.example.queens_problem;

import com.example.queens_problem.logic.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
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
    private Label ga;

    @FXML
    private Label pso;

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
    protected Label nch;

    @FXML
    private Label developedNode;

    @FXML
    private Label generatedNode;

    @FXML
    private Label generatedNodeText;

    @FXML
    private Label developedNodeText;
    @FXML
    private  Label change_params;

    Algorithm selectedAlgorithm = Algorithm.DFS;
    int populationSize=100;
    int maxGenerations=100;

    int rate=60;

    double muationRate=0.05;
    double c1=1;
    double c2=1;
    double theta=1;

    int populationSizePSO=100;
    int maxGenerationPSO=100;



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
        } else if (selectedAlgorithm == Algorithm.NCH) {
            nQueens = new NQueensNCH(chessSize);
        } else if(selectedAlgorithm == Algorithm.GA){
            nQueens = new NQueensGA(chessSize,populationSize,maxGenerations,rate);
        }else{
            nQueens=new NQueensPSO(chessSize,theta,c1,c2,populationSizePSO,maxGenerationPSO,muationRate);
        }
        Chess.drawChess(chessSize, chess);
        Result result = nQueens.solveAndGetResult();
        this.time.setText(String.format("%.2f(S)", result.executionTime));
        this.generatedNode.setText(String.valueOf(result.generatedNodes));
        if(nQueens.getClass()==NQueensGA.class||nQueens.getClass()== NQueensPSO.class){
            this.developedNode.setText(String.valueOf(result.fitness));
        }else{
            this.developedNode.setText(String.valueOf(result.developedNodes));
        }


        Chess.drawQueens(result.solution, chessSize, chess);
    }

    @FXML
    void chooseAlgorithm(MouseEvent event) {
        Control control = ((Control) event.getSource());
        String id = control.getId();
        double x = control.getLayoutX() + control.getWidth() / 2;
        double y = control.getLayoutY() + control.getHeight() / 2;
        if(Objects.equals(id, "dfs")||Objects.equals(id, "ccf")||Objects.equals(id, "nch")||Objects.equals(id, "bfs")){
            developedNodeText.setText("Developed Node:");
            generatedNodeText.setVisible(true);
            generatedNode.setVisible(true);
            change_params.setVisible(false);
            if(sizeTextField.getWidth()<250){
                sizeTextField.setPrefWidth(sizeTextField.getWidth()*2);
            }

        }else{
            developedNodeText.setText("Fitness Value");
            generatedNodeText.setVisible(false);
            generatedNode.setVisible(false);
            change_params.setVisible(true);
            if(sizeTextField.getWidth()>250){
                sizeTextField.setPrefWidth(sizeTextField.getWidth()/2);
            }

        }
        if (Objects.equals(id, "dfs")) {
            selectedAlgorithm = Algorithm.DFS;

        } else if (Objects.equals(id, "bfs")) {
            selectedAlgorithm = Algorithm.BFS;


        } else if (Objects.equals(id, "ccf")) {
            selectedAlgorithm = Algorithm.CCF;
        } else if (Objects.equals(id, "nch")) {
            selectedAlgorithm = Algorithm.NCH;
        } else if(Objects.equals(id, "ga")){
            selectedAlgorithm = Algorithm.GA;
        }else{
            selectedAlgorithm = Algorithm.PSO;
        }
        circle.setLayoutX(x);
        circle.setLayoutY(y);
    }
    @FXML
    void changeParams()  throws IOException{
        if(selectedAlgorithm==Algorithm.PSO){
            changeParamsPSO();
        }else{
            changeParamsGA();
        }
    }

    void changeParamsGA()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GaPopup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Change Parameters");
        stage.show();
        Slider generationSlider = (Slider) scene.lookup("#generationSlider");
        Slider populationSlider = (Slider) scene.lookup("#populationSlider");
        Slider selectionRateSlider = (Slider) scene.lookup("#selectionRateSlider");
        Label generationLabel=(Label) scene.lookup("#generationLabel");
        Label selectionRateLabel=(Label) scene.lookup("#selectionRateLabel");
        Label populationLabel=(Label) scene.lookup("#populationLabel");
        generationSlider.setValue(maxGenerations);
        populationSlider.setValue(populationSize);
        selectionRateSlider.setValue(rate);
        generationLabel.setText(String.valueOf(maxGenerations));
        populationLabel.setText(String.valueOf(populationSize));
        selectionRateLabel.setText(rate +"%");
        generationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            maxGenerations= newValue.intValue();
            generationLabel.setText(String.valueOf(maxGenerations));

        });
        selectionRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            rate= newValue.intValue();
            selectionRateLabel.setText(rate +"%");

        });
        populationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            populationSize= newValue.intValue();
            populationLabel.setText(String.valueOf(populationSize));

        });
        Label exit=(Label) scene.lookup("#exit");
        exit.setOnMouseClicked(e->{
            stage.close();
        });
    }
    void changeParamsPSO()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PSOPopup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Change Parameters");
        stage.show();
        Slider generationSlider = (Slider) scene.lookup("#generationPsoSlider");
        Slider populationSlider = (Slider) scene.lookup("#populationPsoSlider");
        Slider mutationRateSlider = (Slider) scene.lookup("#mutationRateSlider");
        Slider learningRateC1Slider=(Slider)scene.lookup("#learningRateC1Slider");
        Slider learningRateC2Slider=(Slider)scene.lookup("#learningRateC2Slider");
        Slider thetaSlider=(Slider)scene.lookup("#thetaSlider");

        Label learningRateC1Label=(Label)scene.lookup("#learningRateC1Label");
        Label learningRateC2Label=(Label)scene.lookup("#learningRateC2Label");
        Label thetaLabel=(Label)scene.lookup("#thetaLabel");
        DecimalFormat df=new DecimalFormat("#.##");
        Label generationLabel=(Label) scene.lookup("#generationPsoLabel");
        Label mutationRateLabel=(Label) scene.lookup("#mutationRateLabel");
        Label populationLabel=(Label) scene.lookup("#populationPsoLabel");
        generationSlider.setValue(maxGenerationPSO);
        populationSlider.setValue(populationSizePSO);
        mutationRateSlider.setValue(muationRate);
        thetaSlider.setValue(theta);
        learningRateC1Slider.setValue(c1);
        learningRateC2Slider.setValue(c2);
        generationLabel.setText(String.valueOf(maxGenerationPSO));
        populationLabel.setText(String.valueOf(populationSizePSO));
        thetaLabel.setText(String.valueOf(df.format(theta)));
        learningRateC1Label.setText(String.valueOf(df.format(c1)));
        learningRateC2Label.setText(String.valueOf(df.format(c2)));
        mutationRateLabel.setText((int)(muationRate*100) +"%");
        generationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            maxGenerationPSO= newValue.intValue();
            generationLabel.setText(String.valueOf(maxGenerationPSO));

        });
        mutationRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            muationRate= newValue.doubleValue();
            mutationRateLabel.setText((int)(muationRate*100) +"%");

        });
        populationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            populationSizePSO= newValue.intValue();
            populationLabel.setText(String.valueOf(populationSizePSO));

        });

        thetaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            theta= newValue.doubleValue();
            thetaLabel.setText(String.valueOf(df.format(theta)));

        });
        learningRateC1Slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            c1= newValue.doubleValue();
            learningRateC1Label.setText(String.valueOf(df.format(c1)));

        });
        learningRateC2Slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            c2= newValue.doubleValue();
            learningRateC2Label.setText(String.valueOf(df.format(c2)));

        });


        Label exit=(Label) scene.lookup("#exit");
        exit.setOnMouseClicked(e->{
            stage.close();
        });
    }

}
