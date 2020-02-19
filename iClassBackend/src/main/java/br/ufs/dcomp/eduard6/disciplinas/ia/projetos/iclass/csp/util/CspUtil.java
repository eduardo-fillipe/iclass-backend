package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.util;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

/**
 * Classe responsável por fornecer algumas funções de utilidade.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class CspUtil {
	
	/**
	 * Verifica se dois assignments completos são iguais.
	 * 
	 * @param assignment
	 * @param assignment2
	 * @return {@code True} se são iguais, senão {@code False}.
	 */
	public static boolean areEquals(Assignment<TurmaVariable, IClassDomainRepresentation> assignment, Assignment<TurmaVariable, IClassDomainRepresentation> assignment2) {
		for (TurmaVariable v : assignment.getVariables()) {
			if (!assignment.getValue(v).equals(assignment2.getValue(v))) {
				return false;
			}
		}
		return true;
	}
}
