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

        if (!professor.equals(profTurma.professor)) return false;
        return turma.equals(profTurma.turma);
    }

    @Override
    public int hashCode() {
        int result = professor.hashCode();
        result = 31 * result + turma.hashCode();
        return result;
    }

    public Professor getProfessor() {
        return professor;
    }


    public void setProfessor(Professor professor) {

        this.professor = professor;
    }
}