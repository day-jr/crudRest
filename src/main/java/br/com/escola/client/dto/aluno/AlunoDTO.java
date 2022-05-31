package br.com.escola.client.dto.aluno;

import br.com.escola.client.entity.Aluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {

    private String matricula;
    private String nome;
    private String email;


    public AlunoDTO(Optional<Aluno> aluno) {

        if (aluno.isPresent()) {
            this.matricula = aluno.get().getMatricula();
            this.nome = aluno.get().getNome();
            this.email = aluno.get().getEmail();
        }

    }

    public static List<AlunoDTO> parseList(Optional<List<Aluno>> aluno) {
        var alunoListDTO = new ArrayList<AlunoDTO>();

        if (aluno.isPresent()) {
            for (Aluno entity : aluno.get()) {
                var parsingAluno = new AlunoDTO();
                parsingAluno.matricula = entity.getMatricula();
                parsingAluno.nome = entity.getNome();
                parsingAluno.email = entity.getEmail();
                alunoListDTO.add(parsingAluno);
            }
        }
        return alunoListDTO;
    }

    public Aluno build() {
        return new Aluno()
                .setMatricula(this.matricula)
                .setNome(this.nome)
                .setEmail(this.email);

    }


}