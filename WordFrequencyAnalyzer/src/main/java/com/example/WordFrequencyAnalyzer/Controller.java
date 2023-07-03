package com.example.WordFrequencyAnalyzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML private TableView<Word> table;
    @FXML ListView<String> listBoxMain;

    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> items = FXCollections.observableArrayList (
                "The Raven, Edgar Allen Poe");
        listBoxMain.setItems(items);
    }


    @FXML
    protected void onAnalyzerButtonClick(ActionEvent event) {
        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setWidth(300);
        stage.setHeight(500);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Analyze");
        stage.show();

        final Label label = new Label("Word Occurrences");
        label.setFont(new Font("Arial", 20));

        // Table
        table = new TableView();
        table.setEditable(true);
        TableColumn<Word, String> wordCol = new TableColumn<>("Word");
        TableColumn<Word, String> freqCol = new TableColumn<>("Frequency");;

        wordCol.setCellValueFactory(new PropertyValueFactory<Word, String>("word"));
        freqCol.setCellValueFactory(new PropertyValueFactory<Word, String>("frequency"));

        table.getColumns().addAll(wordCol, freqCol);

        Word[] sortedList = WordCounter.wordCounter();
        ObservableList<Word> data = FXCollections.observableArrayList();

        for(int i = sortedList.length - 1; i >= 0; i--)
        {
            data.add(sortedList[i]);
        }

        table.setItems(data);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


}