package br.com.escola.client.service;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.ProfTurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfTurmaService {

    @Autowired
    public ProfTurmaRepository profTurmaRepository;




    public void save(ProfTurma profTurma){

        profTurmaRepository.save(profTurma);
    }

    //////////////////////////////////////////TO DO
//    public List<ProfTurma> getProfTurma(){
//        return profTurmaRepository.findAll();
//    }
//
//    public Optional<ProfTurma> findProfTurma(String id){
//        return profTurmaRepository.findById(id);
//    }
//    ////////////////////////////////////////////
//
//
//    public void DeleteProfTurmaById(String id){
//        profTurmaRepository.deleteById(id);
//    }
}
