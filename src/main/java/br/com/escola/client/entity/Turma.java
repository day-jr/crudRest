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

    @OneToMany(mappedBy = "codigo",cascade = CascadeType.ALL,orphanRemoval = true)
    @Column(name = "TURMAS")
    private List<ProfTurma> turmaCodigo;


    @OneToMany(mappedBy = "codigo")
    @Column(name = "TURMAS")
    private List<AlunoTurma> turmaCodigoAlun;


    //////////////////////////////////////
    public static Turma turmaConverter(Turma t){
        Turma turma = new Turma();
        turma.setCodigo(t.getCodigo());
        turma.setTurno(t.getTurno());
        turma.setInicio(t.getInicio());
        turma.setDuracao(t.getDuracao());
        return turma;
    }


}
