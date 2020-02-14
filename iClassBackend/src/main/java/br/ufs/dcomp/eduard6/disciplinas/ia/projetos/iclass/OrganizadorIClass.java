package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass;

import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspSolver;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.IOganizadorIClass;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;

public class OrganizadorIClass implements IOganizadorIClass {

	@Override
	public GradeTO organize(ProblemaOrganizacaoTO problema,
			CspSolver<TurmaVariable, IClassDomainRepresentation> solver) {
		
		IClassCSP csp = new IClassCSP(problema);
		Optional<Assignment<TurmaVariable, IClassDomainRepresentation>> result = solver.solve(csp);
		
		System.out.println(result);
		
		return null;
	}
}

//System.out.println("Horarios: " + csp.getHorarios());
//System.out.println("Dominio: " + csp.getDominioProblema());
//System.out.println("Professores: " + csp.getProfessores());
//System.out.println("Turmas Obrigatorias: " + csp.getTurmasObrigatorias());
//System.out.println("Turmas Predefinidas: " + csp.getTurmasPredefinidas());
