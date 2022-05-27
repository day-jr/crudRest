package br.com.escola.client.service;


import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.AlunoRepository;
import br.com.escola.client.repository.AlunoTurmaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AlunoService {

    @Autowired
    public AlunoRepository alunoRepository;

    @Autowired
    public AlunoTurmaRepository alunoTurmaRepository;

    //Search all students assigned to a class code
    public Optional<List<Aluno>> allStudentsAssigned(String codigo) {
        Optional<List<Aluno>> alunosFound;
        alunosFound = alunoTurmaRepository.getAllAssignmentsOfStudentByClassCode(codigo);
        return alunosFound;
    }


    public Aluno save(Aluno aluno) {
        //aluno.setId(null);
        return alunoRepository.save(aluno);
    }

    public List<Aluno> getAlunos() {
        return alunoRepository.findAll();
    }

    public void deleteDependency(Long id) {
        alunoTurmaRepository.deleteDependency(id);
    }

    public Optional<Aluno> findByMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula);
    }

    public void deleteAlunoByMatricula(String matricula) {
        alunoRepository.deleteByMatricula(matricula);
    }

    @Autowired
    ModelMapper modelMapper;

    public Optional<Aluno> update(Aluno incomingBody, String matricula) {
        var optionalAluno = findByMatricula(matricula);

        if (optionalAluno.isEmpty()) {
            return Optional.empty();
        }

        Long originalId= optionalAluno.get().getId();

        if (incomingBody.getId()!=null){
            System.out.println("Should not try to modify Id. \nThis attribute was ignored.");

        }


        modelMapper.map(incomingBody, optionalAluno.get());
        //else, parse actual Id to requisition to ensure it will not be modified
        optionalAluno.get().setId(originalId);


        return Optional.of(alunoRepository.save(optionalAluno.get()));
    }

}
