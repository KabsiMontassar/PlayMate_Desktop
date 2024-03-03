package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class MainFx extends Application {
    private WebServer server;
    static TextField addressTextField ;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFx.class.getResource("LoginRegistrationPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setScene(scene);
        stage.show();


        try {
            server = new WebServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        // Stop the server when the application is closed
        if (server != null) {
            server.stop();
        }
    }
    private void openMapInBrowser(String url) {
        try {
            // Open the default web browser
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
