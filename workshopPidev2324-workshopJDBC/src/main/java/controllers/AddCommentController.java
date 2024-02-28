package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;
//*******************************************************************************************
public class AddCommentController {
    @FXML
    private TextArea commentTextArea;
    @FXML
    private HBox ratingStars;
    //*******************************************************************************************
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 5; i++) {
            ImageView star = new ImageView(new Image("/star.png"));
            star.setFitHeight(30);
            star.setFitWidth(30);
            int rating = i + 1;
            star.setOnMouseClicked(e -> {
                System.out.println("User rated: " + rating);// You can store the rating value here or perform any other actions
            });
            ratingStars.getChildren().add(star);}}
    //*******************************************************************************************
    @FXML
    private void submitComment() {
        String comment = commentTextArea.getText();}}