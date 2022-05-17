package br.com.escola.client.service;


import br.com.escola.client.entity.Aluno;
import br.com.escola.client.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AlunoService {

    @Autowired
    public AlunoRepository alunoRepository;

    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> getAluno() {
        return alunoRepository.findAll();
    }

    public void deleteDependency(Long matricula) {
        alunoRepository.deleteDependency(matricula);
    }

    public Optional<Aluno> findByMatricula(String matricula) {
        return Optional.ofNullable(alunoRepository.findByMatricula(matricula));
    }

    public void deleteAlunoByMatricula(String matricula) {
        alunoRepository.deleteByMatricula(matricula);
    }


}
