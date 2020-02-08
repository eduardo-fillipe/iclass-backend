package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;
/**
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 * @param <K>
 */
public abstract class AbstractPOJO<K> {
	public abstract AbstractPOJO<K> fromTO(K to);
	public abstract K toTO();
}
