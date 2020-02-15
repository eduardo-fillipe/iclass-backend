package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

/**
 * O mesmo professor deve ministrar todas as aulas de uma turma.
 * 
 * Restrição opcional: O não uso dela abre espaço para que mais de um professor
 * ministre a mesma disciplina, como é o caso de Edilayne e Ricardo Pinheiro.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ProfessorResponsavelAulasTurma extends IClassRestricaoBase {

	public ProfessorResponsavelAulasTurma(IClassCSP iClassCSP) {
		super(iClassCSP);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TurmaVariable> getScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		// TODO Auto-generated method stub
		return false;
	}

}
