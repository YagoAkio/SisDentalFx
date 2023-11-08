package br.fipp.sisdentalfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public static BorderPane staticpainel;
    public BorderPane painel;
    public Button btHome;
    public Button btClose;
    public Label lbAcesso;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticpainel=painel;
        btHome.setTooltip(new Tooltip("Ir para o início"));
        btClose.setTooltip(new Tooltip("Fechar o sistema"));
        btHome.setDisable(true);

        lbAcesso.setOnMouseClicked(e->{
            try {
                btHome.setDisable(false);
                onHome(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    public void onHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        fxmlLoader.load();
        staticpainel.setCenter(fxmlLoader.getRoot());
    }
    public void onClose(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Finalizar o sistema?");
        if(alert.showAndWait().get() == ButtonType.OK)
            Platform.exit();
    }
}