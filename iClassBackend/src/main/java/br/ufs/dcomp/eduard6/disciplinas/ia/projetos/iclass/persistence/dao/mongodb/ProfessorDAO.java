package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.MongoDBConnectionManager;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.ProfessorPOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;
/**
 * s
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ProfessorDAO extends AbstractMongoDao<ProfessorPOJO> {

	private static ProfessorDAO INSTANCE = new ProfessorDAO();
	
	private ProfessorDAO() {
		super(MongoDBConnectionManager.getInstance().getPropertie("iclass.mongodb.collectionname.professor"),
				ProfessorPOJO.class);
	}

	public static ProfessorDAO getInstance() {
		return INSTANCE;
	}

	public void alterarProfessor(ProfessorTO professor) {

	}

	public ProfessorTO getProfessor(String matricula) {
		return null;
	}

	public List<ProfessorTO> getProfessores() {
		List<ProfessorTO> p = new ArrayList<ProfessorTO>();
		ArrayList<DisciplinaTO> ds = new ArrayList<DisciplinaTO>();
		getCollection().find().iterator().forEachRemaining(o -> {
			
			for (String pr : o.getPreferencias()) {
				DisciplinaTO d = new DisciplinaTO();
				d.setCodigo(pr);
				ds.add(d);
			}
			
			p.add(new ProfessorTO(o.getNome(),
					o.getMatricula(),
					ds,
					o.getCargaHorariaSemanal()));
		});
		
		return p;
	}

	public void inserirProfessor(ProfessorTO professor) {
		getCollection().insertOne(ProfessorPOJO.fromTO(professor));
	}

	public void removerProfessor(String matricula) {
		getCollection().deleteOne(new Document("matricula", matricula));
	}
}
