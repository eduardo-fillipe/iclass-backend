package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Classe que representa uma turma.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class TurmaTO extends TransferObjectBase {
	private DisciplinaTO disciplina;
	private ProfessorTO professor;
	private LinkedHashMap<String, HorarioTO> horariosAulas;
	private String codTurma;

	public TurmaTO() {
		super();
	}

	public TurmaTO(DisciplinaTO disciplina, ProfessorTO professor, List<HorarioTO> horariosAulas, String codTurma) {
		super();
		this.disciplina = disciplina;
		this.professor = professor;

		this.horariosAulas = new LinkedHashMap<String, HorarioTO>();
		
		if (horariosAulas != null) {
			for (HorarioTO horario : horariosAulas)
				this.horariosAulas.put(horario.getCodigo(), horario);
		}
		
		this.codTurma = codTurma;
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

	public String getCodTurma() {
		return codTurma;
	}

	public void setCodTurma(String codTurma) {
		this.codTurma = codTurma;
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

	/**
	 * Verifica se os horários cadastrados nessa turma condizem com os horários da
	 * disciplina.
	 * 
	 * @return a diferença entre a carga horária da disciplina e a soma dos horários
	 *         cadastrados na turma.
	 */
	public short getDiferencaHorarios() {
		short sum = 0;

		for (HorarioTO horario : horariosAulas.values())
			sum += horario.getHorarioSequencia().getValor();

		if (disciplina == null)
			throw new NullPointerException(
					"Não foi inserida uma disciplina na turma. " + "Não é possível comparar os horários.");

		return (short) (disciplina.getCargaHoraria() - sum);
	}

	/**
	 * Verifica se os horários da turma estão completos
	 * 
	 * @return true se a soma dos horários cadastrados na turma é igual ao esperado
	 *         da disciplina da turma.
	 */
	public boolean isHorariosCompletos() {
		return getDiferencaHorarios() == 0;
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
