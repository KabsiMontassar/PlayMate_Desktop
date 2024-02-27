package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("chercherAdversaire.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("reserverTerrainVersion2.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("choix2.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("LancerVous.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
