package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.modulo.persistence;

import java.util.Map;

/**
 * Classe responsável por centralizar o acesso ao banco de dados e gerenciar o banco de dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public abstract class PersistenceModule {
	private Map<String, String> properties;
	
	/**
	 * Função responsável por carregar para memórias configurações de banco de dados.
	 * Deve ser reescrita para cada tipo de banco de dados utilizado.
	 * @return Map contendo as propriedades, onde a Key é o nome da propriedade.
	 * @throws Exception Se não foi bem sucedido durante o carregamento
	 */
	protected abstract Map<String, String> loadProperties() throws Exception;
}
