package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.OganizadorIClassBase.CspSolverEnum;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.MetricaTO;

/**
 * Representa uma Métrica de resultado de execução do csp no banco de dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class MetricaPOJO {
	private int quantidadeAtribuicoes;
	private int quantidadeInferencias;
	private CspSolverEnum algorithm;
	private long timeToSolve;

	public MetricaPOJO() {

	}

	public MetricaPOJO(MetricaTO metrica) {
		this.quantidadeAtribuicoes = metrica.getQuantidadeAtribuicoes();
		this.algorithm = metrica.getAlgorithm();
		this.quantidadeInferencias = metrica.getQuantidadeInferencias();
		this.timeToSolve = metrica.getTimeToSolve();
	}

	public MetricaPOJO(int quantidadeAtribuicoes, int quantidadeInferencias, CspSolverEnum algorithm,
			long timeToSolve) {
		super();
		this.quantidadeAtribuicoes = quantidadeAtribuicoes;
		this.quantidadeInferencias = quantidadeInferencias;
		this.algorithm = algorithm;
		this.timeToSolve = timeToSolve;
	}

	public int getQuantidadeAtribuicoes() {
		return quantidadeAtribuicoes;
	}

	public void setQuantidadeAtribuicoes(int quantidadeAtribuicoes) {
		this.quantidadeAtribuicoes = quantidadeAtribuicoes;
	}

	public int getQuantidadeInferencias() {
		return quantidadeInferencias;
	}

	public void setQuantidadeInferencias(int quantidadeInferencias) {
		this.quantidadeInferencias = quantidadeInferencias;
	}

	public CspSolverEnum getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(CspSolverEnum algorithm) {
		this.algorithm = algorithm;
	}

	public long getTimeToSolve() {
		return timeToSolve;
	}

	public void setTimeToSolve(long timeToSolve) {
		this.timeToSolve = timeToSolve;
	}
}
