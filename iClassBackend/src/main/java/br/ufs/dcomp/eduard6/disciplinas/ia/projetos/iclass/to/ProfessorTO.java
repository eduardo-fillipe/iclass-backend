package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.util.ArrayList;
import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.DisciplinaPOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.ProfessorCompletoPOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.ProfessorPOJO;

/**
 * Representa um Professor.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ProfessorTO extends TransferObjectBase{
	private String nome;
	private String matricula;
	/**
	 * Lista de preferências. Se as preferências do professores for nula ou vazia,
	 * implica que ele pode lecionar qualquer disciplina.
	 */
	private List<DisciplinaTO> preferencias;
	/**
	 * Carga horária disponível para o professor dar aula.
	 */
	private short cargaHorariaSemanal;
	
	private List<HorarioTO> preferenciaHorarios;
	
	public ProfessorTO(String nome, String matricula, List<DisciplinaTO> preferencias, short cargaHorariaSemanal, List<HorarioTO> preferenciaHorarios) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.preferencias = preferencias;
		this.cargaHorariaSemanal = cargaHorariaSemanal;
		this.preferenciaHorarios = preferenciaHorarios;
	}
	
	public ProfessorTO(ProfessorPOJO pojo) {
		this.nome = pojo.getNome();
		this.cargaHorariaSemanal = pojo.getCargaHorariaSemanal();
		this.matricula = pojo.getMatricula();
		this.preferencias = new ArrayList<DisciplinaTO>();
		this.preferenciaHorarios = new ArrayList<>();
		
		if (pojo.getPreferenciaHorarios() != null) {
			pojo.getPreferenciaHorarios().forEach(h -> {
				this.preferenciaHorarios.add(new HorarioTO(h));
			});
		}
		
		pojo.getPreferencias().forEach(d -> {
			DisciplinaTO disciplina = new DisciplinaTO();
			disciplina.setCodigo(d);
			this.preferencias.add(disciplina);
		});
	}
	
	public ProfessorTO() {
		super();
	}
	
	public ProfessorTO(ProfessorCompletoPOJO pojo) {
		this.cargaHorariaSemanal = pojo.getCargaHorariaSemanal();
		this.matricula = pojo.getMatricula();
		this.nome = pojo.getNome();
		this.preferencias = new ArrayList<>();
		this.preferenciaHorarios = new ArrayList<>();
		
		if (pojo.getPreferenciaHorarios() != null) {
			pojo.getPreferenciaHorarios().forEach(h -> {
				this.preferenciaHorarios.add(new HorarioTO(h));
			});
		}
		
		for (DisciplinaPOJO d : pojo.getPreferencias()) {
			preferencias.add(new DisciplinaTO(d));
		}
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public List<DisciplinaTO> getPreferencias() {
		return preferencias;
	}
	public void setPreferencias(List<DisciplinaTO> preferencias) {
		this.preferencias = preferencias;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		ProfessorTO other = (ProfessorTO) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

	public short getCargaHorariaSemanal() {
		return cargaHorariaSemanal;
	}

	public void setCargaHorariaSemanal(short cargaHorariaSemanal) {
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}

	public List<HorarioTO> getPreferenciaHorarios() {
		return preferenciaHorarios;
	}

	public void setPreferenciaHorarios(List<HorarioTO> preferenciaHorarios) {
		this.preferenciaHorarios = preferenciaHorarios;
	}

	@Override
	public String toString() {
		return "ProfessorTO [nome=" + nome + ", matricula=" + matricula + ", preferencias=" + preferencias
				+ ", cargaHorariaSemanal=" + cargaHorariaSemanal + ", preferenciaHorarios=" + preferenciaHorarios + "]";
	}	
}
