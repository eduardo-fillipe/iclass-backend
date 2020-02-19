package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CspListener;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.listener.CandidatosSolucaoListener;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.organizador.OganizadorIClassBase;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.GradeTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.MetricaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProblemaOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ResultadoOrganizacaoTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.TurmaTO;

/**
 * Classe responsável por realizar a interface entre o Frontend e o backend do
 * Organizador iClass.
 * 
 * @author Eduardo Fillipe da Silva Reis
 */
public class OrganizadorIClass extends OganizadorIClassBase {

    private boolean coletarMelhoresParciais;

    /**
     * Instancia um Organizador iClass.
     * 
     * @param problema Problema ser solucionado.
     * @param solverAlgorithm Algoritmo a ser utilizado pelo organizador.
     * @param coletarMelhoresParciais Se devem ou não ser buscadas soluções
     *        parciais.
     */
    public OrganizadorIClass(ProblemaOrganizacaoTO problema,
            CspSolverEnum solverAlgorithm, boolean coletarMelhoresParciais) {
        super(problema, solverAlgorithm);
        this.coletarMelhoresParciais = coletarMelhoresParciais;
    }

    /**
     * Dado um problema, instancia um Organizador iClass Default, utilizando o
     * algoritmo {@link OrganizadorIClassBase.CspSolverEnum#MIN_CONFLICTS} e sem fazer uso da coleta
     * de resultados parciais.
     * 
     * @param problema
     */
    public OrganizadorIClass(ProblemaOrganizacaoTO problema) {
        super(problema, CspSolverEnum.MIN_CONFLICTS);
    }

    @Override
    public ResultadoOrganizacaoTO organize() {
        long startTime = System.currentTimeMillis();
        if (getProblema() == null)
            throw new IllegalArgumentException("Problema não podem ser nulo.");

        CandidatosSolucaoListener candidatosListener = new CandidatosSolucaoListener(
                10); // 10 Melhores Candidatos
        CspListener.StepCounter<TurmaVariable, IClassDomainRepresentation> stepCounter = new CspListener.StepCounter<>();
        getSolver().addCspListener(stepCounter);

        if (this.coletarMelhoresParciais)
            getSolver().addCspListener(candidatosListener);

        IClassCSP csp = new IClassCSP(getProblema());
        Optional<Assignment<TurmaVariable, IClassDomainRepresentation>> result = getSolver().solve(
                csp);
        long endTime = System.currentTimeMillis();

        ResultadoOrganizacaoTO resultadoOrganizacao = new ResultadoOrganizacaoTO();
        resultadoOrganizacao.setDescricao(csp.getProblema().getDescricao());

        MetricaTO metrica = new MetricaTO();
        metrica.setAlgorithm(getSolverAlgorithm());
        metrica.setTimeToSolve(endTime - startTime);
        metrica.setQuantidadeAtribuicoes(
                stepCounter.getResults().getInt("assignmentCount"));
        metrica.setQuantidadeInferencias(
                stepCounter.getResults().getInt("inferenceCount"));
        metrica.setPossuiSolucao(result.isPresent());
        metrica.setQuantTurmas(csp.getTurmasObrigatorias().size() + csp.getTurmasPredefinidas().size());
        metrica.setQuantProfessores(csp.getProfessores().size());
        metrica.setTamanhoDominioTurmasObrigatorias(csp.getHorarios().size());
        resultadoOrganizacao.setMetricas(metrica);

        GradeTO gradeResultado = null;
        if (result.isPresent())
            gradeResultado = toGradeTO(result.get(), csp);

        resultadoOrganizacao.setSolucao(gradeResultado);

        if (coletarMelhoresParciais) {
            resultadoOrganizacao.setMelhoresSolucoesParciais(
                    new LinkedList<>());
            while (!candidatosListener.getCandidatos().isEmpty()) {
                Assignment<TurmaVariable, IClassDomainRepresentation> assignment = candidatosListener.getCandidatos().poll().getAssignment();
                GradeTO grade = toGradeTO(assignment, csp);
                resultadoOrganizacao.getMelhoresSolucoesParciais().add(0,
                        grade);
            }
        }

        return resultadoOrganizacao;
    }

    private GradeTO toGradeTO(
            Assignment<TurmaVariable, IClassDomainRepresentation> result,
            IClassCSP csp) {
        GradeTO grade = new GradeTO(result, csp);

        List<HorarioTO> horarios = new ArrayList<>();

        for (TurmaVariable turmaVariable : result.getVariables()) {
            IClassDomainRepresentation valorTurmaVariable = result.getValue(
                    turmaVariable);

            HorarioTO horario = new HorarioTO(valorTurmaVariable.getHorario());
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

// System.out.println("Horarios: " + csp.getHorarios());
// System.out.println("Dominio: " + csp.getDominioProblema());
// System.out.println("Professores: " + csp.getProfessores());
// System.out.println("Turmas Obrigatorias: " + csp.getTurmasObrigatorias());
// System.out.println("Turmas Predefinidas: " + csp.getTurmasPredefinidas());
