package br.com.escola.client.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "ALUNO_TURMA")

@Accessors(chain = true)
public class AlunoTurma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "FK_ALUNOS", referencedColumnName = "id")
    private Aluno aluno;

    @ManyToOne(optional=false)
    @JoinColumn(name = "FK_TURMAS", referencedColumnName = "id")
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
