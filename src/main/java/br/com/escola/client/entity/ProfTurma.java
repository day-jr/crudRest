package br.com.escola.client.entity;



import br.com.escola.client.entity.idClasses.ProfTurmaId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "PROF_TURMA")
@IdClass(ProfTurmaId.class)
public class ProfTurma implements Serializable {

    @Id
    @JoinColumn(name = "FK_CPF",referencedColumnName = "CPF")
    private  Professor cpf;

    @Id
    @JoinColumn(name = "FK_CODIGO", referencedColumnName = "CODIGO")
    private Turma codigo;





}