package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
//*******************************************************************************************
public class MainFx extends Application {
    @Override
    //throw exception pour verifier si le path de fichier fxml existe pour fair le load
    public void start(Stage primaryStage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/PageTerrain.fxml"));
        Parent root = loader.load();         //parent is the first page to be load
        //scene bordure de l'app change selon l'interface
        Scene scene = new Scene(root);
        primaryStage.setTitle("Liste des terrains");
        primaryStage.setScene(scene);
        Image icon = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_XTb5h9cUJ7x-FmV8s3QYXTKlh6oehZkpdg&usqp=CAU");
       primaryStage.getIcons().add(icon);
        primaryStage.show();}
    //*******************************************************************************************
    public static void main(String[] args) {launch(args);}}