package br.com.escola.client.dto.response;

import br.com.escola.client.entity.Aluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoPostAluno {

    private String matricula;
    private String nome;
    private String email;

    public Aluno build() {
        return new Aluno()
                .setMatricula(this.matricula)
                .setNome(this.nome)
                .setEmail(this.email);

    }

}

