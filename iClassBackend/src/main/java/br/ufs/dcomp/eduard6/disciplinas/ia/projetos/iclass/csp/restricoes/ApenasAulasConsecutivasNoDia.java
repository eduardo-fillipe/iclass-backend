package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

public class ApenasAulasConsecutivasNoDia extends IClassRestricaoBase{

	public ApenasAulasConsecutivasNoDia(IClassCSP iClassCSP) {
		super(iClassCSP);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TurmaVariable> getScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		// TODO Auto-generated method stub
		return false;
	}

}
