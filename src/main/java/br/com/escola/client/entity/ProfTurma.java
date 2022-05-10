package br.com.escola.client.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROF_TURMA")
public class ProfTurma implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CPF",referencedColumnName = "CPF")
    private  Professor cpf;

    @OneToOne
    @JoinColumn(name = "FK_CODIGO")
    private Turma codigo;





    public Professor getCpf() {
        return cpf;
    }

    public void setCpf(Professor cpf) {
        this.cpf = cpf;
    }

    public Turma getCodigo() {
        return codigo;
    }

    public void setCodigo(Turma codigo) {
        this.codigo = codigo;
    }

    public ProfTurma(Professor cpf, Turma codigo) {
        this.cpf = cpf;
        this.codigo = codigo;
    }


}