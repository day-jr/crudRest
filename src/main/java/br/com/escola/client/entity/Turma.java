package br.com.escola.client.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.List;



@AllArgsConstructor
@Builder
@Data
@Entity
@Accessors(chain = true)
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
    @DateTimeFormat(pattern = "HH:mm")
    private Time inicio;

    @Column(name = "DURACAO", nullable = true)
    @DateTimeFormat(pattern = "HH:mm")
    private Time duracao;



    /////////////////
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    @Column(name = "FK_TURMAS")
    private transient List<ProfTurma> profFK;



    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    @Column(name = "FK_ALUNOS")
    private transient List<AlunoTurma> alunoFK;




    public Turma() {
    }


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
        if (profFK != null ? !profFK.equals(turma.profFK) : turma.profFK != null) return false;
        return alunoFK != null ? alunoFK.equals(turma.alunoFK) : turma.alunoFK == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + codigo.hashCode();
        result = 31 * result + (turno != null ? turno.hashCode() : 0);
        result = 31 * result + (inicio != null ? inicio.hashCode() : 0);
        result = 31 * result + (duracao != null ? duracao.hashCode() : 0);
        result = 31 * result + (profFK != null ? profFK.hashCode() : 0);
        result = 31 * result + (alunoFK != null ? alunoFK.hashCode() : 0);
        return result;
    }


}
