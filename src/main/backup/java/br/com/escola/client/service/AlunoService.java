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

    public Aluno Save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> GetAluno() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> FindAluno(Long id) {
        return alunoRepository.findById(id);
    }

    public void DeleteById(Long id) {
        alunoRepository.deleteById(id);
    }


}
