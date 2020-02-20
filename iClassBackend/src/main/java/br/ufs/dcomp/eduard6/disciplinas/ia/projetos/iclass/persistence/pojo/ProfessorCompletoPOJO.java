package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;

import java.util.ArrayList;
import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
/**
 * Representa um Professor (Completo, com informações de outras tabelas) no Banco de Dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ProfessorCompletoPOJO {
	private String nome;
	private String matricula;
	private List<DisciplinaPOJO> preferencias;
	private short cargaHorariaSemanal;
	private List<HorarioPOJO> preferenciaHorarios;
	
	public ProfessorCompletoPOJO(String nome, String matricula, List<DisciplinaPOJO> preferencias, short cargaHorariaSemanal, List<HorarioPOJO> preferenciaHorarios) {
		this.nome = nome;
		this.matricula = matricula;
		this.preferencias = preferencias;
		this.cargaHorariaSemanal = cargaHorariaSemanal;
		this.preferenciaHorarios = preferenciaHorarios;
	}
	
	public ProfessorCompletoPOJO() {
		
	}
	
	public static ProfessorCompletoPOJO fromTO(ProfessorTO professor) {
		ProfessorCompletoPOJO p = new ProfessorCompletoPOJO(professor.getNome(),
				professor.getMatricula(),
				new ArrayList<DisciplinaPOJO>(professor.getPreferencias().size()),
				professor.getCargaHorariaSemanal(), new ArrayList<HorarioPOJO>());
		
		if (professor.getPreferenciaHorarios() != null) {
			professor.getPreferenciaHorarios().forEach(h -> {
				p.getPreferenciaHorarios().add(new HorarioPOJO());
			});
		}
		
		professor.getPreferencias().forEach(o -> {
			p.getPreferencias().add(new DisciplinaPOJO(o));
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

	public List<DisciplinaPOJO> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<DisciplinaPOJO> preferencias) {
		this.preferencias = preferencias;
	}

	public short getCargaHorariaSemanal() {
		return cargaHorariaSemanal;
	}

	public void setCargaHorariaSemanal(short cargaHorariaSemanal) {
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}

	public List<HorarioPOJO> getPreferenciaHorarios() {
		return preferenciaHorarios;
	}

	public void setPreferenciaHorarios(List<HorarioPOJO> preferenciaHorarios) {
		this.preferenciaHorarios = preferenciaHorarios;
	}
}
