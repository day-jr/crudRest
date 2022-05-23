package br.com.escola.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.List;



@AllArgsConstructor
@Builder

@Entity
public class Turma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private Long id;


    @Column(name = "CODIGO", nullable = false, unique = true)
    private String codigo;


    @Column(name = "TURNO", nullable = true)
    private String turno;


    @Column(name = "INICIO", nullable = true)
    private Time inicio;

    @Column(name = "DURACAO", nullable = true)
    private Time duracao;



    /////////////////
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Column(name = "FK_PROFESSORES")
    private transient List<ProfTurma> turmaCodigo;


    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Column(name = "FK_ALUNOS")
    private transient List<AlunoTurma> turmaCodigoAlun;




    public Turma() {
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Time getInicio() {
        return inicio;
    }

    public void setInicio(Time inicio) {
        this.inicio = inicio;
    }

    public Time getDuracao() {
        return duracao;
    }

    public void setDuracao(Time duracao) {
        this.duracao = duracao;
    }


    public List<ProfTurma> getTurmaCodigo() {
        return turmaCodigo;
    }

    public void setTurmaCodigo(List<ProfTurma> turmaCodigo) {
        this.turmaCodigo = turmaCodigo;
    }

    public List<AlunoTurma> getTurmaCodigoAlun() {
        return turmaCodigoAlun;
    }

    public void setTurmaCodigoAlun(List<AlunoTurma> turmaCodigoAlun) {
        this.turmaCodigoAlun = turmaCodigoAlun;
    }

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
