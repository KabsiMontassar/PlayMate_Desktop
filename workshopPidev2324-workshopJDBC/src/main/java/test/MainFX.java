package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// --------------------

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;



import java.io.IOException;

public class MainFX extends Application {

    /*
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("chercherAdversaire.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("reserverTerrainVersion2.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("choix2.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("LancerVous.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("BlackList.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

     */




    private static final int width = 1280;
    private static final int height = 720;

    private static final int PLAYER_HEIGHT = 250;
    private static final int PLAYER_WIDTH = 20;
    private static final double BALL_R = 20;

    private int ballYSpeed = 2;
    private int ballXSpeed = 2;

    private double playerOneYPos = height / 2;
    private double playerTwoYPos = height / 2;

    private double ballXPos = width / 2;
    private double ballYPos = height / 2;

    private int scoreP1 = 0;
    private int scoreP2 = 0;

    private boolean gameStarted;
    private int playerOneXPos = 0;
    private double playerTwoXPos = width - PLAYER_WIDTH;

    public void start(Stage stage) {
        stage.setTitle("PlayMate");

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        gc.fillRect(width / 2 - 5, 0, 10, height);

        gc.strokeOval(width / 2 - 75, height / 2 - 75, 150, 150);

        Image logo = new Image("test/LogoPlayMate.png");
        double logoWidth = 100; // Ajuster la largeur du logo selon votre préférence
        double logoHeight = logoWidth * (logo.getHeight() / logo.getWidth());
        gc.drawImage(logo, width / 2 - logoWidth / 2, height / 2 - logoHeight / 2, logoWidth, logoHeight);

        if (gameStarted) {
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;

            if (ballXPos < width - width / 4) {
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                playerTwoYPos = ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ? playerTwoYPos + 1 : playerTwoYPos - 1;
            }

            Image ballImage = new Image("test/ballon.png");
            gc.drawImage(ballImage, ballXPos, ballYPos, BALL_R * 2, BALL_R * 2);
/*
            gc.setFill(Color.WHITE);
            gc.fillRect(0, height / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
            gc.fillRect(width - PLAYER_WIDTH, height / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);

 */
        } else {
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click", width / 2, height / 2);

            ballXPos = width / 2;
            ballYPos = height / 2;

            ballXSpeed = new Random().nextInt(2) == 0 ? 2 : -2;
            ballYSpeed = new Random().nextInt(2) == 0 ? 2 : -2;
        }

        if (ballYPos > height || ballYPos < 0) ballYSpeed *= -1;

        if (ballXPos < playerOneXPos - PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }

        if (ballXPos > playerTwoXPos + PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }

        if (((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||
                ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }

        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public static void main(String[] args) {
        launch(args);
    }

    }


