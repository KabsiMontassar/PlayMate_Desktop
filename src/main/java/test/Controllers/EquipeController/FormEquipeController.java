package test.Controllers.EquipeController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import models.Equipe;
import models.User;
import services.GestionEquipe.MemberService;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormEquipeController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private Button addPlayerBtn;

    @FXML
    private Button removePlayerBtn;
    private Equipe equipe ;

    private String mode = "ADD";

    @FXML
    private TableView<User> sourceTableView ;

    @FXML
    private TableView<User> targetTableView ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    private int IdUser;
    public int GetIdUser() {
        return this.IdUser;
    }

    public void SetIdUser(int idUser) throws SQLException {



        this.IdUser = idUser;
        this.buildTableView(this.sourceTableView, this.getSourceList());
        this.buildTableView(this.targetTableView, this.getTargetList());
        addDragAndDropHandlers(sourceTableView, targetTableView);
        addDragAndDropHandlers(targetTableView, sourceTableView);

    }
    @FXML
    public void updatePlayerList(ActionEvent event){

        this.targetTableView.getItems().add(this.sourceTableView.getSelectionModel().getSelectedItem());
        this.sourceTableView.getItems().remove(this.sourceTableView.getSelectionModel().getSelectedItem());
    }

    public List<User> getSourceList(){

        MemberService servive = new MemberService();
        try {
            return  this.mode.equals("ADD") ? servive.findAll() : servive.findExternMembresByEquipe(this.equipe.getIdEquipe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getTargetList(){

        MemberService servive = new MemberService();
        try {
            return  this.mode.equals("ADD") ? new ArrayList<>() : servive.findMembresByEquipe(this.equipe.getIdEquipe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildTableView(TableView<User> tableView, List<User> items){

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableView.getColumns().add(nameColumn);
        ObservableList<User> data = FXCollections.observableList(items);
        tableView.setItems(data);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Selected: " + newSelection.getName() );
            }
            else {
                tableView.getSelectionModel().clearSelection(0);
            }
        });
    }

    private void addDragAndDropHandlers(TableView<User> sourceTableView, TableView<User> targetTableView) {
        sourceTableView.setOnDragDetected(event -> {
            Dragboard db = sourceTableView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                content.put(DataFormat.PLAIN_TEXT, objectMapper.writeValueAsString(sourceTableView.getSelectionModel().getSelectedItem()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            db.setContent(content);
            event.consume();
        });

        targetTableView.setOnDragOver(event -> {
            if (event.getGestureSource() != targetTableView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        targetTableView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                try {
                    targetTableView.getItems().add(new User());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        sourceTableView.setOnDragDone(DragEvent::consume);
        targetTableView.setOnDragDone(DragEvent::consume);
    }
}
