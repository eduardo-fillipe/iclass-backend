package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.solver;

import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.Variable;

public class IClassCspSolver<VAR extends Variable, VAL> extends CspSolver<VAR, VAL> {

	@Override
	public Optional<Assignment<VAR, VAL>> solve(CSP<VAR, VAL> csp) {
		return null;
	}

}
