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
}
