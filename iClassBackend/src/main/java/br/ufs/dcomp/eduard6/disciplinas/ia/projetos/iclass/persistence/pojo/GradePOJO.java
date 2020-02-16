package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
/**
 * Representa uma Grade no banco de dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class GradePOJO {
	private ObjectId _id;
	private List<HorarioPOJO> horarios;
	private MetricaPOJO metricas;

	public GradePOJO(List<HorarioPOJO> horarios, MetricaPOJO metricas) {
		super();
		this.horarios = horarios;
		this.metricas = metricas;
	}

	public GradePOJO(GradeTO grade) {
		this.metricas = new MetricaPOJO(grade.getMetricas());
		this.horarios = new ArrayList<HorarioPOJO>(grade.getHorarios().size());

		for (HorarioTO horarioTO : grade.getHorarios()) {
			this.horarios.add(new HorarioPOJO(horarioTO));
		}
	}

	public List<HorarioPOJO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioPOJO> horarios) {
		this.horarios = horarios;
	}

	public MetricaPOJO getMetricas() {
		return metricas;
	}

	public void setMetricas(MetricaPOJO metricas) {
		this.metricas = metricas;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}
}
