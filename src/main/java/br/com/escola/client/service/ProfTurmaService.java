package br.com.escola.client.service;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.ProfTurmaRepository;
import br.com.escola.client.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;

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


    //Search all classes assigned to a CPF
    public ResponseEntity allClassesAssigned(Optional<String> cpf) {
        List<Turma> foundTurma = new ArrayList<>();
        if (cpf.isPresent()) foundTurma = getTurmas(cpf.get());

        if (foundTurma.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(foundTurma, HttpStatus.OK);
    }



    //Search all professors assigned to a class code
    public ResponseEntity allProfessorsAssigned(Optional<String> codigo) {
        List<Professor> foundProf = new ArrayList<>();
        if (codigo.isPresent())foundProf = getProfessores(codigo.get());
        if (foundProf.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(foundProf, HttpStatus.OK);
    }

    public ResponseEntity filterByNumberOfProfessors(Optional<Integer> classesMin, Optional<Integer> classesMax) {
        var allClassesAssigned = getAll();
        Set<Long> allProfessorsAssigned = new HashSet<>();
        final Map<Long, Long> amountOfClasses = new HashMap<>();

        //Assign professors
        for (ProfTurma entidade : allClassesAssigned) {
            allProfessorsAssigned.add(entidade.getProfessor().getId());
        }

        //Assing amount of classes each one have
        for (Long id : allProfessorsAssigned) {
            var profs = findByProf(id);
            var amount = profs.stream().count();
            amountOfClasses.put(id, amount);
        }

        Set<Long> keys = amountOfClasses.keySet();
        List<ProfTurma> professorsToShow = new ArrayList<>();

        //Filter by user preference (Just min param present)
        if (classesMin.isPresent() && classesMax.isEmpty()) {
            for (Long key : keys) {
                if (amountOfClasses.get(key) >= classesMin.get()) {
                    for (ProfTurma element : findByProf(key)) {
                        professorsToShow.add(element);
                    }
                }
            }
        }

        //Filter by user preference (Both params are present)
        if (classesMin.isPresent() && classesMax.isPresent()) {
            for (Long key : keys) {
                if (amountOfClasses.get(key) >= classesMin.get() && amountOfClasses.get(key) <= classesMax.get()) {
                    for (ProfTurma element : findByProf(key)) {
                        professorsToShow.add(element);
                    }
                }
            }
        }

        if (professorsToShow.isEmpty()) return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(professorsToShow, HttpStatus.OK);
    }


    public void saveComposite(String cpf, String codigo) {
        ProfTurma profTurma = new ProfTurma();

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

    public List<ProfTurma> findByProf(Long idProf) {
        return profTurmaRepository.findByProf(idProf);
    }

    public void deleteProfTurma(Long profId, Long turmaId) {
        profTurmaRepository.deleteTurma(profId, turmaId);
    }
}
