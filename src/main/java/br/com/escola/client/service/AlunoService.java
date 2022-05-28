package br.com.escola.client.service;

import br.com.escola.client.dto.request.dtoGetAluno;
import br.com.escola.client.dto.response.dtoPostAluno;
import br.com.escola.client.entity.Aluno;
import br.com.escola.client.repository.AlunoRepository;
import br.com.escola.client.repository.AlunoTurmaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        alunosFound = alunoTurmaRepository.getAllStudentAssignmentsByClassCode(codigo);
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

    public Optional<Aluno> update(dtoPostAluno incomingBody, String matricula) {
        var optionalAluno = findByMatricula(matricula);
        var parsedAluno = new dtoPostAluno();

        if (optionalAluno.isEmpty()) {
            return Optional.empty();
        }
        var alunoDTO = optionalAluno.get();

        modelMapper.map(incomingBody,alunoDTO);

        return Optional.of(alunoRepository.save(alunoDTO));
    }


}
