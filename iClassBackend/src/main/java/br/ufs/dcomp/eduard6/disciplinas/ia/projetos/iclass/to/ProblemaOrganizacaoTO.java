package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import java.util.List;

/**
 * Class que representa o problema enviado pelo FrontEnd para ser resolvido.
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ProblemaOrganizacaoTO extends TransferObjectBase{
	
	public enum TurnoGrade {
		MANHA, TARDE, NOITE
	}
	
	/**
	 * Lista de professores.
	 */
	private List<ProfessorTO> professores;
	/**
	 * Turmas obrigatórias.
	 */
	private List<TurmaTO> turmasObrigatorias;
	/**
	 * Turma de outros departamentos
	 */
	private List<TurmaTO> turmasPredefinidas;
	/**
	 * Turno principal do período.
	 */
	private TurnoGrade turnoGrade;
	/**
	 * Quantidade Máxima de horas/aula permitidas por dia. Inicialmente seriam 6.
	 */
	private int cargaHorariaGrade;
	
	public ProblemaOrganizacaoTO(List<ProfessorTO> professores, List<TurmaTO> turmasObrigatorias,
			List<TurmaTO> turmasPredefinidas, TurnoGrade turnoGrade, int cargaHorariaGrade) {
		super();
		
		for (TurmaTO turmaObrigatoria : turmasPredefinidas) {
		    if (turmaObrigatoria.getHorariosAulas() == null || 
		            turmaObrigatoria.getHorariosAulas().isEmpty() ||
		            turmaObrigatoria.getDisciplina() != null ||
		            turmaObrigatoria.isHorariosCompletos()) {
		        throw new IllegalArgumentException("Toda disciplina predefinida deve possuir seus horários/disciplinas pre-definidos.");
		    }
		}
		
		this.professores = professores;
		this.turmasObrigatorias = turmasObrigatorias;
		this.turmasPredefinidas = turmasPredefinidas;
		this.turnoGrade = turnoGrade;
		this.cargaHorariaGrade = cargaHorariaGrade;
	}
	
	public ProblemaOrganizacaoTO() {
		super();
	}

	public List<ProfessorTO> getProfessores() {
		return professores;
	}
	public void setProfessores(List<ProfessorTO> professores) {
		this.professores = professores;
	}
	public List<TurmaTO> getTurmasObrigatorias() {
		return turmasObrigatorias;
	}
	public void setTurmasObrigatorias(List<TurmaTO> turmasObrigatorias) {
		this.turmasObrigatorias = turmasObrigatorias;
	}
	public List<TurmaTO> getTurmasPredefinidas() {
		return turmasPredefinidas;
	}
	public void setTurmasPredefinida(List<TurmaTO> turmasPredefinida) {
		this.turmasPredefinidas = turmasPredefinida;
	}
	public TurnoGrade getTurnoGrade() {
		return turnoGrade;
	}
	public void setTurnoGrade(TurnoGrade turnoGrade) {
		this.turnoGrade = turnoGrade;
	}
	public int getCargaHorariaGrade() {
		return cargaHorariaGrade;
	}
	public void setCargaHorariaGrade(int cargaHorariaGrade) {
		this.cargaHorariaGrade = cargaHorariaGrade;
	}
}