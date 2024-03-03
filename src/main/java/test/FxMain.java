//package test;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class FxMain extends Application {
//
//    private WebServer server;
//    static TextField addressTextField ;
//    @Override
//    public void start(Stage primaryStage) {
//
//        Scene scene = new Scene(root, 400, 200);
//
//        // Set the scene to the stage and show the stage
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Address Selector");
//        primaryStage.show();
//
//        // Start the web server
//        try {
//            server = new WebServer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Handle address selection from the web page
//
//
//
//    }
//
//    public static void setAddress(String Address){
//        addressTextField.setText(Address);
//    }
//
//    @Override
//    public void stop() throws Exception {
//        super.stop();
//        // Stop the server when the application is closed
//        if (server != null) {
//            server.stop();
//        }
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
