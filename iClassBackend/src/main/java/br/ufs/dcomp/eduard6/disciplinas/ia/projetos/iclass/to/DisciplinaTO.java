package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to;
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
	
	
	public DisciplinaTO() {
		super();
	}
	
	public DisciplinaTO(String nome, String codigo, short cargaHoraria) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.cargaHoraria = cargaHoraria;
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			String stringObj = (String) obj;
			return this.codigo.toLowerCase().equals(stringObj.toLowerCase());
		}
		return false;	
	}
}
