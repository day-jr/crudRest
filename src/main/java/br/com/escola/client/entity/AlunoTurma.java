package br.com.escola.client.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ALUNO_TURMA")
public class AlunoTurma implements Serializable {
    @Id
    @Column(name="PK_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "FK_MATRICULA")
    private Aluno matricula;

    @OneToOne
    @JoinColumn(name = "FK_CODIGO")
    private Turma codigo;




    public AlunoTurma(Aluno matricula, Turma codigo) {
        this.matricula = matricula;
        this.codigo = codigo;
    }

    public Aluno getMatricula() {
        return matricula;
    }

    public void setMatricula(Aluno matricula) {
        this.matricula = matricula;
    }

    public Turma getCodigo() {
        return codigo;
    }

    public void setCodigo(Turma codigo) {
        this.codigo = codigo;
    }
}
