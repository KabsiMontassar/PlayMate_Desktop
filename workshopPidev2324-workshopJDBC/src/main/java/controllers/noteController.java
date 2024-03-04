package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class noteController {

    public AnchorPane anchorpane;
    @FXML
    private ComboBox<Double> ratingComboBox;
    @FXML
    private ImageView ratingImage;

    @FXML
    public void initialize() {
        // Populate the ComboBox with ratings from 0.5 to 5.0
        ratingComboBox.setItems(FXCollections.observableArrayList(
                0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0
        ));
    }

    @FXML
    private void updateRatingImage() {
        double selectedRating = ratingComboBox.getValue();
        String imagePath = "/resources/";

        switch ((int) selectedRating) {
            case 0:
                imagePath += "0.5hst.png";
                break;
            case 1:
                imagePath += "1st.png";
                break;
            case 2:
                imagePath += "1.5sth.png";
                break;
            case 3:
                imagePath += "2st.png";
                break;
            case 4:
                imagePath += "2.5hst.png";
                break;
            case 5:
                imagePath += "3st.png";
                break;
            case 6:
                imagePath += "3.5hst.png";
                break;
            case 7:
                imagePath += "4st.png";
                break;
            case 8:
                imagePath += "4.5hst.png";
                break;
            case 9:
                imagePath += "5st.png";
                break;
            default:
                // Handle invalid or null rating
                return;
        }

        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ratingImage.setImage(image);
    }
}
