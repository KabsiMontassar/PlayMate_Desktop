package test.Controllers.UserController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import services.GestionUser.UserService;
import test.Controllers.ReservationController.HistoriqueController;
import test.MainFx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class AdminPage {

    public Button btnlogout;
    public Button btnhistorique;
    public Button btnlisteNoir;
    @FXML
    private AnchorPane adminPage;

    @FXML
    private Text UserInactive;

    @FXML
    private BarChart<String, Number> chart;

    @FXML
    private Text element1;

    @FXML
    private Text element2;

    @FXML
    private Text element3;

    @FXML
    private Text element4;

    @FXML
    private Text element5;

    @FXML
    private Text element6;

    @FXML
    private Text userActive;

    UserService us = new UserService();

    private String[] getLastLinesFromFile() throws IOException {


        // Number of lines to read
        int numLines = 6;

        // Read the last lines from the file
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("D://Github//pidev_spartans_3a29//Pidev//src//main//resourcesuser_activity_log.txt"))) {
            String line;
            // Read lines until reaching the end of the file or the desired number of lines
            while ((line = reader.readLine()) != null && numLines > 0) {
                builder.insert(0, line + "\n"); // Insert the line at the beginning of the StringBuilder
                numLines--;
            }
        }

        // Split the concatenated lines and return
        return builder.toString().split("\n");
    }
    public void initialize() throws IOException, SQLException {

      userActive.setText(userActive.getText()+"  " + us.CountActive());
        UserInactive.setText(UserInactive.getText() +"  "+ us.CountInactive());


        String[] lastLines = getLastLinesFromFile();

        // Set the text of the remaining elements to the last lines

        if (lastLines.length >= 6) {

            element1.setText(lastLines[0]);
            element2.setText(lastLines[1]);
            element3.setText(lastLines[2]);
            element4.setText(lastLines[3]);
            element5.setText(lastLines[4]);
            element6.setText(lastLines[5]);
        }

        Map<String, Integer> userData = us.getUsersCreatedPerMonth();

        populateChart(userData);
    }
    private void populateChart(Map<String, Integer> userData) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Users");
        for (Map.Entry<String, Integer> entry : userData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        chart.getData().addAll(series);
    }

    public void logout() {
        try {

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/LoginRegistrationPage.fxml"));
            AnchorPane root = loader.load();

            adminPage.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voirlistenoir(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/BlackList.fxml"));
            AnchorPane root = loader.load();

            adminPage.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
