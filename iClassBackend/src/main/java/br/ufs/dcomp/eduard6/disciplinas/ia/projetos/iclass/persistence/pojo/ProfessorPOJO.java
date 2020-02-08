package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;

import java.util.ArrayList;
import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
/**
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ProfessorPOJO {
	private String nome;
	private String matricula;
	private List<String> preferencias;
	private short cargaHorariaSemanal;
	
	public ProfessorPOJO(String nome, String matricula, List<String> preferencias, short cargaHorariaSemanal) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.preferencias = preferencias;
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}
	
	public static ProfessorPOJO fromTO(ProfessorTO professor) {
		ProfessorPOJO p = new ProfessorPOJO(professor.getNome(),
				professor.getMatricula(),
				new ArrayList<String>(professor.getPreferencias().size()),
				professor.getCargaHorariaSemanal());
		
		professor.getPreferencias().forEach(o -> {
			p.preferencias.add(o.getCodigo());
		});
		
		return p;
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

	public List<String> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<String> preferencias) {
		this.preferencias = preferencias;
	}

	public short getCargaHorariaSemanal() {
		return cargaHorariaSemanal;
	}

	public void setCargaHorariaSemanal(short cargaHorariaSemanal) {
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}
}
