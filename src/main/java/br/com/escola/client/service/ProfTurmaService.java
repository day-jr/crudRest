package br.com.escola.client.service;


import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.repository.ProfTurmaRepository;
import br.com.escola.client.repository.ProfessorRepository;
import br.com.escola.client.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProfTurmaService {

    @Autowired
    public ProfTurmaRepository profTurmaRepository;

    @Autowired
    public ProfessorRepository professorRepository;

    @Autowired
    public TurmaRepository turmaRepository;


    //Search all professors assigned to a class code
    public Optional<List<Professor>> allProfessorsAssigned(Optional<String> codigo) {
        if (codigo.isEmpty()) return null;
        var foundProf = profTurmaRepository.findByTurma(codigo.get());
        return foundProf;
    }

    public List<ProfTurma> filterByNumberOfProfessors(Optional<Long> classesMin, Optional<Long> classesMax) {
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
            var amount = profs.get().stream().count();
            amountOfClasses.put(id, amount);
        }


        Set<Long> keys = amountOfClasses.keySet();
        List<ProfTurma> professorsToShow = new ArrayList<>();

        //Filter by user preference (Just min param present)
        if (classesMin.isPresent() && classesMax.isEmpty()) {
            for (Long key : keys) {
                if (amountOfClasses.get(key) >= classesMin.get()) {
                    System.out.println(amountOfClasses.get(key)+"< amount of class "+classesMin.get()+"< classesmin");
                    for (ProfTurma element : findByProf(key).get()) {
                        professorsToShow.add(element);
                    }
                }
            }
        }

        //Filter by user preference (Just max param present)
        if (classesMax.isPresent()) {
            for (Long key : keys) {
                if (amountOfClasses.get(key) <= classesMax.get()) {
                    for (ProfTurma element : findByProf(key).get()) {
                        professorsToShow.add(element);
                    }
                }
            }
        }

        //Filter by user preference (Both params are present)
        if (classesMin.isPresent() && classesMax.isPresent()) {
            for (Long key : keys) {
                if (amountOfClasses.get(key) >= classesMin.get() && amountOfClasses.get(key) <= classesMax.get()) {
                    for (ProfTurma element : findByProf(key).get()) {
                        professorsToShow.add(element);
                    }
                }
            }
        }


        return professorsToShow;
    }


    public void saveComposite(String cpf, String codigo) {
        ProfTurma profTurma = new ProfTurma();
        var professor = professorRepository.findByCpf(cpf);
        var turma = turmaRepository.findByCodigo(codigo);

        if (turma.isEmpty()|| professor.isEmpty())return;

        var idTurma = turma.get().getId();
        var idProf = professor.get().getId();
        profTurmaRepository.saveComposite(idProf, idTurma);
    }


    //////////////////////////////////////////
    public List<ProfTurma> getAll() {
        return profTurmaRepository.findAll();

    }


    public Optional<ProfTurma> find(String cpf, String codigo) {

        return Optional.ofNullable(profTurmaRepository.find(cpf, codigo));
    }

    public Optional<List<ProfTurma>> findByProf(Long cpf) {
        return profTurmaRepository.findByProf(cpf);
    }

    public void deleteProfTurma(Long profId, Long turmaId) {
        profTurmaRepository.deleteTurma(profId, turmaId);
    }
}
