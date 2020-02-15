package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;

/**
 * Se uma turma tem horário num dia X, todos os horários são consecutivos. 
 * Exemplo VÁLIDO para uma turma de 4 créditos que tem aula apenas no dia X: XT12 e XT34
 * Exemplo INVÁLIDO para uma turma de 4 créditos que tem aula apenas no dia X: XT12 e XT56
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ApenasAulasConsecutivasNoDia extends IClassRestricaoBase{
	private TurmaTO turma;
	public ApenasAulasConsecutivasNoDia(IClassCSP iClassCSP, TurmaTO turma) {
		super(iClassCSP);
		this.turma = turma;
	}

	@Override
	public List<TurmaVariable> getScope() {
		return getiClassCSP().getFragmentosTurma(turma);
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		List<HorarioTO> horarios = new ArrayList<>();
		for (TurmaVariable turmaVariable : getScope()) {
			IClassDomainRepresentation value = assignment.getValue(turmaVariable);
			if (value != null) {
				horarios.add(value.getHorario());
			}
		}
		return HorarioTO.isConsecutivos(horarios);
	}
}
