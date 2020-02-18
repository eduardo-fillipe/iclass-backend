package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.GradePOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.ResultadoOrganizacaoPOJO;

/**
 * Classe que representa o Resultado de um problema de organização como TO.
 * 
 * @author fillipe
 */
public class ResultadoOrganizacaoTO extends TransferObjectBase {
    private String descricao;

    private Optional<GradeTO> solucao;

    private List<GradeTO> melhoresSolucoesParciais;

    private MetricaTO metricas;

    public ResultadoOrganizacaoTO(String descricao, GradeTO solucao,
            List<GradeTO> melhoresSolucoesParciais, MetricaTO metricas) {
        super();
        this.solucao = solucao == null ? Optional.empty()
                : Optional.of(solucao);
        this.melhoresSolucoesParciais = melhoresSolucoesParciais;
        this.metricas = metricas;
        this.descricao = descricao;
    }

    public ResultadoOrganizacaoTO(ResultadoOrganizacaoPOJO resultado) {
        this.descricao = resultado.getDescricao();
        this.solucao = resultado.getSolucao() != null
                ? Optional.of(new GradeTO(resultado.getSolucao())) : Optional.empty();
        
        if (resultado.getMelhoresSolucoesParciais() != null) {
            this.melhoresSolucoesParciais = new ArrayList<>();
            for (GradePOJO gradeParcialPojo : resultado.getMelhoresSolucoesParciais() ) {
                this.melhoresSolucoesParciais.add(new GradeTO(gradeParcialPojo));
            }
        }
        
        this.metricas = new MetricaTO(resultado.getMetricas());
    }

    public ResultadoOrganizacaoTO() {
        super();
    }

    public Optional<GradeTO> getSolucao() {
        return solucao;
    }

    public void setSolucao(GradeTO solucao) {
        this.solucao = solucao == null ? Optional.empty()
                : Optional.of(solucao);
    }

    public List<GradeTO> getMelhoresSolucoesParciais() {
        return melhoresSolucoesParciais;
    }

    public void setMelhoresSolucoesParciais(
            List<GradeTO> melhoresSolucoesParciais) {
        this.melhoresSolucoesParciais = melhoresSolucoesParciais;
    }

    public MetricaTO getMetricas() {
        return metricas;
    }

    public void setMetricas(MetricaTO metricas) {
        this.metricas = metricas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResultadoOrganizacaoTO ["
                + "descricao=");
        builder.append(descricao);
        builder.append(",\n solucao=");
        builder.append(solucao);
        builder.append(",\n melhoresSolucoesParciais=");
        builder.append(melhoresSolucoesParciais);
        builder.append(",\n metricas=");
        builder.append(metricas);
        builder.append("]");
        return builder.toString();
    }
}
