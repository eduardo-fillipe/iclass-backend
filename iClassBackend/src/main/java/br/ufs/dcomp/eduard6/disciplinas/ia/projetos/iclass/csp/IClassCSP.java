package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import aima.core.search.csp.CSP;
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
@SuppressWarnings("unused")
public class IClassCSP extends CSP<TurmaVariable, IClassDomainRepresentation> {
	private Set<TurmaTO> turmasObrigatorias;
	private Set<ProfessorTO> professores;
	private Set<TurmaTO> turmasPredefinidas;
	private Set<HorarioTO> horarios;
	private ProblemaOrganizacaoTO problema;
	private List<IClassDomainRepresentation> dominioProblema;
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
		if (problema.getProfessores() == null)
			throw new IllegalArgumentException("A lista de professores precisa ser fornecida.");
		if (problema.getTurmasObrigatorias() == null)
			throw new IllegalArgumentException("A lista de Turmas Obrigatórias precisa ser fornecida.");
		if (problema.getTurmasPredefinidas() == null)
			throw new IllegalArgumentException("A lista de Turmas Predefinidas precisa ser fornecida.");
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): " + (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Carregando conjuntos...");
		tempoInicial = System.currentTimeMillis();
		loadSets(this.problema);
		tempoParcial = System.currentTimeMillis();
		tempoTotal += tempoInicial - tempoParcial;
		LOGGER.log(Level.INFO, "Ok! Tempo(ms): " + (tempoParcial - tempoInicial));

		LOGGER.log(Level.INFO, "Gerando Horarios...");
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

		horarios.forEach(horario -> {
			if (true) {
				professores.forEach(professor -> {
					IClassDomainRepresentation iClassDomainRepresentation = new IClassDomainRepresentation(
							horario.getCodigo(), professor.getMatricula(),
							horario.getCodigo() + "." + professor.getMatricula());
					result.add(iClassDomainRepresentation);
				});
			}
		});

		return result;
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
	
	private boolean isHorarioPreenchido(String codHorario, Collection<TurmaTO> turmas) {
		boolean r = false;
		return r;
	}

	/**
	 * Adiciona todos os possíveis horários de acordo com o Problema.
	 * 
	 * @param problema
	 */
	private void gerarHorarios(ProblemaOrganizacaoTO problema) {
		for (int dia = 1; dia < 6; dia++) {
			for (int h = 1; h <= problema.getCargaHorariaGrade(); h += 2) {
				HorarioTO horarioTO = new HorarioTO();
				horarioTO.setDia(DayOfWeek.of(dia)); // A contagem começa de segunda = 1
				horarioTO.setHorarioSequencia(HorarioSequencia.DOIS);
				horarioTO.setNumeroHorario((short) h);
				StringBuilder stringBuilder = new StringBuilder(String.valueOf(horarioTO.getDia().getValue() + 1));
				stringBuilder.append(problema.getTurnoGrade().getValue());
				stringBuilder.append(h);
				stringBuilder.append(h + 1);
				horarioTO.setCodigo(stringBuilder.toString());
				this.horarios.add(horarioTO);
			}
		}
	}

	private void configurarDominios(ProblemaOrganizacaoTO problema) {

	}

	public Set<HorarioTO> getHorarios() {
		return horarios;
	}

	public List<IClassDomainRepresentation> getPossiveisTurmas() {
		return dominioProblema;
	}

}
