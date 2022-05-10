package br.com.escola.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Turma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PK_ID")
    private Long id;


    @Column(name="CODIGO")
    private String codigo;


    @Column(name = "TURNO")
    private String turno;

    @Column(name = "INICIO")
    private String inicio;

    @Column(name = "DURACAO")
    private String duracao;


    /////////////////
    @OneToMany(mappedBy = "codigo")
    @Column(name = "TURMAS")
    private List<ProfTurma> turmaCodigo;


    @OneToMany(mappedBy = "codigo")
    @Column(name = "TURMAS")
    private List<AlunoTurma> turmaCodigoAlun;

}
