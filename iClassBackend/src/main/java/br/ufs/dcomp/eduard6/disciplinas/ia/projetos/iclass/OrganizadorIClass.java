package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass;

import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.IOganizadorIClass;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;


public class OrganizadorIClass implements IOganizadorIClass{

	@Override
	public List<GradeTO> organize(ProblemaOrganizacaoTO problema) {
		IClassCSP csp = new IClassCSP(problema);
		System.out.println(csp.getHorarios());
		return null;
	}
	
}
