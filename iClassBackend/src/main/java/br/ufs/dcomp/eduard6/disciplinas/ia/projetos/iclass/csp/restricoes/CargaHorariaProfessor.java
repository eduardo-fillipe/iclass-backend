package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

/**
 * A quantidades de aulas que um professor dá, deve menor ou igual que a carga
 * horária de trabalho semanal.
 * 
 * @author Eduardo Fillipe da Silva Reis
 */
public class CargaHorariaProfessor extends IClassRestricaoBase {

	public CargaHorariaProfessor(IClassCSP iClassCSP) {
		super(iClassCSP);
	}

	@Override
	public List<TurmaVariable> getScope() {
		return null;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		return true;
	}
}
