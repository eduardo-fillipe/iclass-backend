package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables;

import aima.core.search.csp.Variable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;

public class TurmaVariable extends Variable {

	public TurmaVariable(TurmaTO turma) {
		super(turma.getCodTurma());
	}
}
