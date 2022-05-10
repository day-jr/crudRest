package br.com.escola.client.service;


import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    public TurmaRepository turmaRepository;

    public Turma Save(Turma turma){
        return turmaRepository.save(turma);
    }

    public List<Turma> GetTurma(){
        return turmaRepository.findAll();
    }

    public Optional<Turma> FindTurma(Long id){
        return turmaRepository.findById(id);
    }

    public void DeleteById(Long id){
        turmaRepository.deleteById(id);
    }
    }
