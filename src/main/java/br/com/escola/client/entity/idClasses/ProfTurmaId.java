package br.com.escola.client.entity.idClasses;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfTurmaId implements Serializable {

    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CPF")
    private Professor professor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CODIGO")
    private Turma turma;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfTurmaId)) return false;

        ProfTurmaId that = (ProfTurmaId) o;

        if (!id.equals(that.id)) return false;
        if (!professor.equals(that.professor)) return false;
        return turma.equals(that.turma);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + professor.hashCode();
        result = 31 * result + turma.hashCode();
        return result;
    }
}
