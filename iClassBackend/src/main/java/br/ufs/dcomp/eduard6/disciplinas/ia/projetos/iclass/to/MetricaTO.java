package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.OganizadorIClassBase.CspSolverEnum;

public class MetricaTO extends TransferObjectBase {
	private int quantidadeAtribuicoes;
	private int quantidadeInferencias;
	private CspSolverEnum algorithm;
	private long timeToSolve;
	
	public MetricaTO() {
		super();
	}

	public MetricaTO(int quantidadeAtribuicoes, int quantidadeInferencias, CspSolverEnum algorithm, long timeToSolve) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MetricaTO [quantidadeAtribuicoes=");
		builder.append(quantidadeAtribuicoes);
		builder.append(", quantidadeInferencias=");
		builder.append(quantidadeInferencias);
		builder.append(", algorithm=");
		builder.append(algorithm);
		builder.append(", timeToSolve=");
		builder.append(timeToSolve);
		builder.append("]");
		return builder.toString();
	}
}
