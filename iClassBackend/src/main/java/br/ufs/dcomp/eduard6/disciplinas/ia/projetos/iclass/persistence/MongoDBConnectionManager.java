package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Gerencia configurações e conexão com o MongoDB.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class MongoDBConnectionManager extends PersistenceModule<MongoClient> {

	private MongoClient client;
	private MongoDatabase database;

	private static MongoDBConnectionManager INSTANCE = new MongoDBConnectionManager();

	private MongoDBConnectionManager() {
		super();
		setLogLevel();
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
		while ((line = br.readLine()) != null) {
			if (line.startsWith("#") || line.isEmpty())
				continue;

			int separator = line.indexOf(':');

			String key = line.substring(0, separator);
			String value = line.substring(separator + 1);

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
	
	/**
	 * Configura o nível de log de acordo com o parâmetro iclass.mongodb.log.level
	 * dado no arquivo de configuração, seguindo o enum {@link Level}
	 * Se esse parâmetro não for informado ou for inválido, o nível é configurado para WARNING. 
	 */
	private void setLogLevel() {
		String level = getPropertie("iclass.mongodb.log.level");
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		try {
			Level l = Level.parse(level.toUpperCase());
			mongoLogger.setLevel(l);
		} catch (IllegalArgumentException | NullPointerException e) {
			Logger.getLogger(MongoDBConnectionManager.class.getName()).log(Level.WARNING,
					"Não foi possível carregar as configurações de Log. Verifique se o nível de log é válido: "
							+ e.getMessage(),
					e);
			mongoLogger.setLevel(Level.WARNING);
		}
	}
}
