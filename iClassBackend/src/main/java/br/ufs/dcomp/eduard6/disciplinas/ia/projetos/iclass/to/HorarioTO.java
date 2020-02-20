package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.HorarioPOJO;

/**
 * Classe que representa o Horario como TO.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class HorarioTO extends TransferObjectBase implements Comparable<HorarioTO> {

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
		
		public static HorarioSequencia fromValue(int seq) {
			switch (seq) {
			case 2:
				return DOIS;

			case 4:
				return QUATRO;
			default:
				return null;
			}
		}
	}

	public HorarioTO(HorarioTO horario) {
		this.codigo = horario.getCodigo();
		this.dia = horario.getDia();
		this.horarioSequencia = horario.getHorarioSequencia();
		this.numeroHorario = horario.getNumeroHorario();
		this.turma = horario.getTurma();
	}

	/**
	 * Constrói um horárioTO vazio a partir de um código dado.
	 * 
	 * @param codigoHorario
	 * @return
	 */
	public static HorarioTO fromCodigo(String codigoHorario) {
		HorarioTO horarioTO = new HorarioTO();
		
		horarioTO.setCodigo(codigoHorario);
		
		char cDia = codigoHorario.charAt(0);
        int iDia = Integer.parseInt((String.valueOf(cDia)));
        horarioTO.setDia(DayOfWeek.of(iDia - 1));
        
        int qtHorarios = codigoHorario.substring(2).length();
        
        horarioTO.setHorarioSequencia(HorarioSequencia.fromValue(qtHorarios));
        horarioTO.setNumeroHorario(Short.parseShort(String.valueOf(codigoHorario.charAt(2))));
 
		return horarioTO;
	}

	public HorarioTO(String codigo, DayOfWeek dia, HorarioSequencia horarioSequencia, TurmaTO turma,
			short numeroHorario) {
		super();
		if (!isCodigoValido(codigo))
			throw new IllegalArgumentException("Código de horário inválido.");

		this.codigo = codigo;
		this.dia = dia;
		this.horarioSequencia = horarioSequencia;
		this.turma = turma;
		this.numeroHorario = numeroHorario;
	}

	public List<HorarioTO> transformarEmSequenciaDois() {
		ArrayList<HorarioTO> novos = new ArrayList<>();

		int quantidadeHorariosTam2 = this.getHorarioSequencia().getValor() / 2;

		if (quantidadeHorariosTam2 > 1) {
			for (int i = 0; i < quantidadeHorariosTam2; i++) {
				HorarioTO horarioNovo = new HorarioTO();
				horarioNovo.setDia(this.dia);
				horarioNovo.setHorarioSequencia(HorarioSequencia.DOIS);

				char n = this.codigo.substring((i * 2) + 2).charAt(0);
				horarioNovo.setNumeroHorario((short) (Short.valueOf(String.valueOf(n))));

				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(horarioNovo.getDia().getValue() + 1);
				stringBuilder.append(this.codigo.charAt(1));
				stringBuilder.append(horarioNovo.getNumeroHorario());
				stringBuilder.append(horarioNovo.getNumeroHorario() + 1);
				horarioNovo.setCodigo(stringBuilder.toString());
				novos.add(horarioNovo);
			}
		} else {
			novos.add(this);
			return novos;
		}

		return novos;
	}

	/**
	 * Verifica se uma dada sequencia de horários é consecutiva. O(nlog(n))
	 * 
	 * @param horarios
	 * @return
	 */
	public static boolean isConsecutivos(List<HorarioTO> horarios) {
		ArrayList<List<HorarioTO>> dias = new ArrayList<List<HorarioTO>>();
		for (int i = 1; i <= DayOfWeek.FRIDAY.getValue(); i++) // O(5)
			dias.add(new ArrayList<HorarioTO>());

		for (HorarioTO horario : horarios) { // O(n)
			if (horario != null)
				dias.get(horario.getDia().getValue() - 1).add(horario);
		}

		for (List<HorarioTO> dia : dias) { // O(n)
			if (dia.size() > 1) {
				Collections.sort(dia); // O(log(n))
				for (int i = 0; i < dia.size() - 1; i++) {
					if (dia.get(i).numeroHorario + 2 != dia.get(i + 1).numeroHorario)
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * Método auxiliar que verifica se um dado código é válido.
	 * 
	 * @return true se o código for válido.
	 */
	private static boolean isCodigoValido(String codigo) {
		return true;
	}
	
	public HorarioTO(HorarioPOJO horarioPOJO) { 
		this.codigo = horarioPOJO.getCodigo();
		char cDia = horarioPOJO.getCodigo().charAt(0);
		int iDia = Integer.parseInt((String.valueOf(cDia)));
		this.dia = DayOfWeek.of(iDia - 1);
		this.horarioSequencia = HorarioSequencia.fromValue(horarioPOJO.getHorarioSequencia());
		this.numeroHorario = (short)horarioPOJO.getNumeroHorario();
		if (horarioPOJO.getTurma() != null)
		    this.turma = new TurmaTO(horarioPOJO.getTurma());
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

	@Override
	public String toString() {
		return "HorarioTO [codigo=" + codigo + ", dia=" + dia + ", horarioSequencia=" + horarioSequencia + ", turma="
				+ turma + ", numeroHorario=" + numeroHorario + "]";
	}

	@Override
	public int compareTo(HorarioTO o) {
		if (this.getDia().getValue() > o.getDia().getValue()) {
			return 1;
		} else {
			if (this.getDia().getValue() < o.getDia().getValue()) {
				return -1;
			} else {
				if (this.getNumeroHorario() > o.getNumeroHorario()) {
					return 1;
				} else {
					if (this.getNumeroHorario() < o.getNumeroHorario()) {
						return -1;
					} else {
						return 0;
					}
				}
			}
		}
	}
}
