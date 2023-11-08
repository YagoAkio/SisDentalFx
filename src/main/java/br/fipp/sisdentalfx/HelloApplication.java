package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.util.DB;
import br.fipp.sisdentalfx.util.UIControl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage mainStage=null;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage=stage;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("SisDentalFX versão 0.1");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        if(DB.conectar()) {
            launch();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Problemas na conexão com o banco"+
                    DB.getCon().getMensagemErro());
            Platform.exit();
        }
    }
}