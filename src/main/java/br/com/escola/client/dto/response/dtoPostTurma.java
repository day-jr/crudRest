package br.com.escola.client.dto.response;

import br.com.escola.client.entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoPostTurma {


    private String codigo;
    private String turno;
    private Time inicio;
    private Time duracao;


    public Turma build() {
        return new Turma()
                .setCodigo(this.codigo)
                .setTurno(this.turno)
                .setInicio(this.inicio)
                .setDuracao(this.duracao);
    }






}
