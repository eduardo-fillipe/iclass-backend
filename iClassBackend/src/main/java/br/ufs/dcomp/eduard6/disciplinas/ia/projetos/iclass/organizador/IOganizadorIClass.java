package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador;

import aima.core.search.csp.CspSolver;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;

/**
 * Interface Responsável pela interação com o frontEnd da aplicação.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public interface IOganizadorIClass {
	public GradeTO organize(ProblemaOrganizacaoTO problema,
			CspSolver<TurmaVariable, IClassDomainRepresentation> solver);
}
