package br.com.escola.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Aluno implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "MATRICULA")
    private String matricula;


    @Column(name = "NOME", nullable = true, unique = false)
    private String nome;

    @Column(name = "EMAIL", nullable = true, unique = true)
    private String email;




    @OneToMany(cascade = CascadeType.ALL,mappedBy = "aluno",orphanRemoval = true)
    @Column(name = "FK_TURMAS")
    private List<AlunoTurma> turmas;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno)) return false;

        Aluno aluno = (Aluno) o;

        if (!id.equals(aluno.id)) return false;
        if (!nome.equals(aluno.nome)) return false;
        if (email != null ? !email.equals(aluno.email) : aluno.email != null) return false;
        return turmas != null ? turmas.equals(aluno.turmas) : aluno.turmas == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (turmas != null ? turmas.hashCode() : 0);
        return result;
    }
}
