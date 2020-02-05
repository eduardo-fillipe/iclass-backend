package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;

public class CargaHorariaProfessor implements Constraint<Variable, HorarioTO> {

	@Override
	public List<Variable> getScope() {
		return null;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<Variable, HorarioTO> assignment) {
		// TODO Auto-generated method stub
		return false;
	}

}
