package com.example.queens_problem;

import com.example.queens_problem.ChessColor;
import com.example.queens_problem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;

import java.util.Objects;


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
    void onSizeButtonClick(ActionEvent event) {
        GraphicsContext gc = chess.getGraphicsContext2D();
        int n = Integer.parseInt(sizeTextField.getText());
        drawChess(gc, n);
    }

    private void drawChess(GraphicsContext gc, int n) {


        gc.setStroke(Color.BLACK);
        double rectangle_size = chess.getWidth() / n;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Point rectangle_position = new Point(rectangle_size * j, rectangle_size * i);
                Point centerRect = getCenterRect(rectangle_position, rectangle_size);
                if (i % 2 == 0) {
                    if (j % 2 == 1) {
                        setColor(gc, ChessColor.Black);


                    } else {
                        setColor(gc, ChessColor.White);

                    }
                } else {
                    if (j % 2 == 0) {
                        setColor(gc, ChessColor.Black);
                    } else {
                        setColor(gc, ChessColor.White);
                    }
                }

                gc.fillRect(rectangle_position.getX(), rectangle_position.getY(), rectangle_size, rectangle_size);
                gc.strokeRect(rectangle_position.getX(), rectangle_position.getY(), rectangle_size, rectangle_size);
                if (i % 2 == 0) {
                    if (j % 2 == 1) {
                        drawQueen(gc, ChessColor.White, centerRect, rectangle_size);
                    } else {
                        drawQueen(gc, ChessColor.Black, centerRect, rectangle_size);

                    }
                } else {
                    if (j % 2 == 0) {
                        drawQueen(gc, ChessColor.White, centerRect, rectangle_size);

                    } else {
                        drawQueen(gc, ChessColor.Black, centerRect, rectangle_size);
                    }
                }

            }

        }

    }

    private Point getCenterRect(Point point, double size) {
        Point center = new Point(point.getX() + size / 4, point.getY() + size / 4);
        return center;
    }

    private void drawQueen(GraphicsContext gc, ChessColor color, Point position, double size) {
        String queen;
        if (color == ChessColor.Black) {
            queen = "black.png";
        } else {
            queen = "white.png";
        }
        gc.drawImage(new Image(Objects.requireNonNull(Main.class.getResource(queen).toString())), position.getX(), position.getY(), size / 2, size / 2);

    }

    private void setColor(GraphicsContext gc, ChessColor color) {
        if (color == ChessColor.Black) {
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.WHITE);
        } else {
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.BLACK);
        }


    }

}
