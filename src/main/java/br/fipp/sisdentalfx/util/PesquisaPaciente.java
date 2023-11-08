package br.fipp.sisdentalfx.util;

import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.Paciente;
import br.fipp.sisdentalfx.db.entidades.Pessoa;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;


public class PesquisaPaciente extends BorderPane {
    private TableView <Pessoa> tableView;
    private Button btConfirmar, btCancelar;
    private Paciente paciente=null;

    public PesquisaPaciente() {
        super();
        tableView = new TableView();
        TableColumn <Pessoa,String> colNome=new TableColumn<>("Nome");
        TableColumn <Pessoa,String> colFone=new TableColumn<>("Telefone");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
        tableView.getColumns().add(colNome);
        tableView.getColumns().add(colFone);
        this.setCenter(tableView);
        // botoes
        btConfirmar=new Button("Confirmar");
        btCancelar=new Button("Cancelar");
        HBox hBox=new HBox(100);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(btConfirmar);
        hBox.getChildren().add(btCancelar);
        btConfirmar.setOnAction(e->{
            paciente = (Paciente) tableView.getSelectionModel().getSelectedItem();
            getScene().getWindow().hide();
        });
        btCancelar.setOnAction(e->{getScene().getWindow().hide();});
        this.setBottom(hBox);
        // carregar a tabela
        List<Pessoa> pessoas = new PessoaDAL().get("",new Paciente());
        tableView.setItems(FXCollections.observableArrayList(pessoas));
    }

    public Paciente getPaciente() {
        return paciente;
    }
}
