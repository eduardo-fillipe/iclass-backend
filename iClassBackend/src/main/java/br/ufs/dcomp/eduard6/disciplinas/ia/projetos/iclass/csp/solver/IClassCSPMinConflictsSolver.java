package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.solver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import aima.core.util.Tasks;
import aima.core.util.Util;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.PreferenciaHorariosProfessor;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.TurmaDevePossuirProfessor;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;

public class IClassCSPMinConflictsSolver<VAR extends Variable, VAL> extends IClassCspSolver<VAR, VAL> {
	private int maxSteps;

	/**
	 * Pontuação por professor que que leciona/ não leciona no horario de sua
	 * preferência.
	 */
	private static final float PONT_PROFESSOR_HORARIO = 1F;
	/**
	 * Pontuação por turma com/sem professor.
	 */
	private static final float PONT_PROFESSOR_TURMA = 5F;
	/**
	 * Pontução por qualquer outra restrição.
	 */
	private static final float PONT_RESTRICAO = 10F;

	public IClassCSPMinConflictsSolver(int maxSteps) {
		this.maxSteps = maxSteps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<Assignment<VAR, VAL>> solve(CSP<VAR, VAL> csp) {

		Assignment<VAR, VAL> current = generateRandomAssignment(csp);
		fireStateChanged(csp, current, null);
		iClassfireStateChanged(csp, current, null, 0);
		for (int i = 0; i < maxSteps && !Tasks.currIsCancelled(); i++) {
			SolutionHelper solutionHelper = isSolution((Assignment<TurmaVariable, IClassDomainRepresentation>) current,
					(CSP<TurmaVariable, IClassDomainRepresentation>) csp);
			if (solutionHelper.isSolution()) {
				return Optional.of(current);
			} else {
				Set<VAR> vars = getConflictedVariables(current, csp);
				VAR var = Util.selectRandomlyFromSet(vars);
				VAL value = getMinConflictValueFor(var, current, csp);
				current.add(var, value);
				fireStateChanged(csp, current, var);
				if (solutionHelper.isComplete()) {
					iClassfireStateChanged(csp, current, var, solutionHelper.getPontuacao());
				}
			}
		}
		return Optional.empty();
	}

	protected SolutionHelper isSolution(Assignment<TurmaVariable, IClassDomainRepresentation> assignment,
			CSP<TurmaVariable, IClassDomainRepresentation> csp) {
		SolutionHelper solutionHelper = new SolutionHelper();
		solutionHelper.setComplete(assignment.isComplete(csp.getVariables()));

		if (!solutionHelper.isComplete()) {
			solutionHelper.setSolution(false);
		} else {
			solutionHelper.setSolution(true);
		}

		float pont = 0;
		for (TurmaVariable turma : csp.getVariables()) {
			IClassDomainRepresentation value = assignment.getValue(turma);

			if (value.getProfessor() != null) {
				pont += PONT_PROFESSOR_TURMA;
			} else {
				pont -= PONT_PROFESSOR_TURMA;
				solutionHelper.setSolution(false);
			}
		}

		for (TurmaVariable turma : csp.getVariables()) {
			IClassDomainRepresentation valueTuma = assignment.getValue(turma);
			if (valueTuma != null && valueTuma.getProfessor() != null) {
				if (isProfessorEmHorarioPreferencial(valueTuma.getHorario(), valueTuma.getProfessor())) {
					pont += PONT_PROFESSOR_HORARIO;
				} else {
					pont -= PONT_PROFESSOR_HORARIO;
					solutionHelper.setSolution(false);
				}
			}
		}

		for (Constraint<TurmaVariable, IClassDomainRepresentation> restricao : csp.getConstraints()) {
			if (!(restricao instanceof TurmaDevePossuirProfessor)
					&& !(restricao instanceof PreferenciaHorariosProfessor)) {
				if (restricao.isSatisfiedWith(assignment)) {
					pont += PONT_RESTRICAO;
				} else {
					pont -= PONT_RESTRICAO;
					solutionHelper.setSolution(false);
				}
			}
		}
		solutionHelper.setPontuacao(pont);
		return solutionHelper;
	}

	protected Assignment<VAR, VAL> generateRandomAssignment(CSP<VAR, VAL> csp) {
		Assignment<VAR, VAL> result = new Assignment<>();
		for (VAR var : csp.getVariables()) {
			VAL randomValue = Util.selectRandomlyFromList(csp.getDomain(var).asList());
			result.add(var, randomValue);
		}
		return result;
	}

	protected Set<VAR> getConflictedVariables(Assignment<VAR, VAL> assignment, CSP<VAR, VAL> csp) {
		Set<VAR> result = new LinkedHashSet<>();
		csp.getConstraints().stream().filter(constraint -> !constraint.isSatisfiedWith(assignment))
				.map(Constraint::getScope).forEach(result::addAll);
		return result;
	}

	protected VAL getMinConflictValueFor(VAR var, Assignment<VAR, VAL> assignment, CSP<VAR, VAL> csp) {
		List<Constraint<VAR, VAL>> constraints = csp.getConstraints(var);
		Assignment<VAR, VAL> testAssignment = assignment.clone();
		int minConflict = Integer.MAX_VALUE;
		List<VAL> resultCandidates = new ArrayList<>();
		for (VAL value : csp.getDomain(var)) {
			testAssignment.add(var, value);
			int currConflict = countConflicts(testAssignment, constraints);
			if (currConflict <= minConflict) {
				if (currConflict < minConflict) {
					resultCandidates.clear();
					minConflict = currConflict;
				}
				resultCandidates.add(value);
			}
		}
		return (!resultCandidates.isEmpty()) ? Util.selectRandomlyFromList(resultCandidates) : null;
	}

	protected int countConflicts(Assignment<VAR, VAL> assignment, List<Constraint<VAR, VAL>> constraints) {
		return (int) constraints.stream().filter(constraint -> !constraint.isSatisfiedWith(assignment)).count();
	}

	protected int getMaxSteps() {
		return this.maxSteps;
	}

	/**
	 * Retorna se um professor possui um determinado horario em sua lista de
	 * preferências.
	 * 
	 * @param horario
	 * @param professor
	 * @return True se um professor possui um determinado horario em sua lista de
	 *         preferências.
	 */
	private boolean isProfessorEmHorarioPreferencial(HorarioTO horario, ProfessorTO professor) {
		return (professor.getPreferenciaHorarios() != null || professor.getPreferenciaHorarios().isEmpty()) ? true
				: professor.getPreferenciaHorarios().contains(horario);
	}

	private class SolutionHelper {
		private boolean solution;
		private float pontuacao;
		private boolean complete;

		public boolean isSolution() {
			return solution;
		}

		public void setSolution(boolean solution) {
			this.solution = solution;
		}

		public float getPontuacao() {
			return pontuacao;
		}

		public void setPontuacao(float pontuacao) {
			this.pontuacao = pontuacao;
		}

		public boolean isComplete() {
			return complete;
		}

		public void setComplete(boolean complete) {
			this.complete = complete;
		}
	}
}
