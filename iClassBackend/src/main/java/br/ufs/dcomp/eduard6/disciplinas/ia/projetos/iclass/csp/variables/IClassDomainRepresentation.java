package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables;

public class IClassDomainRepresentation {
	private String cdHorario;
	private String cdProfessor;
	private String uniqueCod;

	public IClassDomainRepresentation(String cdHorario, String cdProfessor, String uniqueCod) {
		super();
		this.cdHorario = cdHorario;
		this.cdProfessor = cdProfessor;
		this.uniqueCod = uniqueCod;
	}
	
	public IClassDomainRepresentation(String cdHorario, String cdProfessor) {
		super();
		this.cdHorario = cdHorario;
		this.cdProfessor = cdProfessor;
		this.uniqueCod = cdHorario + "." + cdProfessor;
	}

	public String getCdHorario() {
		return cdHorario;
	}

	public void setCdHorario(String cdHorario) {
		this.cdHorario = cdHorario;
	}

	public String getCdProfessor() {
		return cdProfessor;
	}

	public void setCdProfessor(String cdProfessor) {
		this.cdProfessor = cdProfessor;
	}

	public String getUniqueCod() {
		return uniqueCod;
	}

	public void setUniqueCod(String uniqueCod) {
		this.uniqueCod = uniqueCod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdHorario == null) ? 0 : cdHorario.hashCode());
		result = prime * result + ((cdProfessor == null) ? 0 : cdProfessor.hashCode());
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
		if (cdHorario == null) {
			if (other.cdHorario != null)
				return false;
		} else if (!cdHorario.equals(other.cdHorario))
			return false;
		if (cdProfessor == null) {
			if (other.cdProfessor != null)
				return false;
		} else if (!cdProfessor.equals(other.cdProfessor))
			return false;
		return true;
	}
}
