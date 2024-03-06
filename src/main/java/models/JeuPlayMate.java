package models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Random;

public class JeuPlayMate {
    private static final int width = 1280;
    private static final int height = 720;
    private static final int PLAYER_HEIGHT = 300; /*150*/
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
    private GraphicsContext gc;
    private Canvas canvas;

    public JeuPlayMate(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        init();
    }

    private void init() {
        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    private void run() {
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        gc.fillRect(width / 2 - 5, 0, 10, height);

        gc.strokeOval(width / 2 - 75, height / 2 - 75, 150, 150);

        //Image logo = new Image("src/main/resources/test/GestionReservation/LogoPlayMate.png");
        Image logo = new Image(getClass().getResourceAsStream("/test/GestionReservation/LogoPlayMate.png"));
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

            //Image ballImage = new Image("src/main/resources/test/GestionReservation/ballon.png");
            Image ballImage = new Image(getClass().getResourceAsStream("/test/GestionReservation/ballon.png"));
            gc.drawImage(ballImage, ballXPos, ballYPos, BALL_R * 2, BALL_R * 2);




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

    // Autres méthodes nécessaires pour le jeu...
}
