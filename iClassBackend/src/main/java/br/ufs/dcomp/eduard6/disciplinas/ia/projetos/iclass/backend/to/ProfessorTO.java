package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.backend.to;

import java.util.List;
public class ProfessorTO extends TransferObjectBase{
	private String nome;
	private String matricula;
	private List<DisciplinaTO> preferencias;
	
	public ProfessorTO(String nome, String matricula, List<DisciplinaTO> preferencias) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.preferencias = preferencias;
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
}
