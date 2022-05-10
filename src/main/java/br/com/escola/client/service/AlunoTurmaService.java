package br.com.escola.client.service;


import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.repository.AlunoTurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoTurmaService {

    @Autowired
    public AlunoTurmaRepository alunoTurmaRepository;

    public AlunoTurma Save(AlunoTurma alunoTurma){
        return alunoTurmaRepository.save(alunoTurma);
    }

    public List<AlunoTurma> GetAlunoTurma(){
        return alunoTurmaRepository.findAll();
    }

    public Optional<AlunoTurma> FindAlunoTurma(Long id){
        return alunoTurmaRepository.findById(id);
    }

    public void DeleteAlunoTurmaById(Long id){
        alunoTurmaRepository.deleteById(id);
    }

}
