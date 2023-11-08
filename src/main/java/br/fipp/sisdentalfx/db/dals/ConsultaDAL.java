package br.fipp.sisdentalfx.db.dals;

import br.fipp.sisdentalfx.db.entidades.Consulta;
import br.fipp.sisdentalfx.db.entidades.Dentista;
import br.fipp.sisdentalfx.db.entidades.Material;
import br.fipp.sisdentalfx.db.entidades.Paciente;
import br.fipp.sisdentalfx.db.util.DB;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAL implements IDAL<Consulta>{

    @Override
    public boolean gravar(Consulta entidade) {
        String sql="INSERT INTO consulta (con_id, con_relato, con_data, con_horario, pac_id, den_id, con_efetivado) VALUES (default, '#1', '#2', #3, #4, #5, #6)";
        sql=sql.replace("#1",entidade.getRelato());
        sql=sql.replace("#2",entidade.getData().toString());
        sql=sql.replace("#3",""+entidade.getHorario());
        sql=sql.replace("#4",""+entidade.getPaciente().getId());
        sql=sql.replace("#5",""+entidade.getDentista().getId());
        sql=sql.replace("#6",""+entidade.isEfetivada());
        return DB.getCon().manipular(sql);
    }

    @Override
    public boolean alterar(Consulta entidade) {
        return false;
    }

    @Override
    public boolean apagar(Consulta entidade) {
        return false;
    }

    @Override
    public Consulta get(int id) {
        Consulta consulta=null;
        ResultSet rs=DB.getCon().consultar("select * from consulta where con_id="+id);
        try {
            if (rs.next())
            {   Dentista dentista=(Dentista) new PessoaDAL().get(rs.getInt("den_id"),new Dentista());
                Paciente paciente=(Paciente) new PessoaDAL().get(rs.getInt("pac_id"),new Paciente());

                consulta = new Consulta(rs.getInt("con_id"), rs.getDate("con_data").toLocalDate(), rs.getInt("con_horario"),
                                        dentista,paciente,rs.getString("con_relato"),rs.getBoolean("con_efetivado"));
            }
        }
        catch (Exception e){  }
        return consulta;
    }
    public List<Consulta> get(Dentista dentista, LocalDate data) {
        List <Consulta> consultas=new ArrayList();
        String sql="select * from consulta where den_id=#1 and con_data='#2'";
        sql=sql.replace("#1",""+dentista.getId());
        sql=sql.replace("#2",data.toString());
        ResultSet rs=DB.getCon().consultar(sql);
        try {
            while (rs.next()) {
                Paciente paciente=(Paciente) new PessoaDAL().get(rs.getInt("pac_id"),new Paciente());
                consultas.add(new Consulta(rs.getInt("con_id"), rs.getDate("con_data").toLocalDate(), rs.getInt("con_horario"),
                      dentista,paciente,rs.getString("con_relato"),rs.getBoolean("con_efetivado")));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return consultas;
    }
    @Override
    public List<Consulta> get(String filtro) {
        return null;
    }
}
