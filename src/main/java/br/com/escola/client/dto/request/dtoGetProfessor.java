package br.com.escola.client.dto.request;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoGetProfessor {

    private String cpf;
    private String nome;
    private String email;



    public dtoGetProfessor(Professor prof) {
        this.cpf = prof.getCpf();
        this.nome = prof.getNome();
        this.email = prof.getEmail();

    }

    static public List<dtoGetProfessor> parseList(List<Professor> prof) {
        var professorListDTO = new ArrayList<dtoGetProfessor>();

        for (Professor entity: prof) {
            var parsingProfessor = new dtoGetProfessor();
            parsingProfessor.cpf = entity.getCpf();
            parsingProfessor.nome = entity.getNome();
            parsingProfessor.email = entity.getEmail();
            professorListDTO.add(parsingProfessor);
        }
        return professorListDTO;
    }



}
