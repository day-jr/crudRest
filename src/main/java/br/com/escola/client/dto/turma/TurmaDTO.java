package br.com.escola.client.dto.turma;

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
public class TurmaDTO {


    private String codigo;
    private String turno;
    private Time inicio;
    private Time duracao;

    public TurmaDTO(Optional<Turma> turma) {
        if (turma.isPresent()) {

            this.codigo = turma.get().getCodigo();
            this.turno = turma.get().getTurno();
            this.inicio = turma.get().getInicio();
            this.duracao = turma.get().getDuracao();

        }

    }

    static public List<TurmaDTO> parseList(Optional<List<Turma>> turma) {
        List<TurmaDTO> parsedClasses = new ArrayList<>();
        if (turma.isPresent()) {
            for (Turma element : turma.get()) {
                TurmaDTO toParse = new TurmaDTO();

                toParse.codigo = element.getCodigo();
                toParse.turno = element.getTurno();
                toParse.inicio = element.getInicio();
                toParse.duracao = element.getDuracao();
                parsedClasses.add(toParse);

            }
        }

        return parsedClasses;
    }

    public Turma build() {
        return new Turma()
                .setCodigo(this.codigo)
                .setTurno(this.turno)
                .setInicio(this.inicio)
                .setDuracao(this.duracao);
    }




}