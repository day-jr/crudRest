package br.com.escola.client.service;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.ProfTurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class ProfTurmaService {

    @Autowired
    public ProfTurmaRepository profTurmaRepository;

    private EntityManager em;
    public ProfTurmaService (EntityManager em){
        this.em = em;
    }

    public void saveComposite(ProfTurma profTurma){
        String query="select P from Professor as P where P.cpf = :cpf";

        var a= em.createQuery(query,Professor.class);
        a.setParameter("cpf",profTurma.getCpf().getCpf());
        var professor=  a.getResultList();


        query="select T from Turma as T where T.codigo = :codigo";

        var b = em.createQuery(query, Turma.class);
        b.setParameter("codigo",profTurma.getCodigo().getCodigo());
        var turma=  b.getResultList();



        var idTurma = turma.get(0).getId();
        var idProf = professor.get(0).getId();



        profTurmaRepository.saveComposite(idProf,idTurma);

    }

    //////////////////////////////////////////TO DO
    public List<ProfTurma> getProfTurma(){


        return profTurmaRepository.findAll();
    }



    public Optional<ProfTurma> findProfTurma(Long id){
        return profTurmaRepository.findById(id);
    }
    ////////////////////////////////////////////
//
//
//    public void DeleteProfTurmaById(String id){
//        profTurmaRepository.deleteById(id);
//    }
}
