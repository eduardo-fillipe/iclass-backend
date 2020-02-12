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
	
	public ProfessorTO(String nome, String matricula, List<DisciplinaTO> preferencias, short cargaHorariaSemanal) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.preferencias = preferencias;
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}
	
	public ProfessorTO(ProfessorPOJO pojo) {
		this.nome = pojo.getNome();
		this.cargaHorariaSemanal = pojo.getCargaHorariaSemanal();
		this.matricula = pojo.getMatricula();
		this.preferencias = new ArrayList<DisciplinaTO>();
				
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
		this.preferencias = new ArrayList<DisciplinaTO>();
		
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
	public boolean equals(Object obj) {
		if (obj instanceof ProfessorTO) {
			ProfessorTO profObj = (ProfessorTO) obj;
			return profObj.matricula.equals(this.matricula);
		}
		return false;
	}

	public short getCargaHorariaSemanal() {
		return cargaHorariaSemanal;
	}

	public void setCargaHorariaSemanal(short cargaHorariaSemanal) {
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProfessorTO [nome=");
		builder.append(nome);
		builder.append(", matricula=");
		builder.append(matricula);
		builder.append(", preferencias=");
		builder.append(preferencias);
		builder.append(", cargaHorariaSemanal=");
		builder.append(cargaHorariaSemanal);
		builder.append("]");
		return builder.toString();
	}
	
	
}
