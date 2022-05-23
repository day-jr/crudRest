package br.com.escola.client.service;


import br.com.escola.client.entity.*;
import br.com.escola.client.repository.AlunoRepository;
import br.com.escola.client.repository.AlunoTurmaRepository;
import br.com.escola.client.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
public class AlunoTurmaService {

    @Autowired
    public AlunoTurmaRepository alunoTurmaRepository;
    public AlunoRepository alunoRepository;
    public TurmaRepository turmaRepository;


    public AlunoTurma save(AlunoTurma alunoTurma) {
        return alunoTurmaRepository.save(alunoTurma);
    }

    //Search all classes assigned to a registration
    public Optional<List<AlunoTurma>> allClassesAssignedToRegistration(Optional<String> matricula) {
        if(matricula.isEmpty())return null;
        var turmaFound =
                alunoTurmaRepository.getAllAlunoTurmaByMatricula(matricula.get());

        return turmaFound;
    }

    public void saveComposite(String matricula, String codigo) {
        AlunoTurma alunoTurma = new AlunoTurma();
        var aluno = alunoRepository.findByMatricula(matricula);
        var turma = turmaRepository.findByCodigo(codigo);

        if(aluno.isEmpty()|| turma.isEmpty()) return;
        alunoTurma.setTurma(turma.get());
        alunoTurma.setAluno(aluno.get());
        alunoTurmaRepository.save(alunoTurma);
    }

    public List<AlunoTurma> filterByNumberOfStudents(Optional<Long> classesMin, Optional<Long> classesMax) {
        var allClassesAssigned = alunoTurmaRepository.findAll();
        Set<Long> allStudentsIdsAssigned = new HashSet<>();
        final Map<Long, Long> amountOfClasses = new HashMap<>();

        //Ids of all assign students
        for (AlunoTurma entidade : allClassesAssigned) {
            allStudentsIdsAssigned.add(entidade.getAluno().getId());
        }

        //Amount of assigned classes each one have
        for (Long id : allStudentsIdsAssigned) {
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



        return studentsToShow;
    }

    public List<AlunoTurma> getAll() {
        return alunoTurmaRepository.findAll();
    }

    public void deleteAlunoTurma(Long alunoId, Long turmaId) {
        alunoTurmaRepository.deleteAlunoTurma(alunoId, turmaId);
    }

    public AlunoTurma findById(Long idAluno, Long idTurma) {
        return alunoTurmaRepository.findByIds(idAluno, idTurma);
    }

    public Optional<AlunoTurma> find(String matricula, String codigo) {

        return Optional.ofNullable(alunoTurmaRepository.findAlunoTurmaByMatriculaCodigo(matricula, codigo));

    }

}
