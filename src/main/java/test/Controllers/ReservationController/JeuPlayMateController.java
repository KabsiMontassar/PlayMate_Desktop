package test.Controllers.ReservationController;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import models.JeuPlayMate;

public class JeuPlayMateController {
    @FXML
    private Canvas gameCanvas;

    @FXML
    private Pane gamePane; // Assurez-vous que cette Pane contient le Canvas dans votre fichier FXML

    private JeuPlayMate jeu;

    @FXML
    public void initialize() {

        jeu = new JeuPlayMate(gameCanvas);
    }
}
