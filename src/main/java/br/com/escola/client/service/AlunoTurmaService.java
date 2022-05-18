package br.com.escola.client.service;


import br.com.escola.client.entity.*;
import br.com.escola.client.repository.AlunoTurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class AlunoTurmaService {

    @Autowired
    public AlunoTurmaRepository alunoTurmaRepository;

    private EntityManager em;

    public AlunoTurmaService(EntityManager em) {
        this.em = em;
    }

    public AlunoTurma save(AlunoTurma alunoTurma) {
        return alunoTurmaRepository.save(alunoTurma);
    }

    public void saveComposite(AlunoTurma alunoTurma) {
        String query = "select A from Aluno as A where A.matricula = :matricula";

        var a = em.createQuery(query, Aluno.class);
        a.setParameter("matricula", alunoTurma.getAluno().getMatricula());
        var aluno = a.getResultList();


        query = "select T from Turma as T where T.codigo = :codigo";

        var b = em.createQuery(query, Turma.class);
        b.setParameter("codigo", alunoTurma.getTurma().getCodigo());
        var turma = b.getResultList();


        var idTurma = turma.get(0).getId();
        var idAluno = aluno.get(0).getId();


        alunoTurmaRepository.saveComposite(idAluno, idTurma);
    }

    public List<String> getAlunoTurmaBy(String elemento) {
        var AlunoTurma = alunoTurmaRepository.findAll();

        List<String> elementos = new ArrayList<>();


        for (AlunoTurma at : AlunoTurma) {
            switch (elemento) {
                case "nome":
                    elementos.add(at.getAluno().getNome());
                    break;


                case "matricula":
                    elementos.add(at.getAluno().getMatricula());
                    break;


                case "turma":
                    elementos.add(at.getTurma().getCodigo());
                    break;

            }
        }


        return elementos;
    }

    public ResponseEntity filterByNumberOfStudents(Optional<Integer> classesMin, Optional<Integer> classesMax) {
        var allClassesAssigned = alunoTurmaRepository.findAll();
        Set<Long> allStudensAssigned = new HashSet<>();
        final Map<Long, Long> amountOfClasses = new HashMap<>();

        //Ids of all assign students
        for (AlunoTurma entidade : allClassesAssigned) {
            allStudensAssigned.add(entidade.getAluno().getId());
        }

        //Amount of assigned classes each one have
        for (Long id : allStudensAssigned) {
            var students = alunoTurmaRepository.getAllById(id);
            var amount = students.stream().count();
            amountOfClasses.put(id, amount);
        }

        Set<Long> keys = amountOfClasses.keySet();
        List<AlunoTurma> studentsToShow = new ArrayList<>();

        //Filter by user preference (Just min param present)
        if (classesMin.isPresent() && classesMax.isEmpty()) {
            for (Long key : keys) {
                if (amountOfClasses.get(key) >= classesMin.get()) {
                    for (AlunoTurma element : alunoTurmaRepository.getAllById(key)) {
                        studentsToShow.add(element);
                    }
                }
            }
        }

        //Filter by user preference (Just max param present)
        if (classesMax.isPresent()) {
            for (Long key : keys) {
                if (amountOfClasses.get(key) <= classesMax.get()) {
                    for (AlunoTurma element : alunoTurmaRepository.getAllById(key)) {
                        studentsToShow.add(element);
                    }
                }
            }
        }

        //Filter by user preference (Both params are present)
        if (classesMin.isPresent() && classesMax.isPresent()) {
            for (Long key : keys) {
                if (amountOfClasses.get(key) >= classesMin.get() && amountOfClasses.get(key) <= classesMax.get()) {
                    for (AlunoTurma element : alunoTurmaRepository.getAllById(key)) {
                        studentsToShow.add(element);
                    }
                }
            }
        }



        if (studentsToShow.isEmpty()) return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(studentsToShow, HttpStatus.OK);
    }

    public AlunoTurma getAlunoTurma(String matricula) {

        return alunoTurmaRepository.getByMatricula(matricula);
    }

    public List<AlunoTurma> getAll() {
        return alunoTurmaRepository.findAll();}



    public List<Turma> getTurmas(String value) {
        String query = "SELECT turma FROM AlunoTurma as t " +
                "WHERE  t.aluno.matricula =" + value;


        return  em.createQuery(query, Turma.class).getResultList();
    }

    public List<Aluno> getAlunos(String value) {
        String query =  "SELECT aluno FROM AlunoTurma as a " +
                "WHERE  a.turma.codigo  = " + value;

        return  em.createQuery(query, Aluno.class).getResultList();
    }

    public void deleteAlunoTurma(Long alunoId, Long turmaId) {
        alunoTurmaRepository.deleteTurma(alunoId, turmaId);
    }

    public AlunoTurma findById(Long idAluno, Long idTurma) {
        return alunoTurmaRepository.findById(idAluno, idTurma);
    }

    public void modify(Long idAluno, Long idTurma, Long value) {

        alunoTurmaRepository.modify(idAluno, idTurma, value);
    }


    public Optional<AlunoTurma> find(String matricula, String codigo) {

        return Optional.ofNullable(alunoTurmaRepository.find(matricula, codigo));

    }

}
