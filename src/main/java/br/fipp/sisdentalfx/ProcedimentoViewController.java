package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.ProcedimentoDAL;
import br.fipp.sisdentalfx.db.entidades.Procedimento;
import br.fipp.sisdentalfx.db.util.DB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProcedimentoViewController  implements Initializable {
    public TextField tfID;
    public TextField tfDesc;
    public TextField tfTempo;
    public Button btnAceitar;
    public Button btnRecusar;
    public TextField tfValor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()-> tfID.requestFocus());
        btnRecusar.setOnAction(e->{((Control)e.getSource()).getScene().getWindow().hide();});
        btnAceitar.setOnAction(e->gravarProcedimento(e));
    }

    private void gravarProcedimento(ActionEvent e) {
        Procedimento procedimento;
        String id = tfID.getText();
        String desc = tfDesc.getText();
        String preco = tfValor.getText();
        String tempo = tfTempo.getText();


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
            alert.setContentText("O Valor deve ser um número válido.");
            alert.showAndWait();
            return;
        }

        int Id;
        try {
            Id = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("O ID deve ser um número válido.");
            alert.showAndWait();
            return;
        }

        int Tempo;
        try {
            Tempo = Integer.parseInt(tempo);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("O Tempo deve ser um número válido.");
            alert.showAndWait();
            return;
        }

        procedimento = new Procedimento(Id, desc, Tempo, Preco);

        ProcedimentoDAL procedimentoDAL = new ProcedimentoDAL();
        boolean gravacaoComSucesso = procedimentoDAL.gravar(procedimento);

        if (!gravacaoComSucesso) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar o novo procedimento: " + DB.getCon().getMensagemErro());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Procedimento gravado com sucesso!");
            alert.showAndWait();
        }

        ((Control) e.getSource()).getScene().getWindow().hide();
    }
}