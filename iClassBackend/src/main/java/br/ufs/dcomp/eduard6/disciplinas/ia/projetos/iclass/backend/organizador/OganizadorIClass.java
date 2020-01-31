package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.backend.organizador;

import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.backend.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.backend.to.ProblemaOrganizacaoTO;

public interface OganizadorIClass {
	public List<GradeTO> organize(ProblemaOrganizacaoTO problema);
}
