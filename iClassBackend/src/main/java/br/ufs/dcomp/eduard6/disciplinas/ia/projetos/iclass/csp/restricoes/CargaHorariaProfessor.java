package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.HashMap;
import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;

/**
 * A quantidades de aulas que um professor dá, deve menor ou igual que a carga
 * horária de trabalho semanal.
 * 
 * @author Eduardo Fillipe da Silva Reis
 */
public class CargaHorariaProfessor extends IClassRestricaoBase {

	public CargaHorariaProfessor(IClassCSP iClassCSP) {
		super(iClassCSP);
	}

	@Override
	public List<TurmaVariable> getScope() {
		return getiClassCSP().getVariables();
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		HashMap<ProfessorTO, Integer> professorCargaHoraria = new HashMap<>();
		for (TurmaVariable turmaVariable : getScope()) {
			IClassDomainRepresentation value = assignment.getValue(turmaVariable);
			if (value != null) {
				Integer profCargaSum = professorCargaHoraria.get(value.getProfessor());

				if (profCargaSum == null) {
					professorCargaHoraria.put(value.getProfessor(),
							(int) value.getHorario().getHorarioSequencia().getValor());
				} else {
					professorCargaHoraria.put(value.getProfessor(),
							profCargaSum + (int) value.getHorario().getHorarioSequencia().getValor());
				}

				if (professorCargaHoraria.get(value.getProfessor()) > value.getProfessor().getCargaHorariaSemanal()) {
					return false;
				}
			}
		}
		return true;
	}
}
