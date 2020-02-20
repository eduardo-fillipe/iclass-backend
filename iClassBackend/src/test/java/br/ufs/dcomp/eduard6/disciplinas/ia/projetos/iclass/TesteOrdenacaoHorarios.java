package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO.HorarioSequencia;

public class TesteOrdenacaoHorarios {
	public static void main(String[] args) {
		HorarioTO horarioED1 = new HorarioTO("5T34", DayOfWeek.THURSDAY, HorarioSequencia.DOIS, null, (short)3);
		HorarioTO horarioED2 = new HorarioTO("5T12", DayOfWeek.THURSDAY, HorarioSequencia.DOIS, null, (short)1);
		HorarioTO horarioED3 = new HorarioTO("5T56", DayOfWeek.THURSDAY, HorarioSequencia.DOIS, null, (short)5);
		HorarioTO horarioED3_cpy = new HorarioTO("5T56", DayOfWeek.THURSDAY, HorarioSequencia.DOIS, null, (short)5);

		HorarioTO horarioCP1 = new HorarioTO("2T12", DayOfWeek.MONDAY, HorarioSequencia.DOIS, null, (short)1);
		HorarioTO horarioCP2 = new HorarioTO("4T12", DayOfWeek.WEDNESDAY, HorarioSequencia.DOIS, null, (short)1);
		
		ArrayList<HorarioTO> arrayList = new ArrayList<>();
		arrayList.add(horarioED1);
		arrayList.add(horarioED2);
		arrayList.add(horarioCP1);
		arrayList.add(horarioCP2);

		System.out.println(HorarioTO.isConsecutivos(new ArrayList<HorarioTO>(Arrays.asList(horarioED3_cpy, horarioED3))));
		System.out.println(horarioED3.equals(horarioED3_cpy));

		System.out.println(HorarioTO.fromCodigo("2T1234").transformarEmSequenciaDois());
	}
}
