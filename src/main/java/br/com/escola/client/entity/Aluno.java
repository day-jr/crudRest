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
    @Column(name = "PK_MATRICULA")
    private Long id;


    @Column(name = "NOME", nullable = false, unique = false)
    private String nome;

    @Column(name = "EMAIL", nullable = true, unique = true)
    private String email;




    @OneToMany(mappedBy = "matricula")
    @Column(name = "FK_TURMAS")
    private List<AlunoTurma> turmas;



}
