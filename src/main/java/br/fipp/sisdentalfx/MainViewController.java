package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.util.UIControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainViewController {
    public void onAdm(MouseEvent mouseEvent) throws IOException {
        UIControl.trocaPainel("adm-view.fxml");
        //Platform.runLater(()->{
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("adm-view.fxml"));
//        fxmlLoader.load();
//        HelloController.staticpainel.setCenter(fxmlLoader.getRoot());
        //});
    }

    public void onSecretaria(MouseEvent mouseEvent) throws IOException {
        UIControl.trocaPainel("agendamento-view.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("agendamento-view.fxml"));
//        fxmlLoader.load();
//        HelloController.staticpainel.setCenter(fxmlLoader.getRoot());
    }

    public void onDentista(MouseEvent mouseEvent) {
    }

    public void onConfig(MouseEvent mouseEvent) {
    }


    public void onMouseEntered(MouseEvent mouseEvent) {
        ((ImageView)mouseEvent.getSource()).setEffect(new InnerShadow());

    }

    public void onMouseExited(MouseEvent mouseEvent) {
        ((ImageView)mouseEvent.getSource()).setEffect(null);
    }

}
