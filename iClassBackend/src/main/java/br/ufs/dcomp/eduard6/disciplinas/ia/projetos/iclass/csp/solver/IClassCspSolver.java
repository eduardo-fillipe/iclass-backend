package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.solver;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.Variable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.listener.IClassCspListener;

public abstract class IClassCspSolver<VAR extends Variable, VAL> extends CspSolver<VAR, VAL> {

    private List<IClassCspListener<VAR, VAL>> iClasslisteners = new ArrayList<>();

	
	protected void iClassfireStateChanged(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR variable, float pontuacao) {
		iClasslisteners.forEach(l -> {
			l.iClassStateChanged(csp, assignment, variable, pontuacao);
		});
	}
	
    public void addiClassCspListener(IClassCspListener<VAR, VAL> listener) {
    	iClasslisteners.add(listener);
    }

    public void removeiClassCspListener(IClassCspListener<VAR, VAL> listener) {
    	iClasslisteners.remove(listener);
    }
}
