package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspListener;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.OganizadorIClassBase;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.MetricaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;

public class OrganizadorIClass extends OganizadorIClassBase {

	public OrganizadorIClass(ProblemaOrganizacaoTO problema, CspSolverEnum solverAlgorithm) {
		super(problema, solverAlgorithm);
	}
	
	public OrganizadorIClass(ProblemaOrganizacaoTO problema) {
		super(problema, CspSolverEnum.BACKTRACKING_WITH_HEURISTCS);
	}

	@Override
	public GradeTO organize() {
		long startTime = System.currentTimeMillis();
		if (getProblema() == null)
			throw new IllegalArgumentException("Problema não podem ser nulo.");

		CspListener.StepCounter<TurmaVariable, IClassDomainRepresentation> stepCounter = new CspListener.StepCounter<>();

		getSolver().addCspListener(stepCounter);

		IClassCSP csp = new IClassCSP(getProblema());
		Optional<Assignment<TurmaVariable, IClassDomainRepresentation>> result = getSolver().solve(csp);

		GradeTO grade = null;

		if (result.isPresent()) {
			long endTime = System.currentTimeMillis();
			
			MetricaTO metrica = new MetricaTO();
			metrica.setAlgorithm(getSolverAlgorithm());
			metrica.setTimeToSolve(endTime - startTime);
			metrica.setQuantidadeAtribuicoes(stepCounter.getResults().getInt("assignmentCount"));
			metrica.setQuantidadeInferencias(stepCounter.getResults().getInt("inferenceCount"));
			
			grade = toGradeTO(result.get(), csp, metrica);
		}

		return grade;
	}

	private GradeTO toGradeTO(Assignment<TurmaVariable, IClassDomainRepresentation> result, IClassCSP csp, MetricaTO metrica) {
		GradeTO grade = new GradeTO(result, csp, metrica);

		List<HorarioTO> horarios = new ArrayList<HorarioTO>();

		for (TurmaVariable turmaVariable : result.getVariables()) {
			IClassDomainRepresentation valorTurmaVariable = result.getValue(turmaVariable);

			HorarioTO horario = valorTurmaVariable.getHorario();
			ProfessorTO professor = valorTurmaVariable.getProfessor();
			TurmaTO turma = turmaVariable.getTurmaAssociada();

			horario.setTurma(turma);
			turma.setProfessor(professor);
			if (turma.getHorariosAulas() == null)
				turma.setHorariosAulas(new LinkedHashMap<String, HorarioTO>());
			turma.getHorariosAulas().put(horario.getCodigo(), horario);

			horarios.add(horario);
		}

		Collections.sort(horarios);
		grade.setHorarios(horarios);
		return grade;
	}

}

//System.out.println("Horarios: " + csp.getHorarios());
//System.out.println("Dominio: " + csp.getDominioProblema());
//System.out.println("Professores: " + csp.getProfessores());
//System.out.println("Turmas Obrigatorias: " + csp.getTurmasObrigatorias());
//System.out.println("Turmas Predefinidas: " + csp.getTurmasPredefinidas());
