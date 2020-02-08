package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
/**
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class MongoDBConnectionManager extends PersistenceModule<MongoClient>{
	
	private MongoClient client;
	private MongoDatabase database;
	
	private static MongoDBConnectionManager INSTANCE = new MongoDBConnectionManager();
	
	private MongoDBConnectionManager() {
		super();
		client = new MongoClient(new MongoClientURI(getPropertie("iclass.mongodb.serveruri")));
		database = client.getDatabase(getPropertie("iclass.mongodb.databasename"));
	}
	
	public static MongoDBConnectionManager getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected Map<String, String> loadProperties() throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		
		File file = new File("mongoDataBase.properties");
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		String line;
		while((line = br.readLine()) != null) {
			if (line.startsWith("#") || line.isEmpty())
				continue;
			
			int separator = line.indexOf(':');
			
			String key = line.substring(0, separator);
			String value = line.substring(separator+1);
			
			properties.put(key.trim().toLowerCase(), value.trim().toLowerCase());
		}
		
		br.close();
		return properties;
	}

	@Override
	public MongoClient getConnection() {
		return client;
	}
	
	public MongoDatabase getDatabase() {
		return database;
	}
}
