package br.com.escola.client.service;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.repository.ProfTurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfTurmaService {

    @Autowired
    public ProfTurmaRepository profTurmaRepository;

    public ProfTurma Save(ProfTurma profTurma){
        return profTurmaRepository.save(profTurma);
    }

    public List<ProfTurma> GetProfTurma(){
        return profTurmaRepository.findAll();
    }

    public Optional<ProfTurma> FindProfTurma(Long id){
        return profTurmaRepository.findById(id);
    }

    public void DeleteProfTurmaById(Long id){
        profTurmaRepository.deleteById(id);
    }
}
