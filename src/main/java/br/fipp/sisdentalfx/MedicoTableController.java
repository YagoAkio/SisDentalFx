package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.Dentista;
import br.fipp.sisdentalfx.db.entidades.Paciente;
import br.fipp.sisdentalfx.db.entidades.Pessoa;
import br.fipp.sisdentalfx.util.UIControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MedicoTableController implements Initializable {
    public TableColumn <Pessoa,Integer> colID2;
    public TableColumn <Pessoa,String> colNome2;
    public TableColumn <Dentista,Integer> colCRO;
    public TableColumn <Dentista,String> colFone;
    public TextField tfPesquisa;
    public Button btNovo;
    public Button btClose;
    public TableView <Pessoa> tabela;
    public TableColumn <Dentista,String> colEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->{((Button)e.getSource()).getScene().getWindow().hide();});
        preencherTabela("");
    }

    private void preencherTabela(String filtro) {
        List<Pessoa> pessoas = new PessoaDAL().get(filtro,new Dentista());
        colID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCRO.setCellValueFactory(new PropertyValueFactory<>("cro"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tabela.setItems(FXCollections.observableArrayList(pessoas));
    }

    public void onFiltrar(KeyEvent keyEvent) {
        String filtro=tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(pac_nome) like '%"+filtro+"%'");
    }

    public void OnNovoMedico(ActionEvent actionEvent) {
        UIControl.abreModal("medico-view.fxml");
        preencherTabela("");
    }

    public void onAlterar(ActionEvent actionEvent) {
        Pessoa dentistaSelecionado = tabela.getSelectionModel().getSelectedItem();
        if (dentistaSelecionado != null) {
            UIControl.abreModal("medico-view.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione um dentista para alterar seu cadastro.");
            alert.showAndWait();
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        if(tabela.getSelectionModel().getSelectedItem()!=null)
        {
            Pessoa pessoa = tabela.getSelectionModel().getSelectedItem();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Posso apagar definitivamente o paciente "+pessoa.getNome());
            if(alert.showAndWait().get()==ButtonType.OK) {
                new PessoaDAL().apagar(pessoa);
                preencherTabela("");
            }
        }
    }


}
