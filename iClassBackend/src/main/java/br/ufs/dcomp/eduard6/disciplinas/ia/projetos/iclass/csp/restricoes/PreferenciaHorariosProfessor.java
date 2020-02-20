package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.restricoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.search.csp.Assignment;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.IClassCSP;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.IClassDomainRepresentation;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.csp.variables.TurmaVariable;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;

public class PreferenciaHorariosProfessor extends IClassRestricaoBase {
    private TurmaVariable turma;

    private List<TurmaVariable> turmas;

    public PreferenciaHorariosProfessor(IClassCSP iClassCSP,
            TurmaVariable turma) {
        super(iClassCSP);
        this.turma = turma;
        this.turmas = new ArrayList<>(Arrays.asList(this.turma));
    }

    @Override
    public List<TurmaVariable> getScope() {
        return turmas;
    }

    @Override
    public boolean isSatisfiedWith(
            Assignment<TurmaVariable, IClassDomainRepresentation> assignment) {
        for (TurmaVariable turmaVariable : getScope()) {
            IClassDomainRepresentation value = assignment.getValue(
                    turmaVariable);
            if (value != null && value.getProfessor() != null
                    && (value.getProfessor().getPreferenciaHorarios() != null
                            && !value.getProfessor().getPreferenciaHorarios().isEmpty())) {
                HorarioTO horario = value.getHorario();
                if (!value.getProfessor().getPreferenciaHorarios().contains(
                        horario)) {
                    return false;
                }
            }
        }
        return true;
    }
}
