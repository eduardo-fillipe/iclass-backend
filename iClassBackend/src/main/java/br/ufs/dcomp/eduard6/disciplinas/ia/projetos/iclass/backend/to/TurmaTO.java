package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.backend.to;

import java.util.LinkedHashMap;

public class TurmaTO extends TransferObjectBase{
	private DisciplinaTO disciplina;
	private ProfessorTO professor;
	private LinkedHashMap<String, HorarioTO> horariosAulas;
	
	public TurmaTO() {
		super();
	}
	
	public DisciplinaTO getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(DisciplinaTO disciplina) {
		this.disciplina = disciplina;
	}
	public ProfessorTO getProfessor() {
		return professor;
	}
	public void setProfessor(ProfessorTO professor) {
		this.professor = professor;
	}
	public LinkedHashMap<String, HorarioTO> getHorariosAulas() {
		return horariosAulas;
	}
	public void setHorariosAulas(LinkedHashMap<String, HorarioTO> horariosAulas) {
		this.horariosAulas = horariosAulas;
	}
	public void addHorario(HorarioTO horario) {
		horariosAulas.put(horario.getCodigo(), horario);
	}
	public void removerHorario(HorarioTO horario) {
		this.horariosAulas.remove(horario.getCodigo());
	}
	public boolean possuiHorario(HorarioTO horario) {
		return this.horariosAulas.containsKey(horario.getCodigo());
	}
}
