package br.com.escola.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProfTurma implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "CPF")
    private Professor id;


    @ManyToOne
    @JoinColumn(name = "TURMA_CODIGO")
    private Turma codigo;




}
