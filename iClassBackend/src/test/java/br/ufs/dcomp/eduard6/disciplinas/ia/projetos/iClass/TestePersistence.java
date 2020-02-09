package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iClass;

import java.util.ArrayList;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.MongoDBConnectionManager;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.DisciplinaDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.ProfessorDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;

@SuppressWarnings("unused")
public class TestePersistence {
	public static void main(String[] args) {
		//System.out.println(MongoDBConnectionManager.getInstance().getConnection());
		//ProfessorDAO.getInstance().inserirProfessor(new ProfessorTO("Estombelo",
			//	"123",
				//new ArrayList<DisciplinaTO>(), (short)30));
		//ProfessorDAO.getInstance().removerProfessor("123");
		
		//DisciplinaDAO.getInstance().inserirDisciplina(new DisciplinaTO("Estrutura de Dados", "ED001", (short)06));
		
		System.out.println(ProfessorDAO.getInstance().getProfessor("123"));
		
	}
}
