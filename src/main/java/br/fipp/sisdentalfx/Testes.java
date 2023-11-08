package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.ConsultaDAL;
import br.fipp.sisdentalfx.db.dals.MaterialDAL;
import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.*;
import br.fipp.sisdentalfx.db.util.DB;

import java.time.LocalDate;
import java.util.List;

public class Testes {
    public static void main(String[] args) {
        /* conexão com o banco*/
        if(DB.conectar()) {
//            MaterialDAL dal = new MaterialDAL();
//            dal.gravar(new Material("mascara",5.50));
//            List<Material> lista = dal.get("");
//            for (Material material : lista)
//                System.out.println(material);
           //PessoaDAL dal=new PessoaDAL();
           //if(!dal.gravar(new Dentista("Teodoro",321,"18333999","teo@email.com")))
           //    DB.getCon().getMensagemErro();
           //if(!dal.gravar(new Paciente("Gustavo Lima","123254","","","","","","","","","")))
           //    DB.getCon().getMensagemErro();]
            //if(!dal.gravar(new Usuario("Syrley",1,"123")))
            //    DB.getCon().getMensagemErro();

            // System.out.println(dal.get(2,new Paciente()).getNome());
//            List<Pessoa> lista = dal.get("",new Paciente());
//            for (Pessoa p : lista)
//                System.out.println(p.getNome());
            //Consulta consulta=new ConsultaDAL().get(1);
            //System.out.println(consulta.getDentista().getNome());
            new ConsultaDAL().gravar(new Consulta(LocalDate.now(),
                    9,
                    new Dentista(1,"",0,"",""),
                    (Paciente)new PessoaDAL().get(1,new Paciente()),""));
        }
        else
            System.out.println(DB.getCon().getMensagemErro());
    }
}
