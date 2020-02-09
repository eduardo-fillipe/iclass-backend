package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável por centralizar o acesso ao banco de dados e gerenciar o
 * banco de dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 * @param K Tipo da conexão com o banco de dados.
 */
public abstract class PersistenceModule <K>{

	private Logger LOGGER = Logger.getLogger(PersistenceModule.class.getName());

	public PersistenceModule() {
		try {
			setProperties(loadProperties());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível carregar as propriedades de banco de dados.", e);
		}
	}

	private Map<String, String> properties;

	/**
	 * Função responsável por carregar para memórias configurações de banco de
	 * dados. Deve ser reescrita para cada tipo de banco de dados utilizado.
	 * 
	 * @return Map contendo as propriedades, onde a Key é o nome da propriedade.
	 * @throws Exception Se não foi bem sucedido durante o carregamento
	 */
	protected abstract Map<String, String> loadProperties() throws Exception;

	public Map<String, String> getProperties() {
		return new HashMap<>(properties);
	}

	public void setProperties(Map<String, String> p) {
		properties = p;
	}

	public String getPropertie(String key) {
		return properties.get(key);
	}
	
	/**
	 * Retorna uma conexão com o banco de dados. 
	 * 
	 * @return Uma forma de conexão com o banco de dados
	 */
	public abstract K getConnection();
}
