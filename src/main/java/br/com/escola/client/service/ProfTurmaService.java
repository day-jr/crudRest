package br.com.escola.client.service;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.ProfTurmaRepository;
import br.com.escola.client.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.*;


@Service
public class ProfTurmaService {

    @Autowired
    public ProfTurmaRepository profTurmaRepository;

    @Autowired
    public ProfessorRepository professorRepository;

    private EntityManager em;

    public ProfTurmaService(EntityManager em) {
        this.em = em;
    }


    public void saveComposite(ProfTurma profTurma) {
        String query = "select P from Professor as P where P.cpf = :cpf";

        var a = em.createQuery(query, Professor.class);
        a.setParameter("cpf", profTurma.getProfessor().getCpf());
        var professor = a.getSingleResult();


        query = "select T from Turma as T where T.codigo = :codigo";

        var b = em.createQuery(query, Turma.class);
        b.setParameter("codigo", profTurma.getTurma().getCodigo());
        var turma = b.getSingleResult();


        var idTurma = turma.getId();
        var idProf = professor.getId();


        profTurmaRepository.saveComposite(idProf, idTurma);
    }


    //////////////////////////////////////////
    public List<ProfTurma> getAll() {
        return profTurmaRepository.findAll();

//        List<String> elementos = new ArrayList<>();
//
//
//        for (ProfTurma pt : profTurma) {
//            switch (elemento) {
//                case "nome":
//                    elementos.add(pt.getProfessor().getNome());
//                    break;
//
//
//                case "professor":
//                    elementos.add(pt.getProfessor().getCpf());
//                    break;
//
//
//                case "turma":
//                    elementos.add(pt.getTurma().getCodigo());
//                    break;
//
//            }
//        }
//
//
//        return elementos;

    }


    public List<Professor> getProfessores(String value) {

        String query = "SELECT professor FROM ProfTurma as p " +
                "WHERE  p.turma.codigo  = " + value;

        return em.createQuery(query, Professor.class).getResultList();
    }


    public List<Turma> getTurmas(String value) {

        String query = "SELECT turma FROM ProfTurma as t " +
                "WHERE  t.professor.cpf =" + value;
        return em.createQuery(query, Turma.class).getResultList();
    }


    public void modify(Long idProf, Long idTurma, Long value) {

        profTurmaRepository.modify(idProf, idTurma, value);
    }


    public Optional<ProfTurma> find(String cpf, String codigo) {

        return Optional.ofNullable(profTurmaRepository.find(cpf, codigo));

    }

    public Optional<ProfTurma> findById(Long idProf, Long idTurma) {
        return Optional.ofNullable(profTurmaRepository.findById(idProf, idTurma));
    }

    public void deleteProfTurma(Long profId, Long turmaId) {
        profTurmaRepository.deleteTurma(profId, turmaId);
    }
}
