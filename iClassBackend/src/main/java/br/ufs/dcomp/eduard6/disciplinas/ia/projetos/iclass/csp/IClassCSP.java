package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.ApenasAulasConsecutivasNoDia;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.AulasParalelas;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.CargaHorariaProfessor;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.TurmaDevePossuirProfessor;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.PreferenciaProfessor;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes.ProfessorResponsavelAulasTurma;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO.HorarioSequencia;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;

/**
 * Classe que controla a Lógica CSP do problema.
 * 
 * @author Eduardo Fillipe da Silva Reis
 */
public class IClassCSP extends CSP<TurmaVariable, IClassDomainRepresentation> {
	private Set<TurmaTO> turmasObrigatorias;
	private Set<ProfessorTO> professores;
	private Set<TurmaTO> turmasPredefinidas;
	private Set<HorarioTO> horarios;
	private ProblemaOrganizacaoTO problema;
	private List<IClassDomainRepresentation> dominioProblema;
	/**
	 * Link de Variáveis que essa turma possui para consulta rápida.
	 */
	private Map<TurmaTO, List<TurmaVariable>> fragmentosTurma;
	public static final int MIN_DOMAIN_PROBLEM_SIZE = 2;
	/*
	 * Logger desta classe.
	 */
	private static final Logger LOGGER = Logger.getLogger(IClassCSP.class.getName());

	public IClassCSP(ProblemaOrganizacaoTO problema) {
		long tempoInicial = 0;
		long tempoParcial = 0;
		long tempoTotal = 0;

		this.problema = problema;
		this.turmasObrigatorias = new HashSet<>();
		this.turmasPredefinidas = new HashSet<>();
		this.professores = new HashSet<>();
		this.horarios = new HashSet<>();
		this.fragmentosTurma = new HashMap<>();

		LOGGER.log(Level.INFO, "Verificando argumentos...");
		tempoInicial = System.currentTimeMillis();
		verificarArgumentos(problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoParcial - tempoInicial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): {0}", (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Normalizando Horários...");
		tempoInicial = System.currentTimeMillis();
		normalizarHorarios(problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoParcial - tempoInicial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): {0}", (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Carregando conjuntos...");
		tempoInicial = System.currentTimeMillis();
		loadSets(this.problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoParcial - tempoInicial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): {0}", (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Gerando Horarios Disponíveis...");
		tempoInicial = System.currentTimeMillis();
		gerarHorarios(problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoParcial - tempoInicial;
		LOGGER.log(Level.INFO, "Ok! Total de horários adicionados: {0}. Tempo(ms): {1}",
				new Object[] { horarios.size(), (tempoParcial - tempoInicial) });

		LOGGER.log(Level.INFO, "Gerando Dominio do Problema (Horarios x Professor)...");
		tempoInicial = System.currentTimeMillis();
		dominioProblema = gerarDominioProblema(horarios, professores);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoParcial - tempoInicial;
		LOGGER.log(Level.INFO, "Ok! Total de valores de domínio gerados: {0}. Tempo(ms): {1}",
				new Object[] { dominioProblema.size(), (tempoParcial - tempoInicial) });

		LOGGER.log(Level.INFO, "Adicionando as Turmas como variáveis do problema...");
		tempoInicial = System.currentTimeMillis();
		adicionarVariaveisAoProblema();
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoParcial - tempoInicial;
		LOGGER.log(Level.INFO, "Ok! Total de variáveis adicionadas: {0}. Tempo(ms): {1}",
				new Object[] { getVariables().size(), (tempoParcial - tempoInicial) });

		LOGGER.log(Level.INFO, "Adicionando as Restricoes do problema...");
		tempoInicial = System.currentTimeMillis();
		adicionarRestricoes();
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoParcial - tempoInicial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): {0}", (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Construção do CSP finalizada. Tempo(ms): {0}", tempoTotal);
	}

	/**
	 * Retorna uma combinação de todas as possíveis configurações de horários e
	 * professores possíveis.
	 * 
	 * @param horarios
	 * @param professores
	 * @return Todas as possíveis configurações de horário possíveis.
	 */
	private List<IClassDomainRepresentation> gerarDominioProblema(Set<HorarioTO> horarios,
			Set<ProfessorTO> professores) {
		List<IClassDomainRepresentation> result = new ArrayList<>();

		horarios.forEach(horario -> { // Para cada horário disponível.
			professores.forEach(professor -> { // Gere cada combinação possível de professor.
				IClassDomainRepresentation iClassDomainRepresentation = new IClassDomainRepresentation(horario,
						professor);
				result.add(iClassDomainRepresentation);
			});
			IClassDomainRepresentation horarioComProfVazio = new IClassDomainRepresentation(horario, null);
			result.add(horarioComProfVazio);
		});

		return result;
	}

	/**
	 * Transforma todos os horários das turmas predefinidas de tamanho 4 para 2.
	 */
	private void normalizarHorarios(ProblemaOrganizacaoTO problema) {
		for (TurmaTO turma : problema.getTurmasPredefinidas()) {
			ArrayList<HorarioTO> novosHorarios = new ArrayList<>();
			ArrayList<HorarioTO> horariosTurma = new ArrayList<>(turma.getHorariosAulas().values());
			for (int i = 0; i < horariosTurma.size(); i++) {
				if (horariosTurma.get(i).getHorarioSequencia() != HorarioSequencia.DOIS) {
					novosHorarios.addAll(horariosTurma.get(i).transformarEmSequenciaDois());
				} else {
					novosHorarios.add(horariosTurma.get(i));
				}
			}
			turma.getHorariosAulas().clear();
			for (HorarioTO horario : novosHorarios) {
				turma.getHorariosAulas().put(horario.getCodigo(), horario);
			}
		}
	}

	/**
	 * Adiciona as turmas ao problema CSP como variáveis.
	 */
	private void adicionarVariaveisAoProblema() {
		Domain<IClassDomainRepresentation> dominioTurmasObrigatorias = new Domain<>(dominioProblema);
		for (TurmaTO turma : this.turmasObrigatorias) {
			int quantidadeHorarios = turma.getDisciplina().getCargaHoraria() / MIN_DOMAIN_PROBLEM_SIZE;
			List<TurmaVariable> turmasVariableTurma = new ArrayList<>(quantidadeHorarios); // Variaveis que essa turma
																							// possui.
			for (int i = 0; i < quantidadeHorarios; i++) {
				TurmaVariable turmaVariable = new TurmaVariable(turma, quantidadeHorarios, i);
				turmasVariableTurma.add(turmaVariable);
				addVariable(turmaVariable);
				setDomain(turmaVariable, dominioTurmasObrigatorias);
				addConstraint(new PreferenciaProfessor(this, turmaVariable));
				addConstraint(new TurmaDevePossuirProfessor(this, turmaVariable));

			}

			this.fragmentosTurma.put(turma, turmasVariableTurma); // Adiciona um índice de variaveis que essa turma
																	// possui.
			addConstraint(new ProfessorResponsavelAulasTurma(this, turma));
			addConstraint(new ApenasAulasConsecutivasNoDia(this, turma));
		}

		for (TurmaTO turmaPredefinida : this.turmasPredefinidas) {
			int quantidadeHorarios = turmaPredefinida.getDisciplina().getCargaHoraria() / MIN_DOMAIN_PROBLEM_SIZE;
			List<TurmaVariable> turmasVariableTurma = new ArrayList<>(quantidadeHorarios); // Variaveis que essa turma
																							// possui.
			ArrayList<HorarioTO> horariosTurma = new ArrayList<>(turmaPredefinida.getHorariosAulas().values());

			for (int i = 0; i < quantidadeHorarios; i++) {
				TurmaVariable turmaVariable = new TurmaVariable(turmaPredefinida, quantidadeHorarios, i);
				turmasVariableTurma.add(turmaVariable);
				addVariable(turmaVariable);
				Domain<IClassDomainRepresentation> dominioTurmaPredefinida = new Domain<>(
						new IClassDomainRepresentation(horariosTurma.get(i), turmaPredefinida.getProfessor()));
				setDomain(turmaVariable, dominioTurmaPredefinida);
			}
			this.fragmentosTurma.put(turmaPredefinida, turmasVariableTurma); // Adiciona um índice de variaveis que essa
																				// turma possui.
		}
	}

	/**
	 * Adiciona as restricoes ao csp. <br>
	 * <br>
	 * Aulas Paralelas ({@link AulasParalelas}): Para a instancia de problema do da
	 * disciplina, mesmo que as turmas sejam diferentes, elas não devem ter aulas
	 * paralelas. Entretanto, num ambiente real, se fizerem parte de turmas
	 * diferentes, podem haver aulas paralelas e essa parte do método deve ser
	 * alterada.
	 */
	private void adicionarRestricoes() {
		// Aulas Paralelas
		for (int i = 0; i < getVariables().size() - 1; i++) {
			for (int j = i + 1; j < getVariables().size(); j++) {
				addConstraint(new AulasParalelas(this, getVariables().get(i), getVariables().get(j)));
			}
		}

		// Carga Horária de Professor
		addConstraint(new CargaHorariaProfessor(this));
	}

	/**
	 * Carrega os conjuntos dessa classe.
	 * 
	 * @param problema
	 */
	private void loadSets(ProblemaOrganizacaoTO problema) {
		professores.addAll(problema.getProfessores());
		turmasPredefinidas.addAll(problema.getTurmasPredefinidas());
		turmasObrigatorias.addAll(problema.getTurmasObrigatorias());
	}

	/**
	 * Verifica se o horário pertence à uma turma predefinida.
	 * 
	 * @param horario
	 * @return <code>true</code> se o horário pertencer à uma turma predefinida ou
	 *         <code>false</code> senão.
	 */
	private boolean isHorarioPredefinido(HorarioTO horario) {
		for (TurmaTO t : this.turmasPredefinidas) {
			if (t.possuiHorario(horario))
				return true;
		}
		return false;
	}

	/**
	 * Adiciona todos os possíveis horários de acordo com o Problema.
	 * 
	 * @param problema
	 */
	private void gerarHorarios(ProblemaOrganizacaoTO problema) {
		for (int dia = DayOfWeek.MONDAY.getValue(); dia <= DayOfWeek.FRIDAY.getValue(); dia++) {
			for (int h = 1; h <= problema.getCargaHorariaGrade(); h += MIN_DOMAIN_PROBLEM_SIZE) {
				HorarioTO horarioTO = new HorarioTO();
				horarioTO.setDia(DayOfWeek.of(dia)); // A contagem começa de segunda = 1
				horarioTO.setHorarioSequencia(HorarioSequencia.DOIS);
				horarioTO.setNumeroHorario((short) h);
				StringBuilder stringBuilder = new StringBuilder(String.valueOf(horarioTO.getDia().getValue() + 1));
				stringBuilder.append(problema.getTurnoGrade().getValue());
				stringBuilder.append(h);
				stringBuilder.append(h + 1);
				horarioTO.setCodigo(stringBuilder.toString());
				if (!isHorarioPredefinido(horarioTO)) { // Redução do problema em termos de horarios já ocupados por
														// outras disciplinas obrigatórias.
					this.horarios.add(horarioTO);
				}
			}
		}
	}

	/**
	 * Retorna todos as variaveis derivadas de uma turma. Complexidade: O(1)
	 * 
	 * @param turma Turma a ser consultada.
	 * @return Lista com todas aas variáveis do problema que estão relacionadas a
	 *         essa turma.
	 */
	public List<TurmaVariable> getFragmentosTurma(TurmaTO turma) {
		return this.fragmentosTurma.get(turma);
	}

	/**
	 * Verifica se todos os argumentos do problema são válidos.
	 * 
	 * @throws IllegalArgumentException Se algum argumento inválido for encontrado.
	 * @param problema
	 */
	private void verificarArgumentos(ProblemaOrganizacaoTO problema) {
		if (problema.getProfessores() == null)
			throw new IllegalArgumentException("A lista de professores precisa ser fornecida.");
		if (problema.getTurmasObrigatorias() == null)
			throw new IllegalArgumentException("A lista de Turmas Obrigatórias precisa ser fornecida.");
		if (problema.getTurmasPredefinidas() == null)
			throw new IllegalArgumentException("A lista de Turmas Predefinidas precisa ser fornecida.");
		
		for (ProfessorTO professor : problema.getProfessores()) {
			if (professor.getPreferencias() == null) {
				if (professor.getPreferencias().size() >= 3 && professor.getPreferencias().size() <= 6) {
					continue;
				}
				throw new IllegalArgumentException("A quantidade de preferências do professor é menor que 3 ou maior que 6: " + professor);
			}
		}
		
		if (problema.getCargaHorariaGrade() < MIN_DOMAIN_PROBLEM_SIZE)
			throw new IllegalArgumentException("A carga horária diária da grade deve ser >= 2.");
		
		int sum = 0;

		for (TurmaTO turma : problema.getTurmasObrigatorias()) {
			if (turma.getDisciplina() != null)
				sum += turma.getDisciplina().getCargaHoraria();
			else
				throw new IllegalArgumentException("Turma sem disciplina associada: " + turma);

			if (turma.getDisciplina().getCargaHoraria() % 2 != 0)
				throw new IllegalArgumentException(
						"Todas as disciplinas devem possuir carga horaria múltipla de 2:" + turma);
		}

		for (TurmaTO turma : problema.getTurmasPredefinidas()) {
			if (turma.getDisciplina() != null)
				sum += turma.getDisciplina().getCargaHoraria();
			else
				throw new IllegalArgumentException("Turma sem disciplina associada: " + turma);

			if (!turma.isHorariosCompletos())
				throw new IllegalArgumentException(
						"Todas as turmas predefinidas devem possuir um horário completo: " + turma);

			if (turma.getDisciplina().getCargaHoraria() % 2 != 0)
				throw new IllegalArgumentException(
						"Todas as disciplinas devem possuir carga horaria múltipla de 2:" + turma);

			if (turma.getProfessor() == null)
				throw new IllegalArgumentException(
						"Todas as disciplinas predefinidas devem possuir um professor associado." + turma);

			HashMap<ProfessorTO, Integer> professorCargaHoraria = new HashMap<>();
			ProfessorTO professor = turma.getProfessor();
			Integer profCargaSum = professorCargaHoraria.get(professor);
			if (profCargaSum == null) {
				professorCargaHoraria.put(professor, (int) turma.getDisciplina().getCargaHoraria());
			} else {
				professorCargaHoraria.put(professor, profCargaSum + (int) turma.getDisciplina().getCargaHoraria());
			}

			if (professorCargaHoraria.get(professor) > professor.getCargaHorariaSemanal()) {
				throw new IllegalArgumentException(
						"Professor com disciplinas obrigatórias maior que a carga horária semanal: " + professor);
			}
		}

		if (sum > problema.getCargaHorariaGrade() * 5)
			throw new IllegalArgumentException("A quantidade de Turmas excede a carga horária máxima do período.");
	}

	public Set<HorarioTO> getHorarios() {
		return horarios;
	}

	public List<IClassDomainRepresentation> getDominioProblema() {
		return dominioProblema;
	}

	public Set<ProfessorTO> getProfessores() {
		return this.professores;
	}

	public Set<TurmaTO> getTurmasObrigatorias() {
		return this.turmasObrigatorias;
	}

	public Set<TurmaTO> getTurmasPredefinidas() {
		return this.turmasPredefinidas;
	}

	public ProblemaOrganizacaoTO getProblema() {
		return problema;
	}

	public void setProblema(ProblemaOrganizacaoTO problema) {
		this.problema = problema;
	}
}
