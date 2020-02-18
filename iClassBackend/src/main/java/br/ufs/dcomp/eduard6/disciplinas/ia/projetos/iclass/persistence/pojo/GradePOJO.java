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

	public GradePOJO(List<HorarioPOJO> horarios) {
		super();
		this.horarios = horarios;
	}

	public GradePOJO(GradeTO grade) {
		this.horarios = new ArrayList<>(grade.getHorarios().size());
		for (HorarioTO horarioTO : grade.getHorarios()) {
			this.horarios.add(new HorarioPOJO(horarioTO));
		}
	}
	
	public GradePOJO() {
	    
	}

	public List<HorarioPOJO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioPOJO> horarios) {
		this.horarios = horarios;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}
}
