package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.MongoDBConnectionManager;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.ResultadoOrganizacaoPOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ResultadoOrganizacaoTO;

public class GradeDAO extends AbstractMongoDao<ResultadoOrganizacaoPOJO> {

	private static final GradeDAO INSTANCE = new GradeDAO(
			MongoDBConnectionManager.getInstance().getPropertie("iclass.mongodb.collectionname.grade"),
			ResultadoOrganizacaoPOJO.class);

	public GradeDAO(String collectionName, Class<ResultadoOrganizacaoPOJO> pojoClass) {
		super(collectionName, pojoClass);
	}
	
	public static GradeDAO getInstance() {
		return INSTANCE;
	}
	
	public void inserirGrade(ResultadoOrganizacaoTO resultadoTO) {
		ResultadoOrganizacaoPOJO resultado = new ResultadoOrganizacaoPOJO(resultadoTO);
		getCollection().insertOne(resultado);
	}
	
	public List<GradeTO> getGrades() {
		throw new UnsupportedOperationException("Não implementado");
	}
	
	public void removerGradeById(ObjectId id) {
		getCollection().deleteOne(new Document("_id", id));
	}
}
