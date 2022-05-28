package br.com.escola.client.dto.request;

import br.com.escola.client.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoGetTurma {


    private String codigo;
    private String turno;
    private Time inicio;
    private Time duracao;


    static public List<dtoGetTurma> toList(Optional<List<Turma>> turma) {
        List<dtoGetTurma> parsedClasses = new ArrayList<>();
        if (turma.isPresent()) {
            for (Turma element : turma.get()) {
                dtoGetTurma toParse = new dtoGetTurma();

                toParse.codigo = element.getCodigo();
                toParse.turno = element.getTurno();
                toParse.inicio = element.getInicio();
                toParse.duracao = element.getDuracao();
                parsedClasses.add(toParse);

            }
        }

        return parsedClasses;
    }

    public dtoGetTurma(Optional<Turma> turma) {
        if (turma.isPresent()) {

            this.codigo = turma.get().getCodigo();
            this.turno = turma.get().getTurno();
            this.inicio = turma.get().getInicio();
            this.duracao = turma.get().getDuracao();

        }


    }


}