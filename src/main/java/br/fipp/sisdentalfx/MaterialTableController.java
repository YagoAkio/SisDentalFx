package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.MaterialDAL;
import br.fipp.sisdentalfx.db.entidades.Material;
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

public class MaterialTableController implements Initializable {

    public TextField tfPesquisa;
    public Button btNovo;
    public TableView<Material> tabela;
    public TableColumn <Material,Integer> colID3;
    public TableColumn <Material,String> colDesc;
    public TableColumn <Material,Double> colPreco;

    public Button btClose;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->{((Button)e.getSource()).getScene().getWindow().hide();});
        preencherTabela("");
    }

    private void preencherTabela(String filtro) {
        List<Material> materials = new MaterialDAL().get(filtro);
        colID3.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tabela.setItems(FXCollections.observableArrayList(materials));
    }

    public void onFiltrar(KeyEvent keyEvent) {
        String filtro=tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(mat_id) like '%"+filtro+"%'");
    }

    public void OnNovoMaterial(ActionEvent actionEvent) {
        UIControl.abreModal("material-view.fxml");
        preencherTabela("");
    }

    public void onAlterar(ActionEvent actionEvent) {
        Material materalSelecionado = tabela.getSelectionModel().getSelectedItem();
        if (materalSelecionado != null) {
            UIControl.abreModal("material-view.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione um material para alterar seu cadastro.");
            alert.showAndWait();
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        if(tabela.getSelectionModel().getSelectedItem()!=null)
        {
            Material material = tabela.getSelectionModel().getSelectedItem();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Posso apagar definitivamente o material "+material.getId());
            if(alert.showAndWait().get()==ButtonType.OK) {
                new MaterialDAL().apagar(material);
                preencherTabela("");
            }
        }
    }
}
