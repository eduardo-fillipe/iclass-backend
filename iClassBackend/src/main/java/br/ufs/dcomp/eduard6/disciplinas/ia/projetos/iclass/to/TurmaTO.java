package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.util.LinkedHashMap;
/**
 * Classe que representa uma turma.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class TurmaTO extends TransferObjectBase{
	private DisciplinaTO disciplina;
	private ProfessorTO professor;
	private LinkedHashMap<String, HorarioTO> horariosAulas;
	private String codTurma;
	
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TurmaTO other = (TurmaTO) obj;
		if (codTurma == null) {
			if (other.codTurma != null)
				return false;
		} else if (!codTurma.equals(other.codTurma))
			return false;
		return true;
	}
}
