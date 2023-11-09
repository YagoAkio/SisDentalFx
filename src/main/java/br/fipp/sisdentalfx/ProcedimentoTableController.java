package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.ProcedimentoDAL;
import br.fipp.sisdentalfx.db.entidades.Procedimento;
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

public class ProcedimentoTableController implements Initializable {

    public TextField tfPesquisa;
    public Button btNovo;
    public TableView<Procedimento> tabela;
    public TableColumn<Procedimento,Integer> colID4;
    public TableColumn <Procedimento,String> colDesc;
    public TableColumn <Procedimento,Integer> colTempo;
    public TableColumn <Procedimento,Double> colValor;

    public Button btClose;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->{((Button)e.getSource()).getScene().getWindow().hide();});
        preencherTabela("");
    }

    private void preencherTabela(String filtro) {
        List<Procedimento> procedimentos = new ProcedimentoDAL().get(filtro);
        colID4.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colTempo.setCellValueFactory(new  PropertyValueFactory<>("tempo"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tabela.setItems(FXCollections.observableArrayList(procedimentos));
    }

    public void onFiltrar(KeyEvent keyEvent) {
        String filtro=tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(pro_id) like '%"+filtro+"%'");
    }

    public void OnNovoProcedimento(ActionEvent actionEvent) {
        UIControl.abreModal("procedimento-view.fxml");
        preencherTabela("");
    }

    public void onAlterar(ActionEvent actionEvent) {
        Procedimento procedimentoSelecionado = tabela.getSelectionModel().getSelectedItem();
        if (procedimentoSelecionado != null) {
            UIControl.abreModal("procedimento-view.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione um procedimento para alterar seu cadastro.");
            alert.showAndWait();
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        if(tabela.getSelectionModel().getSelectedItem()!=null)
        {
            Procedimento procedimento = tabela.getSelectionModel().getSelectedItem();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Posso apagar definitivamente o procedimento "+procedimento.getId());
            if(alert.showAndWait().get()==ButtonType.OK) {
                new ProcedimentoDAL().apagar(procedimento);
                preencherTabela("");
            }
        }
    }
}