package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.MongoDBConnectionManager;
/**
 * Classe base para todos os DAOS do mongoDB.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 * @param <K>
 */
public abstract class AbstractMongoDao<K> {

	private String collectionName;
	private Class<K> pojoClass;

	private CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
			fromProviders(PojoCodecProvider.builder()
					.register("br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo").automatic(true)
					.build()));

	public AbstractMongoDao(String collectionName, Class<K> pojoClass) {
		super();
		this.collectionName = collectionName;
		this.pojoClass = pojoClass;
	}

	public MongoCollection<K> getCollection() {
		return getDatabase().getCollection(collectionName, pojoClass).withCodecRegistry(pojoCodecRegistry);
	}

	public MongoClient getConexao() {
		return MongoDBConnectionManager.getInstance().getConnection();
	}

	public MongoDatabase getDatabase() {
		return MongoDBConnectionManager.getInstance().getDatabase();
	}
}
