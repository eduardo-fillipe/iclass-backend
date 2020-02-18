package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;

/**
 * Nenhum professor pode lecionar disciplinas que não estejam na sua lista de
 * preferências.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class PreferenciaProfessor extends IClassRestricaoBase {

	private TurmaVariable turma;

	public PreferenciaProfessor(IClassCSP iClassCSP, TurmaVariable turma) {
		super(iClassCSP);
		this.turma = turma;
	}

	@Override
	public List<TurmaVariable> getScope() {
		return new ArrayList<TurmaVariable>(Arrays.asList(turma));
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		for (TurmaVariable turmaVariable : getScope()) { // Para cada turma no escopo
			IClassDomainRepresentation value = assignment.getValue(turmaVariable); // Pegue o valor dela
			if (value != null && value.getProfessor() != null) { // Se ela já possui um valor
				DisciplinaTO disciplinaDaTurma = turmaVariable.getTurmaAssociada().getDisciplina(); // Pegue a
																									// disciplina dessa
																									// turma
				if (!value.getProfessor().getPreferencias().contains(disciplinaDaTurma)) { // Se o professor não tem
																							// preferência sobre a
																							// disciplina dessa turma,
					return false; // Retorne falso
				}
			}
		}
		return true;
	}
}
