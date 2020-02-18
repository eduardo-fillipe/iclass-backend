package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.OganizadorIClassBase.CspSolverEnum;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.MetricaPOJO;

public class MetricaTO extends TransferObjectBase {
	private int quantidadeAtribuicoes;
	private int quantidadeInferencias;
	private CspSolverEnum algorithm;
	private long timeToSolve;
	private boolean possuiSolucao;
	private int tamanhoDominioTurmasObrigatorias;
	private int quantProfessores;
	private int quantTurmas;
	
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

	public MetricaTO(MetricaPOJO metricaPojo) {
        super();
        this.quantidadeAtribuicoes = metricaPojo.getQuantidadeAtribuicoes();
        this.quantidadeInferencias = metricaPojo.getQuantidadeInferencias();
        this.algorithm = metricaPojo.getAlgorithm();
        this.timeToSolve = metricaPojo.getTimeToSolve();
        this.possuiSolucao = metricaPojo.isPossuiSolucao();
        this.tamanhoDominioTurmasObrigatorias = metricaPojo.getTamanhoDominioTurmasObrigatorias();
        this.quantProfessores = metricaPojo.getQuantProfessores();
        this.quantTurmas = metricaPojo.getQuantTurmas();
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

	public boolean isPossuiSolucao() {
        return possuiSolucao;
    }

    public void setPossuiSolucao(boolean possuiSolucao) {
        this.possuiSolucao = possuiSolucao;
    }

    public int getTamanhoDominioTurmasObrigatorias() {
        return tamanhoDominioTurmasObrigatorias;
    }

    public void setTamanhoDominioTurmasObrigatorias(
            int tamanhoDominioTurmasObrigatorias) {
        this.tamanhoDominioTurmasObrigatorias = tamanhoDominioTurmasObrigatorias;
    }

    public int getQuantProfessores() {
        return quantProfessores;
    }

    public void setQuantProfessores(int quantProfessores) {
        this.quantProfessores = quantProfessores;
    }

    public int getQuantTurmas() {
        return quantTurmas;
    }

    public void setQuantTurmas(int quantTurmas) {
        this.quantTurmas = quantTurmas;
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
        builder.append(", possuiSolucao=");
        builder.append(possuiSolucao);
        builder.append(", tamanhoDominioTurmasObrigatorias=");
        builder.append(tamanhoDominioTurmasObrigatorias);
        builder.append(", quantProfessores=");
        builder.append(quantProfessores);
        builder.append(", quantTurmas=");
        builder.append(quantTurmas);
        builder.append("]");
        return builder.toString();
    }
}
