package br.com.escola.client.service;


import br.com.escola.client.entity.*;
import br.com.escola.client.repository.AlunoTurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoTurmaService {

    @Autowired
    public AlunoTurmaRepository alunoTurmaRepository;

    private EntityManager em;
    public AlunoTurmaService (EntityManager em){
        this.em = em;
    }

    public void saveComposite(AlunoTurma alunoTurma){
        String query="select A from Aluno as A where A.matricula = :matricula";

        var a= em.createQuery(query, Aluno.class);
        a.setParameter("matricula", alunoTurma.getAluno().getMatricula());
        var aluno=  a.getResultList();


        query="select T from Turma as T where T.codigo = :codigo";

        var b = em.createQuery(query, Turma.class);
        b.setParameter("codigo", alunoTurma.getTurma().getCodigo());
        var turma=  b.getResultList();



        var idTurma = turma.get(0).getId();
        var idAluno = aluno.get(0).getId();



        alunoTurmaRepository.saveComposite(idAluno,idTurma);
    }




    public List<String> getAlunoTurma(String elemento){
        var profTurma = alunoTurmaRepository.findAll();

        List<String> elementos = new ArrayList<>();



        for (AlunoTurma at:profTurma) {
            switch(elemento){
                case "nome":
                    elementos.add(at.getAluno().getNome());
                    break;


                case "matricula":
                    elementos.add(at.getAluno().getMatricula());
                    break;


                case "turma":
                    elementos.add(at.getTurma().getCodigo());
                    break;

            }
        }


        return elementos;
    }



}
