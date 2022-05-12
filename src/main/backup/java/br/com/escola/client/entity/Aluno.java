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
    @Column(name = "MATRICULA")
    private Long id;

    @OneToMany(mappedBy = "id")
    private List<AlunoTurmas> matriculaAlun;

    @Column(name = "NOME", nullable = false, unique = false)
    private String nome;

    @Column(name = "EMAIL", nullable = true, unique = true)
    private String email;

    @OneToMany(mappedBy = "turma")
    @Column(name = "TURMA")
    private List<AlunoTurmas> turma;
}
