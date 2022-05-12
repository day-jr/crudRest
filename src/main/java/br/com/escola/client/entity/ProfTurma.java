package br.com.escola.client.entity;


import br.com.escola.client.entity.idClasses.ProfTurmaId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "PROF_TURMA")
@IdClass(ProfTurmaId.class)
public class ProfTurma implements Serializable {


    @Id
    @JoinColumn(referencedColumnName = "TURMAS")
    private Professor cpf;

    @Id
    @JoinColumn(referencedColumnName = "PROFESSORES")
    private Turma codigo;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfTurma)) return false;

        ProfTurma profTurma = (ProfTurma) o;

        if (!cpf.equals(profTurma.cpf)) return false;
        return codigo.equals(profTurma.codigo);
    }

    @Override
    public int hashCode() {
        int result = cpf.hashCode();
        result = 31 * result + codigo.hashCode();
        return result;
    }
}