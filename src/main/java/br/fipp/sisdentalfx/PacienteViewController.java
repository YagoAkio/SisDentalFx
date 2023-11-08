package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.Paciente;
import br.fipp.sisdentalfx.db.util.DB;
import br.fipp.sisdentalfx.util.MaskFieldUtil;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class PacienteViewController implements Initializable {
    @FXML
    private TextArea taHisto;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfCEP;

    @FXML
    private TextField tfCPF;

    @FXML
    private Button btCancelar;

    @FXML
    private TextField tfCidade;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFone;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfRua;

    @FXML
    private TextField tfUF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()-> tfCPF.requestFocus()); // executa ao finalizar o carregamento do formulário
        btCancelar.setOnAction(e->{((Control)e.getSource()).getScene().getWindow().hide();});
        btConfirmar.setOnAction(e->gravarPaciente(e));
        MaskFieldUtil.cpfField(tfCPF);
        MaskFieldUtil.cepField(tfCEP);
        MaskFieldUtil.foneField(tfFone);
        tfCEP.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(tfCEP.getText().length()==9)
                {
                    Task task=new Task() {
                        @Override
                        protected Object call() throws Exception {
                            String dados=consultaCep(tfCEP.getText(),"json");
                            JSONObject json=new JSONObject(dados);
                            tfBairro.setText(json.getString("bairro"));
                            tfCidade.setText(json.getString("localidade"));
                            tfUF.setText(json.getString("uf"));
                            tfRua.setText(json.getString("logradouro"));
                            System.out.println(dados);
                            Platform.runLater(()->tfNumero.requestFocus());
                            return null;
                        }
                    };
                    new Thread(task).start();
                }
            }
        });

    }

    private void gravarPaciente(ActionEvent e) {
        Paciente paciente;
        //validar antes
        paciente = new Paciente(tfNome.getText(),tfCPF.getText(),tfCEP.getText(),tfRua.getText(),tfNumero.getText(),tfBairro.getText(),tfCidade.getText(),tfUF.getText(),tfFone.getText(),tfEmail.getText(),taHisto.getText());
        // gravar um novo paciente
        if(new PessoaDAL().gravar(paciente)==false)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar o paciente "+ DB.getCon().getMensagemErro());
            alert.showAndWait();
        }
        ((Control)e.getSource()).getScene().getWindow().hide();
    }
    private String consultaCep(String cep, String formato)
    {
        StringBuffer dados = new StringBuffer();
        try {
            URL url = new URL("https://viacep.com.br/ws/"+ cep + "/"+formato+"/");
            URLConnection con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while (null != (s = br.readLine()))
                dados.append(s);
            br.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dados.toString();
    }
}






