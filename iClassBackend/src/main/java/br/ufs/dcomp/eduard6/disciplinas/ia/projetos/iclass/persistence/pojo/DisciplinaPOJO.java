package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;

/**
 * Representa uma disciplina no banco de dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class DisciplinaPOJO {
    private String nome;

    private String codigo;

    private short cargaHoraria;

    private boolean precisaLaboratorio;

    public DisciplinaPOJO() {
        super();
    }

    public DisciplinaPOJO(String nome, String codigo, short cargaHoraria,
            boolean precisaLaboratorio) {
        super();
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.precisaLaboratorio = precisaLaboratorio;
    }

    public DisciplinaPOJO(DisciplinaTO to) {
        super();
        this.nome = to.getNome();
        this.codigo = to.getCodigo();
        this.cargaHoraria = to.getCargaHoraria();
        this.precisaLaboratorio = to.isPrecisaLaboratorio();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public short getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(short cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public boolean isPrecisaLaboratorio() {
        return precisaLaboratorio;
    }

    public void setPrecisaLaboratorio(boolean precisaLaboratorio) {
        this.precisaLaboratorio = precisaLaboratorio;
    }
}
