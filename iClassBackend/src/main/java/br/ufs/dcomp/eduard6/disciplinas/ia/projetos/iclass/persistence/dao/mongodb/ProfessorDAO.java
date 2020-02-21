package br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.dao.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.lookup;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Sorts.ascending;

import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.MongoDBConnectionManager;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.ProfessorCompletoPOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.ProfessorPOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.HorarioTO;

/**
 * Classe responsável pelas interações relacionadas a professores com o banco de
 * dados.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
public class ProfessorDAO extends AbstractMongoDao<ProfessorPOJO> {

	private static ProfessorDAO INSTANCE = new ProfessorDAO();

	private ProfessorDAO() {
		super(MongoDBConnectionManager.getInstance().getPropertie("iclass.mongodb.collectionname.professor"),
				ProfessorPOJO.class);
	}

	public static ProfessorDAO getInstance() {
		return INSTANCE;
	}

	/**
	 * Altera um professor.
	 * 
	 * @param professor
	 */
	public void alterarProfessor(ProfessorTO professor) {
		throw new UnsupportedOperationException("Não implementado ainda");
	}

	/**
	 * Retorna um professor da a matrícula. As preferências desse professor possuem
	 * apenas o código. Para que seja retornada a disciplina completa use:
	 * {@link #getProfessorCompleto(String)}
	 * 
	 * @param matricula Matricula do professor
	 * @return Um professorTO sem os a disciplina preenchida, apenas o código da
	 *         mesma.
	 */
	public ProfessorTO getProfessor(String matricula) {

		ProfessorPOJO pp = getCollection().find(new Document("matricula", matricula)).first();

		if (pp == null)
			return null;
		
		ProfessorTO p = new ProfessorTO();
		p.setNome(pp.getNome());
		p.setMatricula(pp.getMatricula());
		p.setCargaHorariaSemanal(pp.getCargaHorariaSemanal());
		p.setPreferencias(new ArrayList<DisciplinaTO>());
		p.setPreferenciaHorarios(new ArrayList<HorarioTO>());

		for (String pr : pp.getPreferencias()) {
			DisciplinaTO d = new DisciplinaTO();
			d.setCodigo(pr);
			p.getPreferencias().add(d);
		}

		if (pp.getPreferenciaHorarios() != null) {
			pp.getPreferenciaHorarios().forEach(h -> {
				p.getPreferenciaHorarios().add(new HorarioTO(h));
			});
		}

		return p;
	}

	public ProfessorTO getProfessorCompleto(String matricula) {
		ProfessorCompletoPOJO doc = getDatabase()
				.getCollection(
						MongoDBConnectionManager.getInstance().getPropertie("iclass.mongodb.collectionname.professor"),
						ProfessorCompletoPOJO.class)
				.withCodecRegistry(getCodec())
				.aggregate(Arrays.asList(match(eq("matricula", matricula)), limit(1), unwind("$preferencias"),
						lookup("disciplina", "preferencias", "codigo", "preferencias"), unwind("$preferencias"),
						group("$_id", first("matricula", "$matricula"), first("nome", "$nome"),
								first("cargaHorariaSemanal", "$cargaHorariaSemanal"),
								push("preferencias", "$preferencias"),
								first("preferenciaHorarios", "$preferenciaHorarios"))))
				.first();
		ProfessorTO professorTO = null;
		if (doc != null) {
			professorTO = new ProfessorTO(doc);
		}

		return professorTO;
	}

	public List<ProfessorTO> getProfessores() {
		List<ProfessorTO> p = new ArrayList<ProfessorTO>();

		getDatabase()
				.getCollection(
						MongoDBConnectionManager.getInstance().getPropertie("iclass.mongodb.collectionname.professor"),
						ProfessorCompletoPOJO.class)
				.withCodecRegistry(getCodec())
				.aggregate(Arrays.asList(unwind("$preferencias"),
						lookup("disciplina", "preferencias", "codigo", "preferencias"), unwind("$preferencias"),
						group("$_id", first("nome", "$nome"), first("cargaHorariaSemanal", "$cargaHorariaSemanal"),
								first("matricula", "$matricula"), push("preferencias", "$preferencias"),
								first("preferenciaHorarios", "$preferenciaHorarios"))))
				.iterator().forEachRemaining(professorCompleto -> {
					p.add(new ProfessorTO(professorCompleto));
				});

		return p;
	}

	public void inserirProfessor(ProfessorTO professor) {
		getCollection().insertOne(ProfessorPOJO.fromTO(professor));
	}

	public void removerProfessor(String matricula) {
		getCollection().deleteOne(new Document("matricula", matricula));
	}

	/**
	 * Retorna uma lista de professores que contem um determinado nome.\
	 * 
	 * @param nome   Nome do professor a ser buscado.
	 * @param limite Limite de resultados.
	 * @return Lista de professores que possuem o nome buscado.
	 */
	public List<ProfessorTO> getProfessoresPorNome(String nome, int limite) {
		List<ProfessorTO> professorTOs = new ArrayList<>();

		getCollection()
				.aggregate(Arrays.asList(match(regex("nome", nome, "i")), sort(ascending("nome")), limit(limite)))
				.iterator().forEachRemaining(p -> {
					professorTOs.add(new ProfessorTO(p));
				});

		return professorTOs;
	}
}
