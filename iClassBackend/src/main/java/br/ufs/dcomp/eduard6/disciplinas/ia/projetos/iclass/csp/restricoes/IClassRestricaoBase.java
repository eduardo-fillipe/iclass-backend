package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import aima.core.search.csp.Constraint;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;

/**
 * 
 * @author Eduardo Fillipe da Silva Reis
 */
public abstract class IClassRestricaoBase implements Constraint<TurmaVariable, IClassDomainRepresentation> {
	protected IClassCSP iClassCSP;
	
	public IClassRestricaoBase(IClassCSP iClassCSP) {
		this.iClassCSP = iClassCSP;
	}

	public IClassCSP getiClassCSP() {
		return iClassCSP;
	}

	public void setiClassCSP(IClassCSP iClassCSP) {
		this.iClassCSP = iClassCSP;
	}
}
