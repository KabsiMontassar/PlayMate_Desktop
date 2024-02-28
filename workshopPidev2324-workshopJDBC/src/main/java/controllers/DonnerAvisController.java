package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//*******************************************************************************************
public class DonnerAvisController {
    @FXML
    private Circle star1;
    @FXML
    private Circle star2;
    @FXML
    private Circle star3;
    @FXML
    private Circle star4;
    @FXML
    private Circle star5;
    @FXML
    private TextArea commentaireArea;
    //*******************************************************************************************
    @FXML
    public void initialize() {
        star1.setOnMouseClicked(event -> {
            star1.setFill(star1.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);
            star2.setFill(Color.TRANSPARENT);
            star3.setFill(Color.TRANSPARENT);
            star4.setFill(Color.TRANSPARENT);
            star5.setFill(Color.TRANSPARENT);});
        star2.setOnMouseClicked(event -> {
            star1.setFill(Color.YELLOW);
            star2.setFill(star2.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);
            star3.setFill(Color.TRANSPARENT);
            star4.setFill(Color.TRANSPARENT);
            star5.setFill(Color.TRANSPARENT);});
        star3.setOnMouseClicked(event -> {
            star1.setFill(Color.YELLOW);
            star2.setFill(Color.YELLOW);
            star3.setFill(star3.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);
            star4.setFill(Color.TRANSPARENT);
            star5.setFill(Color.TRANSPARENT);});
        star4.setOnMouseClicked(event -> {
            star1.setFill(Color.YELLOW);
            star2.setFill(Color.YELLOW);
            star3.setFill(Color.YELLOW);
            star4.setFill(star4.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);
            star5.setFill(Color.TRANSPARENT);});
        star5.setOnMouseClicked(event -> {
            star1.setFill(Color.YELLOW);
            star2.setFill(Color.YELLOW);
            star3.setFill(Color.YELLOW);
            star4.setFill(Color.YELLOW);
            star5.setFill(star5.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);});}
    //*******************************************************************************************
    public String getCommentaire() {
        return commentaireArea.getText();
    }
    //*******************************************************************************************
    public int getNote() {
        int note = 0;
        if (star1.getFill() == Color.YELLOW) note++;
        if (star2.getFill() == Color.YELLOW) note++;
        if (star3.getFill() == Color.YELLOW) note++;
        if (star4.getFill() == Color.YELLOW) note++;
        if (star5.getFill() == Color.YELLOW) note++;
        return note;}}