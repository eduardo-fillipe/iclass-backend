package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;

public class CargaHorariaProfessor implements Constraint<Variable, List<IClassDomainRepresentation>> {

	@Override
	public List<Variable> getScope() {
		return null;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<Variable, List<IClassDomainRepresentation>> assignment) {
		return false;
	}

}
