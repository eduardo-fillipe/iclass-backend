package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador;

import aima.core.search.csp.CspSolver;
import aima.core.search.csp.FlexibleBacktrackingSolver;
import aima.core.search.csp.MinConflictsSolver;
import aima.core.search.csp.TreeCspSolver;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;

/**
 * Interface Responsável pela interação com o frontEnd da aplicação.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public abstract class IOganizadorIClass {
	private ProblemaOrganizacaoTO problema;
	private CspSolverEnum solverAlgorithm;
	CspSolver<TurmaVariable, IClassDomainRepresentation> solver;

	public IOganizadorIClass(ProblemaOrganizacaoTO problema, CspSolverEnum solverAlgorithm) {
		this.problema = problema;
		this.solverAlgorithm = solverAlgorithm;
		solver = getSolverFromEnum();
	}

	public abstract GradeTO organize();

	/**
	 * Enum responsável por definir qual será o algoritmo utilizado pelo
	 * organizador.
	 * 
	 * @author Eduardo Fillipe da Silva Reis
	 *
	 */
	public enum CspSolverEnum {

		MIN_CONFLICTS("Min Conflicts Solver"), BACKTRACKING("Backtracking Solver"),
		BACKTRACKING_WITH_HEURISTCS("Backtracking with Heuristics Solver"), TREE("Tree Solver");

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
				return new MinConflictsSolver<TurmaVariable, IClassDomainRepresentation>(10000);
			case BACKTRACKING:
				return new FlexibleBacktrackingSolver<TurmaVariable, IClassDomainRepresentation>();
			case BACKTRACKING_WITH_HEURISTCS:
				return new FlexibleBacktrackingSolver<TurmaVariable, IClassDomainRepresentation>().setAll();
			case TREE:
				return new TreeCspSolver<TurmaVariable, IClassDomainRepresentation>();
			default:
				return new FlexibleBacktrackingSolver<TurmaVariable, IClassDomainRepresentation>().setAll();
		}
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
