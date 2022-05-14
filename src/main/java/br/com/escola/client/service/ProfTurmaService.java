package br.com.escola.client.service;


import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.ProfTurmaRepository;
import br.com.escola.client.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import java.util.*;


@Service
public class ProfTurmaService {

    @Autowired
    public ProfTurmaRepository profTurmaRepository;

    @Autowired
    public ProfessorRepository professorRepository;

    private EntityManager em;
    public ProfTurmaService (EntityManager em){
        this.em = em;
    }



    public void saveComposite(ProfTurma profTurma){
        String query="select P from Professor as P where P.cpf = :cpf";

        var a= em.createQuery(query,Professor.class);
        a.setParameter("cpf",profTurma.getProfessor().getCpf());
        var professor=  a.getResultList();


        query="select T from Turma as T where T.codigo = :codigo";

        var b = em.createQuery(query, Turma.class);
        b.setParameter("codigo",profTurma.getTurma().getCodigo());
        var turma=  b.getResultList();



        var idTurma = turma.get(0).getId();
        var idProf = professor.get(0).getId();



        profTurmaRepository.saveComposite(idProf,idTurma);
    }




    //////////////////////////////////////////
    public List<String> getProfTurma(String elemento){
        var profTurma = profTurmaRepository.findAll();

        List<String> elementos = new ArrayList<>();



        for (ProfTurma pt:profTurma) {
            switch(elemento){
                case "nome":
                    elementos.add(pt.getProfessor().getNome());
                    break;


                case "professor":
                    elementos.add(pt.getProfessor().getCpf());
                    break;


                case "turma":
                    elementos.add(pt.getTurma().getCodigo());
                    break;

            }
        }


        return elementos;
    }




    public Optional<List<String>> findProfTurma(String elemento, String atributo, String value){

        String query;

        if(!atributo.equals("codigo")) {

            if (elemento.equals("codigo")) {

                query = "SELECT turma." + elemento + " FROM ProfTurma as t " +
                        "WHERE  t.professor." + atributo + " =" + value;

            } else {

                query = "SELECT professor." + elemento + " FROM ProfTurma as p " +
                        "WHERE p.professor." + atributo + "=" + value;
            }
        }

        else{
            query = "SELECT professor."+ elemento +" FROM ProfTurma as p " +
                    "WHERE  p.turma.codigo  = " + value;
        }

        var a = em.createQuery(query,String.class);


        Optional<List<String>> result;

        result = Optional.ofNullable(a.getResultList());
        return result;
    }

    public void modify(Long idProf, Long idTurma, Long value){

        profTurmaRepository.modify(idProf,idTurma,value);
    }


    public ProfTurma find(String cpf, String codigo){

        return profTurmaRepository.find(cpf, codigo);

    }

    public ProfTurma findById(Long idProf, Long idTurma){
        return profTurmaRepository.findById(idProf,idTurma);
    }

    public void deleteProfTurma(Long profId,Long turmaId ){
        profTurmaRepository.deleteTurma(profId, turmaId);
    }
}
