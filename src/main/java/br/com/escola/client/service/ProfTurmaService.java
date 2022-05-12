package br.com.escola.client.service;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.entity.idClasses.ProfTurmaId;
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

    //////////////////////////////////////////
    public List<String> getProfTurma(String elemento){
        var profTurma = profTurmaRepository.findAll();

        List<String> elementos = new ArrayList<>();



        for (ProfTurma pt:profTurma) {
            switch(elemento){
                case "nome":
                    elementos.add(pt.getCpf().getNome());
                    break;


                case "cpf":
                    elementos.add(pt.getCpf().getCpf());
                    break;


                case "codigo":
                    elementos.add(pt.getCodigo().getCodigo());
                    break;

            }
        }


        return elementos;
    }



    public Optional<ProfTurma> findProfTurma(Long id1, Long id2){

        ProfTurmaId profTurmaId = new ProfTurmaId();
        Professor professor = new Professor();
        Turma turma = new Turma();

        professor.setId(id1);
        turma.setId(id2);

        profTurmaId.setCodigo(turma);
        profTurmaId.setCpf(professor);


        String query = "SELECT * FROM ProfTurma WHERE (:cpf,:codigo)";
        var a = em.createQuery(query,ProfTurma.class);
        a.setParameter(":cpf", profTurmaId.getCpf());
        a.setParameter(":codigo", profTurmaId.getCodigo());


        Optional<ProfTurma> result;

        result = Optional.ofNullable(a.getSingleResult());
        return result;
    }
    //////////////////////////////////////////
//
//
//    public void DeleteProfTurmaById(String id){
//        profTurmaRepository.deleteById(id);
//    }
}
