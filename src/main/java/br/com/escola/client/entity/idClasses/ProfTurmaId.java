package br.com.escola.client.entity.idClasses;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;



import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor


@Embeddable
public class ProfTurmaId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private  Professor cpf;


    @ManyToOne(cascade = CascadeType.ALL)
    private Turma codigo;


    public ProfTurmaId(){}
}
