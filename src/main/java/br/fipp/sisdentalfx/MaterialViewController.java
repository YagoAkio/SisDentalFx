package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.MaterialDAL;
import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.Dentista;
import br.fipp.sisdentalfx.db.entidades.Material;
import br.fipp.sisdentalfx.db.util.DB;
import br.fipp.sisdentalfx.util.MaskFieldUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MaterialViewController implements Initializable {
    public TextField tfID;
    public TextField tfPreco;
    public TextField tfDesc;
    public Button btnAceitar;
    public Button btnRecusar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()-> tfID.requestFocus());
        btnRecusar.setOnAction(e->{((Control)e.getSource()).getScene().getWindow().hide();});
        btnAceitar.setOnAction(e->gravarMaterial(e));
        MaskFieldUtil.cpfField(tfID);
        MaskFieldUtil.cepField(tfID);


    }

    private void gravarMaterial(ActionEvent e) {
        Material material;
        String id = tfID.getText();
        String desc = tfDesc.getText();
        String preco = tfPreco.getText();


        if (id.isEmpty() || desc.isEmpty() || preco.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        double Preco;
        try {
            Preco = Double.parseDouble(preco);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("O Preco deve ser um número válido.");
            alert.showAndWait();
            return;
        }

        int Id;
        try {
            Id = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("O Preco deve ser um número válido.");
            alert.showAndWait();
            return;
        }

        material = new Material(Id, desc, Preco);

        MaterialDAL materialDAL = new MaterialDAL();
        boolean gravacaoComSucesso = materialDAL.gravar(material);

        if (!gravacaoComSucesso) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar o novo material: " + DB.getCon().getMensagemErro());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Material gravado com sucesso!");
            alert.showAndWait();
        }

        ((Control) e.getSource()).getScene().getWindow().hide();
    }
}
