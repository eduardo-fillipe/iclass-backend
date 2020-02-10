package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.DisciplinaPOJO;

/**
 * Classe que representa da Disciplina como TO.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class DisciplinaTO extends TransferObjectBase{
	private String nome;
	private String codigo;
	private short cargaHoraria;
	private boolean precisaLaboratorio;
	
	public DisciplinaTO() {
		super();
	}
	
	public DisciplinaTO(String nome, String codigo, short cargaHoraria, boolean precisaLaboratorio) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.cargaHoraria = cargaHoraria;
		this.precisaLaboratorio = precisaLaboratorio;
	}
	public DisciplinaTO(DisciplinaPOJO pojo) {
		this.nome = pojo.getNome();
		this.codigo = pojo.getCodigo();
		this.cargaHoraria = pojo.getCargaHoraria();
		this.precisaLaboratorio = pojo.isPrecisaLaboratorio();
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

    @Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			String stringObj = (String) obj;
			return this.codigo.toLowerCase().equals(stringObj.toLowerCase());
		}
		return false;	
	}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DisciplinaTO [nome=");
        builder.append(nome);
        builder.append(", codigo=");
        builder.append(codigo);
        builder.append(", cargaHoraria=");
        builder.append(cargaHoraria);
        builder.append(", precisaLaboratorio=");
        builder.append(precisaLaboratorio);
        builder.append("]");
        return builder.toString();
    }
}
