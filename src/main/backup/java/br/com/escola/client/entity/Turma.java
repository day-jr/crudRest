package br.com.escola.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Turma implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="CODIGO",nullable = false)
    private String codigo;


    @OneToMany(mappedBy = "turma")
    private List<ProfTurma> codigoTurmaProf;// = new ArrayList<>();


    @OneToMany(mappedBy = "turma")
    private List<AlunoTurmas> codigoTurma;// = new ArrayList<>();


    @Column(name = "TURNO", nullable = false)
    private String turno;


    @Column(name = "INICIO")
    private String inicio;

    @Column(name = "DURACAO")
    private String duracao;


}
