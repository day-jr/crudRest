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
public class AlunoTurmas implements Serializable {

    @Id
    @JoinColumn(name = "MATRICULA")
    private Aluno id;

    @ManyToOne
    @JoinColumn
    private Turma codigo;


}
