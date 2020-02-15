package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

/**
 * Não podem haver aulas paralelas, ou seja, turmas não podem ter os horários
 * iguais, mesmo que sejam ocupados por professores diferentes.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class AulasParalelas extends IClassRestricaoBase {
	
	private TurmaVariable turma1;
	private TurmaVariable turma2;
	
	
	public AulasParalelas(IClassCSP iClassCSP, TurmaVariable turma1, TurmaVariable turma2) {
		super(iClassCSP);
		this.turma1 = turma1;
		this.turma2 = turma2;
	}

	@Override
	public List<TurmaVariable> getScope() {
		return new ArrayList<>(Arrays.asList(turma1, turma2));
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		IClassDomainRepresentation valor1 = assignment.getValue(turma1);
		if (valor1 == null)
			return true;
		IClassDomainRepresentation valor2 = assignment.getValue(turma1);
		if (valor2 == null)
			return true;
		return !(valor1.getHorario().equals(valor2.getHorario()));
	}
}
