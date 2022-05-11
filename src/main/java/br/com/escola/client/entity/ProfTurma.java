package br.com.escola.client.entity;


import br.com.escola.client.entity.idClasses.ProfTurmaId;
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
@Table(name = "PROF_TURMA")
@IdClass(ProfTurmaId.class)
public class ProfTurma implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CPF",referencedColumnName = "CPF")
    private  Professor cpf;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CODIGO", referencedColumnName = "CODIGO")
    private Turma codigo;


}