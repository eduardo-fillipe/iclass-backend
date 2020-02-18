package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.util.List;
import java.util.Optional;

public class ResultadoOrganizacaoTO extends TransferObjectBase {
	private Optional<GradeTO> solucao;
	private List<GradeTO> melhoresSolucoesParciais;
	
	public ResultadoOrganizacaoTO(GradeTO solucao, List<GradeTO> melhoresSolucoesParciais) {
		super();
		this.solucao = solucao == null ? Optional.empty() : Optional.of(solucao);
		this.melhoresSolucoesParciais = melhoresSolucoesParciais;
	}

	public Optional<GradeTO> getSolucao() {
		return solucao;
	}

	public void setSolucao(Optional<GradeTO> solucao) {
		this.solucao = solucao;
	}

	public List<GradeTO> getMelhoresSolucoesParciais() {
		return melhoresSolucoesParciais;
	}

	public void setMelhoresSolucoesParciais(List<GradeTO> melhoresSolucoesParciais) {
		this.melhoresSolucoesParciais = melhoresSolucoesParciais;
	}
}
