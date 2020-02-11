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
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.persistence.pojo.ProfessorPOJO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.ProfessorTO;
import br.ufs.dcomp.eduard6.disciplinas.ia.projetos.iclass.to.DisciplinaTO;

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

		ProfessorTO p = new ProfessorTO();
		p.setNome(pp.getNome());
		p.setMatricula(pp.getMatricula());
		p.setCargaHorariaSemanal(pp.getCargaHorariaSemanal());
		p.setPreferencias(new ArrayList<DisciplinaTO>());
		for (String pr : pp.getPreferencias()) {
			DisciplinaTO d = new DisciplinaTO();
			d.setCodigo(pr);
			p.getPreferencias().add(d);
		}

		return p;
	}

	public ProfessorTO getProfessorCompleto(String matricula) {
		Document doc = getDatabase()
				.getCollection(
						MongoDBConnectionManager.getInstance().getPropertie("iclass.mongodb.collectionname.professor"))
				.aggregate(Arrays.asList(match(eq("matricula", matricula)), limit(1), unwind("$preferencias"),
						lookup("disciplina", "preferencias", "codigo", "preferencias"), unwind("$preferencias"),
						group("$_id", first("matricula", "$matricula"), first("nome", "$nome"),
								first("cargaHorariaSemanal", "$cargaHorariaSemanal"),
								push("preferencias", "$preferencias"))))
				.first();

		ProfessorTO professorTO = new ProfessorTO();

		professorTO.setNome(doc.getString("nome"));
		professorTO.setMatricula(doc.getString("matricula"));
		professorTO.setCargaHorariaSemanal(doc.get("cargaHorariaSemanal", Integer.class).shortValue());
		professorTO.setPreferencias(new ArrayList<DisciplinaTO>());

		@SuppressWarnings("unchecked")
		ArrayList<Document> preferencias = doc.get("preferencias", ArrayList.class);

		if (preferencias != null) {
			preferencias.forEach(disciplinaDoc -> {
				DisciplinaTO d = new DisciplinaTO();
				d.setCargaHoraria(disciplinaDoc.get("cargaHoraria", Integer.class).shortValue());
				d.setCodigo(disciplinaDoc.getString("codigo"));
				d.setNome(disciplinaDoc.getString("nome"));
				professorTO.getPreferencias().add(d);
			});
		}

		return professorTO;
	}

	public List<ProfessorTO> getProfessores() {
		List<ProfessorTO> p = new ArrayList<ProfessorTO>();
		ArrayList<DisciplinaTO> ds = new ArrayList<DisciplinaTO>();

		getCollection().find().iterator().forEachRemaining(o -> {

			for (String pr : o.getPreferencias()) {
				DisciplinaTO d = new DisciplinaTO();
				d.setCodigo(pr);
				ds.add(d);
			}

			p.add(new ProfessorTO(o.getNome(), o.getMatricula(), ds, o.getCargaHorariaSemanal()));
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
