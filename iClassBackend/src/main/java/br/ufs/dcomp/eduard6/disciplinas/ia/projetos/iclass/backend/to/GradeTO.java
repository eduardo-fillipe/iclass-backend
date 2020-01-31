package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.backend.to;

public class GradeTO extends TransferObjectBase{
	private HorarioTO[][] grade;

	public GradeTO(short cargaHorariaMaximaDiaria) {
		super();
		grade = new HorarioTO[5][cargaHorariaMaximaDiaria];
	}
	
	public boolean addTurmaHorario(String codigoHorario, TurmaTO turma) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public boolean removerTurmaHorario(String codigoHorario, TurmaTO turma) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public HorarioTO getHorarioByCodigo(String codigo) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}
