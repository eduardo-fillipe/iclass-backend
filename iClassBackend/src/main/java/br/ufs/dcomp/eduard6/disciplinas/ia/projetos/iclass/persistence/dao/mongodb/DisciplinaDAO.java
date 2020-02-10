package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.MongoDBConnectionManager;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.DisciplinaPOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;

/**
 * Classe responsável pelas interações relacionadas a Disciplinas com o banco de dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class DisciplinaDAO extends AbstractMongoDao<DisciplinaPOJO>{
	
	private DisciplinaDAO(String collectionName, Class<DisciplinaPOJO> pojoClass) {
		super(collectionName, pojoClass);
	}

	private static DisciplinaDAO INSTANCE = new DisciplinaDAO(
			MongoDBConnectionManager.getInstance().getPropertie("iclass.mongodb.collectionname.disciplina"),
			DisciplinaPOJO.class);
	
	public static DisciplinaDAO getInstance() {
		return INSTANCE;
	}
	
	public void inserirDisciplina(DisciplinaTO disciplinaTO) {
		getCollection().insertOne(new DisciplinaPOJO(disciplinaTO));
	}
	
	public void removerDisciplina(String cod) {
		getCollection().deleteOne(new Document(new Document("codigo", cod)));
	}
	
	public DisciplinaTO getDisciplina(String cod) {
		DisciplinaPOJO result = getCollection().find(new Document("codigo", cod)).first();
		
		if (result != null) {
			DisciplinaTO d = new DisciplinaTO();
			d.setCargaHoraria(result.getCargaHoraria());
			d.setCodigo(result.getCodigo());
			d.setNome(result.getNome());
			return d;
		}
		return null;
	}
	
	public List<DisciplinaTO> getDisciplinas() {
		List<DisciplinaTO> disciplinas = new ArrayList<>();
		
		getCollection().find().iterator().forEachRemaining(result -> {
			DisciplinaTO d = new DisciplinaTO();
			d.setCargaHoraria(result.getCargaHoraria());
			d.setCodigo(result.getCodigo());
			d.setNome(result.getNome());
			d.setPrecisaLaboratorio(result.isPrecisaLaboratorio());
			disciplinas.add(d);
		});
		
		return disciplinas;
	}
}
