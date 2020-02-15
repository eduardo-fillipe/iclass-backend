package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iClass;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.OrganizadorIClass;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.IOganizadorIClass;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.IOganizadorIClass.CspSolverEnum;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.DisciplinaDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.ProfessorDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO.HorarioSequencia;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO.TurnoGrade;

public class TestesOrganizador {
	public static void main(String[] args) {
		//Professores
		List<ProfessorTO> professorTOs = ProfessorDAO.getInstance().getProfessores();
		ProfessorTO estombelo = ProfessorDAO.getInstance().getProfessorCompleto("1");
		ProfessorTO breno = ProfessorDAO.getInstance().getProfessorCompleto("2");
		
		//Disciplinas
		DisciplinaTO compiladores = (DisciplinaDAO.getInstance().getDisciplina("CP001"));
		DisciplinaTO ed = (DisciplinaDAO.getInstance().getDisciplina("ED001"));
		DisciplinaTO ia = (DisciplinaDAO.getInstance().getDisciplina("IA001"));
		DisciplinaTO labRedes = (DisciplinaDAO.getInstance().getDisciplina("LR001"));
		
		//Horarios
		HorarioTO horarioCP1 = new HorarioTO("2T12", DayOfWeek.MONDAY, HorarioSequencia.DOIS, null, (short)1);
		HorarioTO horarioCP2 = new HorarioTO("4T12", DayOfWeek.WEDNESDAY, HorarioSequencia.DOIS, null, (short)1);
		ArrayList<HorarioTO> horariosCP = new ArrayList<HorarioTO>(Arrays.asList(horarioCP1, horarioCP2));
		
		HorarioTO horarioED1 = new HorarioTO("3T12", DayOfWeek.TUESDAY, HorarioSequencia.DOIS, null, (short)1);
		HorarioTO horarioED2 = new HorarioTO("5T12", DayOfWeek.THURSDAY, HorarioSequencia.DOIS, null, (short)1);
		ArrayList<HorarioTO> horariosED = new ArrayList<HorarioTO>(Arrays.asList(horarioED1, horarioED2));
		
		//Turmas
		TurmaTO turmaEd = new TurmaTO(ed, estombelo, horariosED, "ED001");
		TurmaTO turmaCp = new TurmaTO(compiladores, breno, horariosCP, "CP001");
		TurmaTO turmaIA = new TurmaTO(ia, null, null, "IA001");
		TurmaTO turmaLR = new TurmaTO(labRedes, null, null, "LR001");

		List<TurmaTO> turmasPredef = new ArrayList<TurmaTO>(Arrays.asList(turmaEd, turmaCp));
		List<TurmaTO> turmasObrigatorias = new ArrayList<TurmaTO>(Arrays.asList(turmaIA, turmaLR));
		
		//Problema
		ProblemaOrganizacaoTO problema = new ProblemaOrganizacaoTO();
		problema.setCargaHorariaGrade(6);
		problema.setProfessores(professorTOs);
		problema.setTurmasObrigatorias(turmasObrigatorias);
		problema.setTurmasPredefinida(turmasPredef);
		problema.setTurnoGrade(TurnoGrade.TARDE);

		IOganizadorIClass organizador = new OrganizadorIClass(problema, CspSolverEnum.BACKTRACKING_WITH_HEURISTCS);
		System.out.println(organizador.organize());
	}
}
