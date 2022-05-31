package br.com.escola.client.dto.professor;


import br.com.escola.client.entity.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO {

    private String cpf;
    private String nome;
    private String email;


    public ProfessorDTO(Optional<Professor> prof) {
        if (prof.isPresent()) {
            this.cpf = prof.get().getCpf();
            this.nome = prof.get().getNome();
            this.email = prof.get().getEmail();
        }

    }

    static public List<ProfessorDTO> parseList(Optional<List<Professor>> prof) {
        var professorListDTO = new ArrayList<ProfessorDTO>();

        if (prof.isPresent()) {
            for (Professor entity : prof.get()) {
                var parsingProfessor = new ProfessorDTO();
                parsingProfessor.cpf = entity.getCpf();
                parsingProfessor.nome = entity.getNome();
                parsingProfessor.email = entity.getEmail();
                professorListDTO.add(parsingProfessor);
            }
        }
        return professorListDTO;
    }

    public Professor build() {

        return new Professor()
                .setCpf(this.cpf)
                .setNome(this.nome)
                .setEmail(this.email);

    }


}
