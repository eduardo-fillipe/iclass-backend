package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.time.DayOfWeek;


/**
 * Classe que representa o Horario como TO.
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class HorarioTO extends TransferObjectBase {
	
	private String codigo;
	private DayOfWeek dia;
	/**
	 * Quantidade de aulas/hora em sequencia desse horário, pode variar entre 2 e 4.
	 */
	private HorarioSequencia horarioSequencia;
	private TurmaTO turma;
	/**
	 * Numero do horário (primeiro, segundo, terceiro horário?)
	 */
	private short numeroHorario;
	
	/**
	 * Representa as sequencias de horários que as turmas podem assumir.
	 * 
	 * @author Eduardo Fillipe da Silva Reis
	 */
	public enum HorarioSequencia {
	    DOIS((short) 2), QUATRO((short) 4);
	    
	    private short valor;
	    
	    private HorarioSequencia(short horarioSequencia) {
            this.valor = horarioSequencia;
        }
	    
	    public short getValor() {
	        return valor;
	    }
	}
	
	/**
	 * Constrói um horárioTO vazio a partir de um código dado.
	 * @param codigoHorario
	 * @return
	 */
	public static HorarioTO fromCodigo(String codigoHorario) {
		throw new UnsupportedOperationException("Ainda não implementado.");
	}

	public HorarioTO(String codigo, DayOfWeek dia, HorarioSequencia horarioSequencia, TurmaTO turma) {
		super();
		if (!isCodigoValido())
		    throw new IllegalArgumentException("Código de horário inválido.");
		
		this.codigo = codigo;
		this.dia = dia;
		this.horarioSequencia = horarioSequencia;
		this.turma = turma;
	}
	/**
	 * Método auxiliar que verifica se um dado código é válido.
	 * @return true se o código for válido.
	 */
	private boolean isCodigoValido(){
	   throw new UnsupportedOperationException("Ainda não implementado.");
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

	public short getNumeroHorario() {
        return numeroHorario;
    }

    public void setNumeroHorario(short numeroHorario) {
        this.numeroHorario = numeroHorario;
    }

    public void setDia(DayOfWeek dia) {
		this.dia = dia;
	}

	public HorarioSequencia getHorarioSequencia() {
		return horarioSequencia;
	}

	public void setHorarioSequencia(HorarioSequencia horarioSequencia) {
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
