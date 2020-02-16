package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

/**
 * Classe que representa da Grade como TO.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class GradeTO extends TransferObjectBase{
	
	private String descricao;
	private List<HorarioTO> horarios;
	private IClassCSP csp;
	private Assignment<TurmaVariable, IClassDomainRepresentation> assignment;
	private MetricaTO metricas;
	
	public GradeTO(Assignment<TurmaVariable, IClassDomainRepresentation> assignment, IClassCSP csp, MetricaTO metricas) {
		super();
		this.assignment = assignment;
		this.csp = csp;
		this.metricas = metricas;
	}

	public List<HorarioTO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioTO> horarios) {
		this.horarios = horarios;
	}

	public IClassCSP getCsp() {
		return csp;
	}

	public void setCsp(IClassCSP csp) {
		this.csp = csp;
	}

	public Assignment<TurmaVariable, IClassDomainRepresentation> getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
		this.assignment = assignment;
	}

	public MetricaTO getMetricas() {
		return metricas;
	}

	public void setMetricas(MetricaTO metricas) {
		this.metricas = metricas;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GradeTO [horarios=\n");
		for(HorarioTO horario : horarios) {
			builder.append(horario);
			builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
}
