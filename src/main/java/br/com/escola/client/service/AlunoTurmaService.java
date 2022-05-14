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

    public  AlunoTurma save(AlunoTurma alunoTurma){
        return alunoTurmaRepository.save(alunoTurma);
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

    public List<String> getAlunoTurmaBy(String elemento){
        var AlunoTurma = alunoTurmaRepository.findAll();

        List<String> elementos = new ArrayList<>();



        for (AlunoTurma at:AlunoTurma) {
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

    public AlunoTurma getAlunoTurma(String matricula){

        return alunoTurmaRepository.getByMatricula(matricula);
    }

    public Optional<List<String>> findAlunoTurma(String elemento, String atributo, String value){


        String query;

        if(!atributo.equals("codigo")) {

            if (elemento.equals("codigo")) {

                query = "SELECT turma." + elemento + " FROM AlunoTurma as t " +
                        "WHERE  t.aluno." + atributo + " =" + value;

            } else {

                query = "SELECT aluno." + elemento + " FROM AlunoTurma as a " +
                        "WHERE a.aluno." + atributo + "=" + value;
            }
        }

        else{
            query = "SELECT aluno."+ elemento +" FROM AlunoTurma as a " +
                    "WHERE  a.turma.codigo  = " + value;
        }

        var a = em.createQuery(query,String.class);


        Optional<List<String>> result;

        result = Optional.ofNullable(a.getResultList());
        return result;
    }

    public void deleteAlunoTurma(Long alunoId,Long turmaId ){
        alunoTurmaRepository.deleteTurma(alunoId, turmaId);
    }

    public AlunoTurma findById(Long idAluno, Long idTurma){
        return alunoTurmaRepository.findById(idAluno,idTurma);
    }

    public void modify(Long idAluno, Long idTurma, Long value){

        alunoTurmaRepository.modify(idAluno,idTurma,value);
    }


    public AlunoTurma find(String matricula, String codigo){

        return alunoTurmaRepository.find(matricula, codigo);

    }

}
