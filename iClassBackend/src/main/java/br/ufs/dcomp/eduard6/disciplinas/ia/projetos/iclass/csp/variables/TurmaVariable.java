package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables;

import aima.core.search.csp.Variable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;
/**
 * Representa uma variável do IClassCSP
 * 
 * @author Eduardo Fillipe da Silva Reis
 */
public class TurmaVariable extends Variable {
	/**
	 * Turma associada a essa Variável.
	 */
	private TurmaTO turmaAssociada;
	/**
	 * Quantidade total de variáveis que essa turma possui.
	 */
	private int quantidadeHorarios; 
	/**
	 * Identificador da instancia dessa turma. 
	 * 
	 * 0 <= idHorarioTurma < quantidadeHorarios.
	 * 
	 * Utilizado para identificar unicamente esse variável no csp.
	 */
	private int idHorarioTurma;

	public TurmaVariable(String uniqueName, TurmaTO turma, int quantidadeDivisoes, int idHorarioTurma) {
		super(uniqueName);
		this.turmaAssociada = turma;
		this.quantidadeHorarios = quantidadeDivisoes;
		this.idHorarioTurma = idHorarioTurma;
	}
	
	public TurmaVariable(TurmaTO turma, int quantidadeDivisoes, int idHorarioTurma) {
		super(turma.getCodTurma() + "." + idHorarioTurma);
		this.turmaAssociada = turma;
		this.quantidadeHorarios = quantidadeDivisoes;
		this.idHorarioTurma = idHorarioTurma;
	}

	public TurmaTO getTurmaAssociada() {
		return turmaAssociada;
	}

	public int getQuantidadeDivisoes() {
		return quantidadeHorarios;
	}
	
	public int getFragmentSequence() {
		return this.idHorarioTurma;
	}
}
