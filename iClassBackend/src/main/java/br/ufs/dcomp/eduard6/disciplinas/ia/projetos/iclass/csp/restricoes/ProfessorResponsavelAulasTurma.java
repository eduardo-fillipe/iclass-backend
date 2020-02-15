package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;

/**
 * O mesmo professor deve ministrar todas as aulas de uma turma.
 * 
 * Restrição opcional: O não uso dela abre espaço para que mais de um professor
 * ministre a mesma disciplina, como é o caso de Edilayne e Ricardo Pinheiro.
 * 
 * Deve ser alterada a especificação da classe {@link TurmaTO} para comportar um
 * array de professores, em vez de um só.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ProfessorResponsavelAulasTurma extends IClassRestricaoBase {

	private List<TurmaVariable> turmas;

	public ProfessorResponsavelAulasTurma(IClassCSP iClassCSP, TurmaTO turma) {
		super(iClassCSP);
		this.turmas = getiClassCSP().getFragmentosTurma(turma);
	}

	@Override
	public List<TurmaVariable> getScope() {
		return this.turmas;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		int primeiroNaoNull;
		for (primeiroNaoNull = 0; primeiroNaoNull < getScope().size(); primeiroNaoNull++){
			IClassDomainRepresentation temp = assignment.getValue(getScope().get(primeiroNaoNull));				
			if (temp != null)
				break;
		}
		
		if (primeiroNaoNull >= getScope().size() - 1)
			return true;
		
		IClassDomainRepresentation temp = assignment.getValue(getScope().get(primeiroNaoNull));				
		for (int i = primeiroNaoNull; i < getScope().size() - 1; i++) {
			IClassDomainRepresentation v = assignment.getValue(getScope().get(i));
			if (v != null) {
				if (v.getProfessor().equals(temp.getProfessor())) {
					return false;
				}
			} 
		}
		return true;
	}
}
