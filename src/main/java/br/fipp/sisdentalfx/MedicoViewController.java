package br.fipp.sisdentalfx;

        import br.fipp.sisdentalfx.db.dals.PessoaDAL;
        import br.fipp.sisdentalfx.db.entidades.Dentista;
        import br.fipp.sisdentalfx.db.entidades.Paciente;
        import br.fipp.sisdentalfx.db.util.DB;
        import br.fipp.sisdentalfx.util.MaskFieldUtil;
        import javafx.application.Platform;
        import javafx.concurrent.Task;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.input.KeyEvent;
        import org.json.JSONObject;

        import java.net.URL;
        import java.util.ResourceBundle;

public class MedicoViewController implements Initializable {
    public TextField tfID;
    public TextField tfNome;
    public TextField tfCRO;
    public TextField tfFone;
    public TextField tfEmail;
    public Button btnAceitar;
    public Button btnRecusar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()-> tfID.requestFocus());
        btnRecusar.setOnAction(e->{((Control)e.getSource()).getScene().getWindow().hide();});
        btnAceitar.setOnAction(e->gravarMedico(e));
        MaskFieldUtil.cpfField(tfID);
        MaskFieldUtil.cepField(tfID);
        MaskFieldUtil.foneField(tfFone);

    }

    private void gravarMedico(ActionEvent e) {
        Dentista dentista;
        String nome = tfNome.getText();
        String croText = tfCRO.getText();
        String telefone = tfFone.getText();
        String email = tfEmail.getText();


        if (nome.isEmpty() || croText.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        int cro;
        try {
            cro = Integer.parseInt(croText);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("O CRO deve ser um número válido.");
            alert.showAndWait();
            return;
        }

        dentista = new Dentista(nome, cro, telefone, email);

        PessoaDAL pessoaDAL = new PessoaDAL();
        boolean gravacaoComSucesso = pessoaDAL.gravar(dentista);

        if (!gravacaoComSucesso) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar o novo dentista: " + DB.getCon().getMensagemErro());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Dentista gravado com sucesso!");
            alert.showAndWait();
        }

        ((Control) e.getSource()).getScene().getWindow().hide();
    }

}
