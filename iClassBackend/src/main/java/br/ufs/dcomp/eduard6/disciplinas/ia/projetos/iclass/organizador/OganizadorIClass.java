package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador;

import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;

/**
 * Interface Responsável pela interação com o frontEnd da aplicação.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public interface OganizadorIClass {
	public List<GradeTO> organize(ProblemaOrganizacaoTO problema);
}
