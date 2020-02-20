package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
/**
 * Representa um Horario no banco de dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class HorarioPOJO {
	private TurmaPOJO turma;
	private String codigo;
	private int horarioSequencia;
	private int numeroHorario;

	public HorarioPOJO(HorarioTO horarioTO) {
		super();
		if (horarioTO.getTurma() != null)
		    this.turma = new TurmaPOJO(horarioTO.getTurma());
		this.codigo = horarioTO.getCodigo();
		this.horarioSequencia = horarioTO.getHorarioSequencia().getValor();
		this.numeroHorario = horarioTO.getNumeroHorario();
	}

	public HorarioPOJO() {
		super();
	}

	public HorarioPOJO(TurmaPOJO turma, String codigo, int horarioSequencia, int numeroHorario) {
		super();
		this.turma = turma;
		this.codigo = codigo;
		this.horarioSequencia = horarioSequencia;
		this.numeroHorario = numeroHorario;
	}

	public TurmaPOJO getTurma() {
		return turma;
	}

	public void setTurma(TurmaPOJO turma) {
		this.turma = turma;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getHorarioSequencia() {
		return horarioSequencia;
	}

	public void setHorarioSequencia(int horarioSequencia) {
		this.horarioSequencia = horarioSequencia;
	}

	public int getNumeroHorario() {
		return numeroHorario;
	}

	public void setNumeroHorario(int numeroHorario) {
		this.numeroHorario = numeroHorario;
	}
}
