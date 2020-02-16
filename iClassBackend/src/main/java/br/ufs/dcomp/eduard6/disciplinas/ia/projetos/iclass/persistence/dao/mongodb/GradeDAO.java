package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.MongoDBConnectionManager;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.GradePOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;

public class GradeDAO extends AbstractMongoDao<GradePOJO> {

	private static final GradeDAO INSTANCE = new GradeDAO(
			MongoDBConnectionManager.getInstance().getPropertie("iclass.mongodb.collectionname.grade"),
			GradePOJO.class);

	public GradeDAO(String collectionName, Class<GradePOJO> pojoClass) {
		super(collectionName, pojoClass);
	}
	
	public static GradeDAO getInstance() {
		return INSTANCE;
	}
	
	public void inserirGrade(GradeTO grade) {
		GradePOJO gradePojo = new GradePOJO(grade);
		getCollection().insertOne(gradePojo);
	}
	
	public List<GradeTO> getGrades() {
		throw new UnsupportedOperationException("Não implementado");
	}
	
	public void removerGradeById(ObjectId id) {
		getCollection().deleteOne(new Document("_id", id));
	}
}
