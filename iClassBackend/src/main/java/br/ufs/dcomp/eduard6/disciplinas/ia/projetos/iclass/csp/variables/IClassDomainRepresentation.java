package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;

/**
 * Representa o valor de uma variável no iClassCSP.
 * <br>
 * Cada variável pode possui um {@link HorarioTO} e um {@link ProfessorTO}
 * associado. O professor dessa classe pode ser nulo, indicando que essa turma,
 * apesar de possuir um horario, não possui professor.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class IClassDomainRepresentation {
	private HorarioTO horario;
	private ProfessorTO professor;
	private String uniqueCod;

	public IClassDomainRepresentation(HorarioTO horario, ProfessorTO professor, String uniqueCod) {
		super();
		this.horario = horario;
		this.professor = professor;
		this.uniqueCod = uniqueCod;
	}

	public IClassDomainRepresentation(HorarioTO horario, ProfessorTO professor) {
		super();
		this.horario = horario;
		this.professor = professor;
		if (professor != null)
			this.uniqueCod = horario.getCodigo() + "." + professor.getMatricula();
		else
			this.uniqueCod = horario.getCodigo() + "." + "null";
	}

	public HorarioTO getHorario() {
		return horario;
	}

	public ProfessorTO getProfessor() {
		return professor;
	}

	public String getUniqueCod() {
		return uniqueCod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((horario == null) ? 0 : horario.hashCode());
		result = prime * result + ((professor == null) ? 0 : professor.hashCode());
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
		IClassDomainRepresentation other = (IClassDomainRepresentation) obj;
		if (horario == null) {
			if (other.horario != null)
				return false;
		} else if (!horario.equals(other.horario))
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IClassDomainRepresentation [horario=");
		builder.append(horario != null ? horario.getCodigo() : null);
		builder.append(", professor=");
		builder.append(professor != null ? professor.getNome() : null);
		builder.append("]");
		return builder.toString();
	}
}
