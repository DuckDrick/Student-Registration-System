package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    @FXML
    private Button add, look;
    @FXML
    private AnchorPane anchor;
    public void GoAdd(ActionEvent event) throws IOException {
        if(event.getSource()==add)
            ((Stage)anchor.getScene().getWindow()).setScene(new Scene(FXMLLoader.load(getClass().getResource("add.fxml"))));
        else if(event.getSource()==look)
            ((Stage)anchor.getScene().getWindow()).setScene(new Scene(FXMLLoader.load(getClass().getResource("perziura.fxml"))));
        else
            ((Stage)anchor.getScene().getWindow()).setScene(new Scene(FXMLLoader.load(getClass().getResource("lankomumas.fxml"))));
    }

    public void initialize(){

    }

}
