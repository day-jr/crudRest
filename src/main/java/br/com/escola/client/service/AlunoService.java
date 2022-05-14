package br.com.escola.client.service;


import br.com.escola.client.entity.Aluno;
import br.com.escola.client.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlunoService {

    @Autowired
    public AlunoRepository alunoRepository;

    public Aluno save(Aluno aluno){
        return alunoRepository.save(aluno);
    }

    public List<Aluno> getAluno(){
        return alunoRepository.findAll();
    }

    public Aluno findByMatricula(String matricula){
        return alunoRepository.findByMatricula(matricula);
    }

    public void deleteAlunoByMatricula(String matricula){
        alunoRepository.deleteByMatricula(matricula);
    }


}
