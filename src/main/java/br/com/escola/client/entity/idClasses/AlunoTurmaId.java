package br.com.escola.client.entity.idClasses;

import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
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
@Embeddable
public class AlunoTurmaId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_MATRICULA")
    private Aluno aluno;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_CODIGO")
    private Turma turma;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlunoTurmaId)) return false;

        AlunoTurmaId that = (AlunoTurmaId) o;

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
