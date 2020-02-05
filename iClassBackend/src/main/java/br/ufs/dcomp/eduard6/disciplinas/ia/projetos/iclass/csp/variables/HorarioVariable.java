package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables;

import aima.core.search.csp.Variable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;

public class HorarioVariable extends Variable {
	
	private HorarioTO horario;

	public HorarioVariable(HorarioTO horario) {
		super(horario.getCodigo());
		this.horario = horario;
	}
	
	public HorarioTO getHorario() {
		return this.horario;
	}
}
