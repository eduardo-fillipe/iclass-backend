package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.listener;

import java.util.Comparator;
import java.util.PriorityQueue;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.CspListener;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.TurmaDevePossuirProfessor;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

/**
 * Classe responsável por manter uma fila de prioridades do N melhores
 * candidatos, caso o CSP falhe.
 * 
 * Utiliza uma função objetivo para avaliar e decidir se o csp será mantido ou
 * não. Essa função objetivo é CARA, ela deve verificar todas as turmas de
 * Assignment Completo para definir uma pontuação para o mesmo. Isso pode ser
 * apriomorado, se durante à busca sejam passadas para a função objetivo
 * informações que a própria busca já verificou, como quais restricoes o CSP viola.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class CandidatosSolucaoListener implements CspListener<TurmaVariable, IClassDomainRepresentation> {

	private PriorityQueue<PontuacaoAssignment> candidatos;
	private int quantCandidatos;
	private static final float PONT_PROFESSOR_TURMA = 1F;
	private static final float PONT_RESTRICAO = 5F;

	public CandidatosSolucaoListener(int quantCandidatos) {
		super();
		this.candidatos = new PriorityQueue<>(
				new PontuacaoAssignmentComparator());
		this.quantCandidatos = quantCandidatos;
	}

	@Override
	public void stateChanged(CSP<TurmaVariable, IClassDomainRepresentation> csp,
			Assignment<TurmaVariable, IClassDomainRepresentation> assignment, TurmaVariable variable) {
		if (assignment != null && assignment.isComplete(csp.getVariables())) {
			float pontuacao = getObjectiveFunctionValue(csp, assignment, variable);
			if (pontuacao > candidatos.peek().getPontuacao()) {
				candidatos.add(new PontuacaoAssignment(pontuacao, assignment));
				if (candidatos.size() > quantCandidatos) {
					candidatos.poll();
				}
			}
		}
	}
	
	/**
	 * Método que realiza o calculo da função objetivo de um Assignment.
	 * 
	 * @param csp
	 * @param assignment
	 * @param variable
	 * @return
	 */
	private float getObjectiveFunctionValue(CSP<TurmaVariable, IClassDomainRepresentation> csp,
			Assignment<TurmaVariable, IClassDomainRepresentation> assignment, TurmaVariable variable) {
		float pont = 0;
		for (TurmaVariable turma : csp.getVariables()) {
			IClassDomainRepresentation value = assignment.getValue(turma);

			if (value.getProfessor() != null) {
				pont += PONT_PROFESSOR_TURMA;
			} else {
				pont -= PONT_PROFESSOR_TURMA;
			}

			for (Constraint<TurmaVariable, IClassDomainRepresentation> restricao : csp.getConstraints()) {
				if (!(restricao instanceof TurmaDevePossuirProfessor)) {
					if (restricao.isSatisfiedWith(assignment)) {
						pont += PONT_RESTRICAO;
					} else {
						pont -= PONT_RESTRICAO;
					}
				}
			}
		}
		return pont;
	}

	public PriorityQueue<PontuacaoAssignment> getCandidatos() {
		return candidatos;
	}
	
	/**
	 * Abstração para tornar a ordenação dos Assignments mais simples.
	 * 
	 * @author Eduardo Fillipe da Silva Reis
	 *
	 */
	public class PontuacaoAssignment {
		private float pontuacao;
		private Assignment<TurmaVariable, IClassDomainRepresentation> assignment;

		public PontuacaoAssignment() {
			super();
		}

		public PontuacaoAssignment(float pontuacao, Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
			super();
			this.pontuacao = pontuacao;
			this.assignment = assignment;
		}

		public float getPontuacao() {
			return pontuacao;
		}

		public void setPontuacao(float pontuacao) {
			this.pontuacao = pontuacao;
		}

		public Assignment<TurmaVariable, IClassDomainRepresentation> getAssignment() {
			return assignment;
		}

		public void setAssignment(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
			this.assignment = assignment;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PontuacaoAssignment [pontuacao=");
			builder.append(pontuacao);
			builder.append(", assignment=");
			builder.append(assignment);
			builder.append("]");
			return builder.toString();
		}
	}

	/**
	 * Comparator da Classe {@link CandidatosSolucaoListener.PontuacaoAssignment}.
	 * 
	 * @author Eduardo Fillipe da Silva Reis
	 *
	 */
	private class PontuacaoAssignmentComparator implements Comparator<PontuacaoAssignment> {
		@Override
		public int compare(PontuacaoAssignment o1, PontuacaoAssignment o2) {
			return Float.compare(o1.getPontuacao(), o2.getPontuacao());
		}
	}
}
