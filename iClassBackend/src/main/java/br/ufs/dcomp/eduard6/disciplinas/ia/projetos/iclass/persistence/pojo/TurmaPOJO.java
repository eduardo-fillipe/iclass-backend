package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;
/**
 * Representa uma Turma no banco de dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class TurmaPOJO {
	private String matriculaProfessor;
	private String codDisciplina;
	private String codTurma;

	public TurmaPOJO(TurmaTO turma) {
		this.matriculaProfessor = turma.getProfessor() != null ? turma.getProfessor().getMatricula() : null;
		this.codDisciplina = turma.getDisciplina().getCodigo();
		this.codTurma = turma.getCodTurma();
	}

	public TurmaPOJO(String matriculaProfessor, String codDisciplina, String codTurma) {
		super();
		this.matriculaProfessor = matriculaProfessor;
		this.codDisciplina = codDisciplina;
		this.codTurma = codTurma;
	}
	
	public TurmaPOJO() {
		
	}

	public String getMatriculaProfessor() {
		return matriculaProfessor;
	}

	public void setMatriculaProfessor(String matriculaProfessor) {
		this.matriculaProfessor = matriculaProfessor;
	}

	public String getCodDisciplina() {
		return codDisciplina;
	}

	public void setCodDisciplina(String codDisciplina) {
		this.codDisciplina = codDisciplina;
	}

	public String getCodTurma() {
		return codTurma;
	}

	public void setCodTurma(String codTurma) {
		this.codTurma = codTurma;
	}
}
