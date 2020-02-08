package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp;

import java.util.HashMap;
import java.util.Map;

import aima.core.search.csp.CSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.HorarioVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;

/**
 * Classe que controla a Lógica CSP do problema.
 * 
 * @author Eduardo Fillipe da Silva Reis
 */
public class IClassCSP extends CSP<HorarioVariable, TurmaDomainRepresentation>{
	private HashMap<String, TurmaTO> turmas;
	private HashMap<String, ProfessorTO> professores;
	private HashMap<String, TurmaTO> turmasPredefinidas;
	
	public IClassCSP(ProblemaOrganizacaoTO problema) {
		
	}
	
	private TurmaDomainRepresentation gerarTurmasPossiveis(Map<String, TurmaTO> turmas, Map<String, ProfessorTO> professores) {
		throw new UnsupportedOperationException();
	}
}
