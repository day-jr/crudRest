package br.com.escola.client.entity;


import br.com.escola.client.entity.idClasses.ProfTurmaId;

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
@Entity
@Table(name = "PROF_TURMA")
@IdClass(ProfTurmaId.class)
@Accessors(chain = true)
public class ProfTurma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @JoinColumn(referencedColumnName = "FK_TURMAS")
    private Professor professor;

    @Id
    @JoinColumn(referencedColumnName = "FK_PROFESSORES")
    private Turma turma;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfTurma)) return false;

        ProfTurma profTurma = (ProfTurma) o;

        if (!id.equals(profTurma.id)) return false;
        if (!professor.equals(profTurma.professor)) return false;
        return turma.equals(profTurma.turma);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + professor.hashCode();
        result = 31 * result + turma.hashCode();
        return result;
    }
}