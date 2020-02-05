package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.util.List;

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
	
	public ProfessorTO() {
		super();
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
}
