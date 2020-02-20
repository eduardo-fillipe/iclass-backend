package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass;

import java.util.ArrayList;
import java.util.Arrays;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.MongoDBConnectionManager;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.DisciplinaDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.ProfessorDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;

@SuppressWarnings("unused")
public class TestePersistence {
    public static void main(String[] args) {
        System.out.println(
                MongoDBConnectionManager.getInstance().getConnection());
//        ProfessorDAO.getInstance().inserirProfessor(new ProfessorTO("Estombelo",
//                "1", new ArrayList<DisciplinaTO>(), (short) 8,
//                new ArrayList<HorarioTO>(
//                        Arrays.asList(HorarioTO.fromCodigo("2T56"),
//                                HorarioTO.fromCodigo("2T34")))));

        // DisciplinaDAO.getInstance().inserirDisciplina(new
        // DisciplinaTO("Laboratório de Redes", "LR001", (short)06, true));
        // DisciplinaDAO.getInstance().inserirDisciplina(new
        // DisciplinaTO("Compiladores", "CP001", (short)04, true));
        System.out.println(
                ProfessorDAO.getInstance().getProfessorCompleto("1"));

    }
}
