package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.OganizadorIClassBase;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.OganizadorIClassBase.CspSolverEnum;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.DisciplinaDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.GradeDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb.ProfessorDAO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ResultadoOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO.HorarioSequencia;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO.TurnoGrade;

public class TestesOrganizador {
	public static void main(String[] args) {
		// Professores
		List<ProfessorTO> professorTOs = ProfessorDAO.getInstance().getProfessores();
		ProfessorTO estombelo = ProfessorDAO.getInstance().getProfessorCompleto("1");
		ProfessorTO breno = ProfessorDAO.getInstance().getProfessorCompleto("2");


		// Disciplinas
		DisciplinaTO compiladores = (DisciplinaDAO.getInstance().getDisciplina("CP001"));
		DisciplinaTO ed = (DisciplinaDAO.getInstance().getDisciplina("ED001"));
		DisciplinaTO ia = (DisciplinaDAO.getInstance().getDisciplina("IA001"));
		//DisciplinaTO labRedes = (DisciplinaDAO.getInstance().getDisciplina("LR001"));
		DisciplinaTO grafos = (DisciplinaDAO.getInstance().getDisciplina("GR001"));
		//DisciplinaTO progI = (DisciplinaDAO.getInstance().getDisciplina("PI001"));
		DisciplinaTO pi = (DisciplinaDAO.getInstance().getDisciplina("PI002"));

		// Horarios
		HorarioTO horarioCP1 = new HorarioTO("2N12", DayOfWeek.MONDAY, HorarioSequencia.DOIS, null, (short) 1);
		HorarioTO horarioCP2 = new HorarioTO("4N12", DayOfWeek.WEDNESDAY, HorarioSequencia.DOIS, null, (short) 1);
		ArrayList<HorarioTO> horariosCP = new ArrayList<>(Arrays.asList(horarioCP1, horarioCP2));

		HorarioTO horarioED1 = new HorarioTO("3N12", DayOfWeek.TUESDAY, HorarioSequencia.DOIS, null, (short) 1);
		HorarioTO horarioED2 = new HorarioTO("5N12", DayOfWeek.THURSDAY, HorarioSequencia.DOIS, null, (short) 1);
		ArrayList<HorarioTO> horariosED = new ArrayList<>(Arrays.asList(horarioED1, horarioED2));

		// Turmas
		TurmaTO turmaEd = new TurmaTO(ed, estombelo, horariosED, "ED001");
		TurmaTO turmaCp = new TurmaTO(compiladores, breno, horariosCP, "CP001");
		TurmaTO turmaIA = new TurmaTO(ia, null, null, "IA001");
		//TurmaTO turmaLR = new TurmaTO(labRedes, null, null, "LR001");
		TurmaTO turmaGrafos = new TurmaTO(grafos, null, null, "GR001");
		//TurmaTO turmaProgI = new TurmaTO(progI, null, null, "PI001");
		TurmaTO turmaPI = new TurmaTO(pi, null, null, "PI002");

		List<TurmaTO> turmasPredef = new ArrayList<>(Arrays.asList(turmaEd, turmaCp));
		List<TurmaTO> turmasObrigatorias = new ArrayList<>(
				Arrays.asList(turmaGrafos, turmaIA, turmaPI));

		// Problema
		ProblemaOrganizacaoTO problema = new ProblemaOrganizacaoTO();
		problema.setDescricao("Problema de Teste "
				+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(System.currentTimeMillis())));
		problema.setCargaHorariaGrade(4);
		problema.setProfessores(professorTOs);
		problema.setTurmasObrigatorias(turmasObrigatorias);
		problema.setTurmasPredefinida(turmasPredef);
		problema.setTurnoGrade(TurnoGrade.NOITE);

		OganizadorIClassBase organizador = new OrganizadorIClass(problema, CspSolverEnum.BACKTRACKING, true);
		ResultadoOrganizacaoTO resultado = organizador.organize();
		System.out.println(resultado);
		if (resultado != null) {
			GradeDAO.getInstance().inserirGrade(resultado);
		}
	}
}
