package br.com.escola.client.dto.response;

import br.com.escola.client.entity.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoPostProfessor {

    private String cpf;
    private String nome;
    private String email;


    public Professor build() {

        return new Professor()
                .setCpf(this.cpf)
                .setNome(this.nome)
                .setEmail(this.email);

    }


}



