package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
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
	public static final int MIN_DOMAIN_PROBLEM_SIZE = 2;
	/*
	 * Logger desta classe.
	 */
	private static Logger LOGGER = Logger.getLogger(IClassCSP.class.getName());

	public IClassCSP(ProblemaOrganizacaoTO problema) {
		long tempoInicial = 0;
		long tempoParcial = 0;
		long tempoTotal = 0;

		this.problema = problema;
		this.turmasObrigatorias = new HashSet<TurmaTO>();
		this.turmasPredefinidas = new HashSet<TurmaTO>();
		this.professores = new HashSet<ProfessorTO>();
		this.horarios = new HashSet<HorarioTO>();

		LOGGER.log(Level.INFO, "Verificando argumentos...");
		tempoInicial = System.currentTimeMillis();
		verificarArgumentos(problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): " + (tempoParcial - tempoInicial));
		
		LOGGER.log(Level.INFO, "Normalizando Horários...");
		tempoInicial = System.currentTimeMillis();
		normalizarHorarios(problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): " + (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Carregando conjuntos...");
		tempoInicial = System.currentTimeMillis();
		loadSets(this.problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): " + (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Gerando Horarios Disponíveis...");
		tempoInicial = System.currentTimeMillis();
		gerarHorarios(problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Total de horários adicionados: " + horarios.size() + ". Tempo(ms): "
				+ (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Gerando Dominio do Problema (Horarios x Professor)...");
		tempoInicial = System.currentTimeMillis();
		dominioProblema = gerarDominioProblema(horarios, professores);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Total de valores de domínio gerados: " + this.horarios.size() + ". Tempo(ms): "
				+ (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Adicionando as Turmas como variáveis do problema...");
		tempoInicial = System.currentTimeMillis();
		adicionarVariaveisAoProblema();
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Total de variáveis adicionadas " + getVariables().size() + ". Tempo(ms): "
				+ (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Adicionando as Restricoes do problema...");
		tempoInicial = System.currentTimeMillis();
		adicionarRestricoes();
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): " + (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Construção do CSP finalizada. Tempo(ms): " + tempoTotal);
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
		List<IClassDomainRepresentation> result = new ArrayList<IClassDomainRepresentation>();

		horarios.forEach(horario -> { // Para cada horário disponível.
			professores.forEach(professor -> { // Gere cada combinação possível de professor.
				IClassDomainRepresentation iClassDomainRepresentation = new IClassDomainRepresentation(
						horario.getCodigo(), professor.getMatricula());
				result.add(iClassDomainRepresentation);
			});
		});

		return result;
	}
	
	/**
	 * Transforma todos os horários das turmas predefinidas de tamanho 4 para 2.
	 */
	private void normalizarHorarios(ProblemaOrganizacaoTO problema) {
		for (TurmaTO turma : problema.getTurmasPredefinidas()) {
			ArrayList<HorarioTO> novosHorarios = new ArrayList<>();
			ArrayList<HorarioTO> horarios = new ArrayList<>(turma.getHorariosAulas().values());
			for (int i = 0; i < horarios.size(); i ++) {
				if (horarios.get(i).getHorarioSequencia() != HorarioSequencia.DOIS) {
					novosHorarios.addAll(horarios.get(i).transformarEmSequenciaDois());
				} else {
					novosHorarios.add(horarios.get(i));
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
			for (int i = 0; i < quantidadeHorarios; i++) {
				TurmaVariable turmaVariable = new TurmaVariable(turma, quantidadeHorarios, i);
				addVariable(turmaVariable);
				setDomain(turmaVariable, dominioTurmasObrigatorias);
			}
		}

		for (TurmaTO turmaPredefinida : this.turmasPredefinidas) {
			int quantidadeHorarios = turmaPredefinida.getDisciplina().getCargaHoraria() / MIN_DOMAIN_PROBLEM_SIZE;
			ArrayList<HorarioTO> horarios = new ArrayList<>(turmaPredefinida.getHorariosAulas().values());
			
			for (int i = 0; i < quantidadeHorarios; i++) {
				TurmaVariable turmaVariable = new TurmaVariable(turmaPredefinida, quantidadeHorarios, i);
				addVariable(turmaVariable);
				
				Domain<IClassDomainRepresentation> dominioTurmaPredefinida = new Domain<>(new IClassDomainRepresentation(horarios.get(i).getCodigo(), turmaPredefinida.getProfessor().getMatricula()));
				
				setDomain(turmaVariable, dominioTurmaPredefinida);
			}
		}
	}

	/**
	 * Adiciona as restricoes ao csp.
	 */
	private void adicionarRestricoes() {

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

	private boolean isHorarioPredefinido(HorarioTO horario) {
		for (TurmaTO t : turmasPredefinidas) {
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
				if (isHorarioPredefinido(horarioTO)) { // Redução do problema em termos de horarios já ocupados por
														// outras // disciplinas
					this.horarios.add(horarioTO);
				}
			}
		}
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

		if (problema.getCargaHorariaGrade() == 0)
			throw new IllegalArgumentException("A carga horária diária da grade deve ser > 0.");

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
		}

		if (sum > problema.getCargaHorariaGrade() * 5)
			throw new IllegalArgumentException("A quantidade de Turmas excede a carga horária máxima do período.");

	}

	public Set<HorarioTO> getHorarios() {
		return horarios;
	}

	public List<IClassDomainRepresentation> getPossiveisTurmas() {
		return dominioProblema;
	}

}
