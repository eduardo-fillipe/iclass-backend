package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.listener;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;

public interface IClassCspListener<VAR extends Variable, VAL>{
	
	void iClassStateChanged(CSP<VAR, VAL> csp, Assignment<VAR, VAL> assignment, VAR variable, float pontuacao);
}
