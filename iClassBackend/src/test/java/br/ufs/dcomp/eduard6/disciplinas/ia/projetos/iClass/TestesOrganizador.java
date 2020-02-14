package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iClass;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.FlexibleBacktrackingSolver;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.OrganizadorIClass;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.IOganizadorIClass;
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
		IOganizadorIClass organizador = new OrganizadorIClass();

		List<ProfessorTO> professorTOs = ProfessorDAO.getInstance().getProfessores();
		List<DisciplinaTO> disciplinaTOs = DisciplinaDAO.getInstance().getDisciplinasPorNome("Lab", 1);
		List<TurmaTO> turmasObrigatorias = new ArrayList<TurmaTO>();
		List<TurmaTO> turmasPredef = new ArrayList<TurmaTO>();

		ProblemaOrganizacaoTO problema = new ProblemaOrganizacaoTO();
		problema.setCargaHorariaGrade(6);
		problema.setProfessores(professorTOs);
		problema.setTurmasObrigatorias(turmasObrigatorias);
		problema.setTurmasPredefinida(turmasPredef);
		problema.setTurnoGrade(TurnoGrade.TARDE);

		organizador.organize(problema,
				new FlexibleBacktrackingSolver<TurmaVariable, IClassDomainRepresentation>().setAll());

//		HorarioTO h = new HorarioTO();
//		h.setCodigo("2T3456");
//		h.setDia(DayOfWeek.MONDAY);
//		h.setHorarioSequencia(HorarioSequencia.QUATRO);
//		h.setNumeroHorario((short)1);
//		
//		System.out.println(h);
//		System.out.println(h.transformarEmSequenciaDois());
	}
}
