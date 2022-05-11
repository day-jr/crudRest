package br.com.escola.client.entity.idClasses;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class ProfTurmaId implements Serializable {


    private Professor cpf;
    private Turma codigo;

}
