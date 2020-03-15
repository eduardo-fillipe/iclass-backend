package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.listener;

import java.util.Comparator;
import java.util.PriorityQueue;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.PreferenciaHorariosProfessor;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.TurmaDevePossuirProfessor;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.util.CspUtil;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;

/**
 * Classe responsável por manter uma fila de prioridades dos N melhores
 * candidatos, caso o CSP falhe.
 * 
 * Utiliza uma função objetivo para avaliar e decidir se o csp será mantido ou
 * não. Essa função objetivo é CARA, ela deve verificar todas as turmas de
 * Assignment Completo para definir uma pontuação para o mesmo. Isso pode ser
 * apriomorado, se durante à busca sejam passadas para a função objetivo
 * informações que a própria busca já verificou, como quais restricoes o CSP
 * viola.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class CandidatosSolucaoListener implements IClassCspListener<TurmaVariable, IClassDomainRepresentation> {

	private PriorityQueue<PontuacaoAssignment> candidatos;

	private int quantCandidatos;

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

	public CandidatosSolucaoListener(int quantCandidatos) {
		super();
		this.candidatos = new PriorityQueue<>(new PontuacaoAssignmentComparator());
		this.quantCandidatos = quantCandidatos;
	}

	
	@Override
	public void iClassStateChanged(CSP<TurmaVariable, IClassDomainRepresentation> csp,
			Assignment<TurmaVariable, IClassDomainRepresentation> assignment, TurmaVariable variable, float pontuacao) {
		if (candidatos.peek() != null) {
			if (pontuacao > candidatos.peek().getPontuacao()) {
				PontuacaoAssignment pA = new PontuacaoAssignment(pontuacao, assignment.clone());
				if (!candidatos.contains(pA)) {
					candidatos.add(pA);
					if (candidatos.size() > quantCandidatos) {
						candidatos.poll();
					}
				}
			}
		} else {
			candidatos.add(new PontuacaoAssignment(pontuacao, assignment));
		}
	}

	/**
	 * Método que realiza o calculo da função objetivo de um Assignment.
	 * 
	 * @param csp
	 * @param assignment
	 * @param variable
	 * @return Uma pontuação que determina o quão boa é uma determinada grade. Quão
	 *         maior o valor, melhor a grade.
	 */
	@SuppressWarnings("unused")
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
		}

		for (TurmaVariable turma : csp.getVariables()) {
			IClassDomainRepresentation valueTuma = assignment.getValue(turma);
			if (valueTuma != null && valueTuma.getProfessor() != null) {
				if (isProfessorEmHorarioPreferencial(valueTuma.getHorario(), valueTuma.getProfessor())) {
					pont += PONT_PROFESSOR_HORARIO;
				} else {
					pont -= PONT_PROFESSOR_HORARIO;
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
				}
			}
		}
		return pont;
	}

	/**
	 * Retorna se um professor possui um determinado horario em sua lista de preferências.
	 * @param horario
	 * @param professor
	 * @return True se um professor possui um determinado horario em sua lista de preferências.
	 */
	private boolean isProfessorEmHorarioPreferencial(HorarioTO horario, ProfessorTO professor) {
		return professor.getPreferenciaHorarios().contains(horario); 
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((assignment == null) ? 0 : assignment.hashCode());
			result = prime * result + Float.floatToIntBits(pontuacao);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PontuacaoAssignment other = (PontuacaoAssignment) obj;
			if (Float.floatToIntBits(pontuacao) != Float.floatToIntBits(other.pontuacao))
				return false;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (assignment == null) {
				if (other.assignment != null)
					return false;
			} else if (!CspUtil.areEquals(assignment, other.assignment))
				return false;
			return true;
		}

		private CandidatosSolucaoListener getEnclosingInstance() {
			return CandidatosSolucaoListener.this;
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
