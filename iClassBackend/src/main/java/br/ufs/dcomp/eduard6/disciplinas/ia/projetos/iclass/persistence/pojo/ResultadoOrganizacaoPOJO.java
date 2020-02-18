package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;

import java.util.ArrayList;
import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ResultadoOrganizacaoTO;

/**
 * Representa um objeto de Resultado de Organização no Banco de Dados.
 * 
 * @author Eduardo Fillipe da Silva Reis.
 */
public class ResultadoOrganizacaoPOJO {
    private String descricao;

    private GradePOJO solucao;

    private List<GradePOJO> melhoresSolucoesParciais;

    private MetricaPOJO metricas;

    public ResultadoOrganizacaoPOJO() {
        super();
    }

    public ResultadoOrganizacaoPOJO(String descricao, GradePOJO solucao,
            List<GradePOJO> melhoresSolucoesParciais, MetricaPOJO metricas) {
        super();
        this.descricao = descricao;
        this.solucao = solucao;
        this.melhoresSolucoesParciais = melhoresSolucoesParciais;
        this.metricas = metricas;
    }

    public ResultadoOrganizacaoPOJO(ResultadoOrganizacaoTO resultadoTO) {
        this.descricao = resultadoTO.getDescricao();
        this.solucao = resultadoTO.getSolucao().isPresent()
                ? new GradePOJO(resultadoTO.getSolucao().get()) : null;
        if (resultadoTO.getMelhoresSolucoesParciais() != null){
            this.melhoresSolucoesParciais = new ArrayList<>(resultadoTO.getMelhoresSolucoesParciais().size());
            for (GradeTO gradeParcial : resultadoTO.getMelhoresSolucoesParciais()) {
                melhoresSolucoesParciais.add(new GradePOJO(gradeParcial));
            }
        }
        this.metricas = new MetricaPOJO(resultadoTO.getMetricas());
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public GradePOJO getSolucao() {
        return solucao;
    }

    public void setSolucao(GradePOJO solucao) {
        this.solucao = solucao;
    }

    public List<GradePOJO> getMelhoresSolucoesParciais() {
        return melhoresSolucoesParciais;
    }

    public void setMelhoresSolucoesParciais(
            List<GradePOJO> melhoresSolucoesParciais) {
        this.melhoresSolucoesParciais = melhoresSolucoesParciais;
    }

    public MetricaPOJO getMetricas() {
        return metricas;
    }

    public void setMetricas(MetricaPOJO metricas) {
        this.metricas = metricas;
    }
}
