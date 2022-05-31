package br.com.escola.client.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@Builder
@Entity
@Data
@Accessors(chain = true)
public class Professor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "CPF", unique = true, nullable = false)
    private String cpf;


    @Column(name = "NOME", nullable = false)
    private String nome;


    @Column(name = "EMAIL")
    private String email;

    ///////////////VVVV FK KEY VVVV//////////////
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor", orphanRemoval = true)
    @Column(name = "FK_PROFESSORES")
    private transient List<ProfTurma> profTurmaFK;


    public Professor() {
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;

        Professor professor = (Professor) o;

        if (!id.equals(professor.id)) return false;
        if (!cpf.equals(professor.cpf)) return false;
        if (!nome.equals(professor.nome)) return false;
        if (email != null ? !email.equals(professor.email) : professor.email != null) return false;
        return profTurmaFK != null ? profTurmaFK.equals(professor.profTurmaFK) : professor.profTurmaFK == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + cpf.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (profTurmaFK != null ? profTurmaFK.hashCode() : 0);
        return result;
    }
}
