package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

/**
 * Toda Turma deve ter um professor atribuído.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class DisciplinaDevePossuirProfessor extends IClassRestricaoBase {

	private TurmaVariable turma;
	private ArrayList<TurmaVariable> scope;
	
	public DisciplinaDevePossuirProfessor(IClassCSP iClassCSP, TurmaVariable turma) {
		super(iClassCSP);
		this.turma = turma;
		scope = new ArrayList<>(Arrays.asList(this.turma));
	}

	@Override
	public List<TurmaVariable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		IClassDomainRepresentation value = assignment.getValue(this.turma);
		if (value != null) 
			return value.getProfessor() != null;
		return true;
	}
}
