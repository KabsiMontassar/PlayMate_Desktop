package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

       FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("afficherEquipe.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setScene(scene);
      //  ((AfficherController) fxmlLoader.getController()).initialize();
        stage.show();
    }
}
