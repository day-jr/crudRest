package br.com.escola.client.dto.request;

import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoGetAluno {

    private String matricula;
    private String nome;
    private String email;
    private List<String> turmas;


    public dtoGetAluno(Aluno aluno) {
        this.matricula = aluno.getMatricula();
        this.nome = aluno.getNome();
        this.email = aluno.getEmail();

    }

    static public List<dtoGetAluno> parseList(List<Aluno> aluno) {
        var alunoListDTO = new ArrayList<dtoGetAluno>();

        for (Aluno entity: aluno) {
            var parsingAluno = new dtoGetAluno();
            parsingAluno.matricula = entity.getMatricula();
            parsingAluno.nome = entity.getNome();
            parsingAluno.email = entity.getEmail();
            alunoListDTO.add(parsingAluno);
        }
        return alunoListDTO;
    }






}