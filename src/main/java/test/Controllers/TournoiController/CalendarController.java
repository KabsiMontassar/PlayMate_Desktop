package test.Controllers.TournoiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.Tournoi;
import test.MainFx;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @FXML
    private Button btnretour;

    @FXML
    private AnchorPane calender;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calendar.getChildren().clear();
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();

    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getClientName() + ", " + calendarActivities.get(k).getDate().toLocalTime());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity: calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if(!calendarActivityMap.containsKey(activityDate)){
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);

                List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return  calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        for (Tournoi tournoi : TournoiData.tournois) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateDebut = LocalDate.parse(tournoi.getDatedebut(), formatter);
                if (dateDebut.getYear() == dateFocus.getYear() && dateDebut.getMonth().getValue() == dateFocus.getMonthValue()) {
                    // Convertir la date de début du tournoi en ZonedDateTime
                    ZonedDateTime time = dateDebut.atStartOfDay(dateFocus.getZone());
                    // Créer une nouvelle activité de calendrier avec le nom du tournoi et l'ajouter à la liste
                    CalendarActivity activity = new CalendarActivity(time, tournoi.getNom(), null); // 'null' peut être remplacé par un identifiant si nécessaire
                    calendarActivities.add(activity);
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }

        // Convertir la liste des activités en une carte organisée par jour du mois
        return createCalendarMap(calendarActivities);
    }
    @FXML
    void retour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoiClient.fxml"));
        AnchorPane root = loader.load();
        calender.getChildren().setAll(root);
    }
}