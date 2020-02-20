package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.solver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.CspSolver;
import aima.core.search.csp.Variable;
import aima.core.util.Tasks;
import aima.core.util.Util;

public class IClassCSPMinConflictsSolver<VAR extends Variable, VAL> extends CspSolver<VAR, VAL> {
	private int maxSteps;
	
	public IClassCSPMinConflictsSolver(int maxSteps) {
	}

	@Override
	public Optional<Assignment<VAR, VAL>> solve(
			CSP<VAR, VAL> csp) {
		
		Assignment<VAR, VAL> current = generateRandomAssignment(csp);
		fireStateChanged(csp, current, null);
		for (int i = 0; i < maxSteps && !Tasks.currIsCancelled(); i++) {
			if (current.isSolution(csp)) {
				return Optional.of(current);
			} else {
				Set<VAR> vars = getConflictedVariables(current, csp);
				VAR var = Util.selectRandomlyFromSet(vars);
				VAL value = getMinConflictValueFor(var, current, csp);
				current.add(var, value);
				fireStateChanged(csp, current, var);
			}
		}
		return Optional.empty();
	}
	
	private Assignment<VAR, VAL> generateRandomAssignment(CSP<VAR, VAL> csp) {
		Assignment<VAR, VAL> result = new Assignment<>();
		for (VAR var : csp.getVariables()) {
			VAL randomValue = Util.selectRandomlyFromList(csp.getDomain(var).asList());
			result.add(var, randomValue);
		}
		return result;
	}

	private Set<VAR> getConflictedVariables(Assignment<VAR, VAL> assignment, CSP<VAR, VAL> csp) {
		Set<VAR> result = new LinkedHashSet<>();
		csp.getConstraints().stream().filter(constraint -> !constraint.isSatisfiedWith(assignment)).
				map(Constraint::getScope).forEach(result::addAll);
		return result;
	}

	private VAL getMinConflictValueFor(VAR var, Assignment<VAR, VAL> assignment, CSP<VAR, VAL> csp) {
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

	private int countConflicts(Assignment<VAR, VAL> assignment, List<Constraint<VAR, VAL>> constraints) {
		return (int) constraints.stream().filter(constraint -> !constraint.isSatisfiedWith(assignment)).count();
	}
	
	public int getMaxSteps() {
		return this.maxSteps;
	}
}
