package br.com.escola.client.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

import java.util.List;



@Data
@AllArgsConstructor
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

    @OneToMany(mappedBy = "turma",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    @Column(name = "FK_PROFESSORES")
    private transient List<ProfTurma> turmaCodigo;




    @OneToMany(mappedBy = "turma",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    @Column(name = "FK_ALUNOS")
    private transient List<AlunoTurma> turmaCodigoAlun;


    //////////////////////////////////////
    public static Turma turmaConverter(Turma t){
        Turma turma = new Turma();
        turma.setCodigo(t.getCodigo());
        turma.setTurno(t.getTurno());
        turma.setInicio(t.getInicio());
        turma.setDuracao(t.getDuracao());
        return turma;
    }

    public Turma(){}







    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turma)) return false;

        Turma turma = (Turma) o;

        if (!id.equals(turma.id)) return false;
        if (!codigo.equals(turma.codigo)) return false;
        if (turno != null ? !turno.equals(turma.turno) : turma.turno != null) return false;
        if (inicio != null ? !inicio.equals(turma.inicio) : turma.inicio != null) return false;
        if (duracao != null ? !duracao.equals(turma.duracao) : turma.duracao != null) return false;
        if (turmaCodigo != null ? !turmaCodigo.equals(turma.turmaCodigo) : turma.turmaCodigo != null) return false;
        return turmaCodigoAlun != null ? turmaCodigoAlun.equals(turma.turmaCodigoAlun) : turma.turmaCodigoAlun == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + codigo.hashCode();
        result = 31 * result + (turno != null ? turno.hashCode() : 0);
        result = 31 * result + (inicio != null ? inicio.hashCode() : 0);
        result = 31 * result + (duracao != null ? duracao.hashCode() : 0);
        result = 31 * result + (turmaCodigo != null ? turmaCodigo.hashCode() : 0);
        result = 31 * result + (turmaCodigoAlun != null ? turmaCodigoAlun.hashCode() : 0);
        return result;
    }


}
