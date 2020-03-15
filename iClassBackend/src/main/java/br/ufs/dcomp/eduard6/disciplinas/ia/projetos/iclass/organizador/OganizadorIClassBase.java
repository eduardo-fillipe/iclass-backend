package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador;

import aima.core.search.csp.CspSolver;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.MinConflictsSolver;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.solver.IClassCSPMinConflictsSolver;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ResultadoOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;

/**
 * Interface Responsável pela interação com o frontEnd da aplicação.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public abstract class OganizadorIClassBase {
	private ProblemaOrganizacaoTO problema;
	private CspSolverEnum solverAlgorithm;
	CspSolver<TurmaVariable, IClassDomainRepresentation> solver;
	private int numIteracoesMinConflicts;
			
	public OganizadorIClassBase(ProblemaOrganizacaoTO problema, CspSolverEnum solverAlgorithm) {
		this.problema = problema;
		this.solverAlgorithm = solverAlgorithm;
		this.numIteracoesMinConflicts = getNumIteracoesMinConflicts(problema);
		solver = getSolverFromEnum();
	}

	/**
	 * Método que tenta resolver o Problema de Organização dessa Classe.
	 * 
	 * @return Resultado da Organização devidamente preenchido.
	 * @see ResultadoOrganizacaoTO
	 */
	public abstract ResultadoOrganizacaoTO organize();

	/**
	 * Enum responsável por definir qual será o algoritmo utilizado pelo
	 * organizador.
	 * 
	 * @author Eduardo Fillipe da Silva Reis
	 *
	 */
	public enum CspSolverEnum {

		MIN_CONFLICTS("Min Conflicts Solver"), BACKTRACKING("Backtracking Solver"),
		BACKTRACKING_WITH_HEURISTCS("Backtracking with Heuristics Solver"), ICLASS_CSP_SOLVER("iClass Csp Solver");

		String solverClassName;

		private CspSolverEnum(String className) {
			this.solverClassName = className;
		}

		@Override
		public String toString() {
			return this.solverClassName;
		}
	}

	private CspSolver<TurmaVariable, IClassDomainRepresentation> getSolverFromEnum() {
		switch (solverAlgorithm) {
			case MIN_CONFLICTS:
				return new MinConflictsSolver<>(numIteracoesMinConflicts);
			case BACKTRACKING:
				return new FlexibleBacktrackingSolver<>();
			case BACKTRACKING_WITH_HEURISTCS:
				return new FlexibleBacktrackingSolver<TurmaVariable, IClassDomainRepresentation>().setAll();
			case ICLASS_CSP_SOLVER:
				return new IClassCSPMinConflictsSolver<TurmaVariable, IClassDomainRepresentation>(numIteracoesMinConflicts);
			default:
				return new FlexibleBacktrackingSolver<TurmaVariable, IClassDomainRepresentation>().setAll();
		}
	}
	
	private int getNumIteracoesMinConflicts(ProblemaOrganizacaoTO problema) {
		int numeroIteracoes = 0;
		int qtVAR = 0;
		
		for (TurmaTO t : problema.getTurmasObrigatorias()) {
			qtVAR += t.getDisciplina().getCargaHoraria()/2;
		}
		
		for (TurmaTO t : problema.getTurmasPredefinidas()) {
			qtVAR += t.getDisciplina().getCargaHoraria()/2;
		}
		
		int qtVAL = problema.getProfessores().size() * ((problema.getCargaHorariaGrade() * 5)/2); 
		
		numeroIteracoes = qtVAR * qtVAL;
		
		return numeroIteracoes;
	}

	public ProblemaOrganizacaoTO getProblema() {
		return problema;
	}

	public void setProblema(ProblemaOrganizacaoTO problema) {
		this.problema = problema;
	}

	public CspSolverEnum getSolverAlgorithm() {
		return solverAlgorithm;
	}

	public void setSolverAlgorithm(CspSolverEnum solverAlgorithm) {
		this.solverAlgorithm = solverAlgorithm;
	}

	public void setSolver(CspSolver<TurmaVariable, IClassDomainRepresentation> solver) {
		this.solver = solver;
	}

	public CspSolver<TurmaVariable, IClassDomainRepresentation> getSolver() {
		return solver;
	}
}
