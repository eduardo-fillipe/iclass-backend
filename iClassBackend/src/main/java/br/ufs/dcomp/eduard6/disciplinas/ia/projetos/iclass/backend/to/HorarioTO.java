package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.backend.to;

import java.time.DayOfWeek;

public class HorarioTO extends TransferObjectBase {
	
	private String codigo;
	private DayOfWeek dia;
	private short horarioSequencia;
	private TurmaTO turma;
	
	public static HorarioTO fromCodigo(String codigoHorario) {
		throw new UnsupportedOperationException("Ainda não implementado.");
	}

	public HorarioTO(String codigo, DayOfWeek dia, short horarioSequencia, TurmaTO turma) {
		super();
		this.codigo = codigo;
		this.dia = dia;
		this.horarioSequencia = horarioSequencia;
	}

	public HorarioTO() {
		super();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public DayOfWeek getDia() {
		return dia;
	}

	public void setDia(DayOfWeek dia) {
		this.dia = dia;
	}

	public short getHorarioSequencia() {
		return horarioSequencia;
	}

	public void setHorarioSequencia(short horarioSequencia) {
		this.horarioSequencia = horarioSequencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		HorarioTO other = (HorarioTO) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public TurmaTO getTurma() {
		return turma;
	}

	public void setTurma(TurmaTO turma) {
		this.turma = turma;
	}
}
