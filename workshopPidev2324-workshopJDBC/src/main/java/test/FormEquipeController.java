package test;

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
import models.Membre;
import services.EquipeService;
import services.MemberService;

import javax.swing.text.TabableView;
import java.net.URL;
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
    private TableView<Membre> sourceTableView ;

    @FXML
    private TableView<Membre> targetTableView ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public List<Membre> getSourceList(){

        MemberService servive = new MemberService();
        try {
            return  this.mode.equals("ADD") ? servive.findAll() : servive.findExternMembresByEquipe(this.equipe.getIdEquipe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Membre> getTargetList(){

        MemberService servive = new MemberService();
        try {
            return  this.mode.equals("ADD") ? new ArrayList<>() : servive.findMembresByEquipe(this.equipe.getIdEquipe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildTableView(TableView<Membre> tableView, List<Membre> items){

        TableColumn<Membre, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableView.getColumns().add(nameColumn);
        ObservableList<Membre> data = FXCollections.observableList(items);
        tableView.setItems(data);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Selected: " + newSelection.getNom() );
            }
            else {
                tableView.getSelectionModel().clearSelection(0);
            }
        });
    }

    private void addDragAndDropHandlers(TableView<Membre> sourceTableView, TableView<Membre> targetTableView) {
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
                targetTableView.getItems().add(new Membre(db.getString()));
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        sourceTableView.setOnDragDone(DragEvent::consume);
        targetTableView.setOnDragDone(DragEvent::consume);
    }
}
