package br.com.escola.client.entity;


import br.com.escola.client.entity.idClasses.AlunoTurmaId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "ALUNO_TURMA")
@IdClass(AlunoTurmaId.class)
@Accessors(chain = true)
public class AlunoTurma implements Serializable {
    @Id
    @JoinColumn(referencedColumnName = "FK_TURMAS")
    private Aluno aluno;

    @Id
    @JoinColumn(referencedColumnName = "FK_ALUNOS")
    private Turma turma;


    public AlunoTurma() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlunoTurma)) return false;

        AlunoTurma that = (AlunoTurma) o;

        if (!aluno.equals(that.aluno)) return false;
        return turma.equals(that.turma);
    }

    @Override
    public int hashCode() {
        int result = aluno.hashCode();
        result = 31 * result + turma.hashCode();
        return result;
    }
}
