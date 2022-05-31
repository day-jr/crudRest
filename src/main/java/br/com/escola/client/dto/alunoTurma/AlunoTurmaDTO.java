package br.com.escola.client.dto.alunoTurma;

import br.com.escola.client.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoTurmaDTO {
    private Long id;
    private String alunoMatricula;
    private String turmaCodigo;



    public AlunoTurmaDTO(Optional<AlunoTurma> alunoTurma){
        if(alunoTurma.isPresent()) {
            this.id = alunoTurma.get().getId();
            this.alunoMatricula = alunoTurma.get().getAluno().getMatricula();
            this.turmaCodigo = alunoTurma.get().getTurma().getCodigo();
        }

    }

    public static List<AlunoTurmaDTO> parseList(Optional<List<AlunoTurma>> alunoTurma){
        var alunoturmaDTO = new ArrayList<AlunoTurmaDTO>();


        if(alunoTurma.isPresent()) {
            for(AlunoTurma element: alunoTurma.get()) {
                var parsingAlunoTurma = new AlunoTurmaDTO();
                parsingAlunoTurma.id = element.getId();
                parsingAlunoTurma.turmaCodigo = element.getTurma().getCodigo();
                parsingAlunoTurma.alunoMatricula = element.getAluno().getMatricula();
                alunoturmaDTO.add(parsingAlunoTurma);
            }
        }

        return alunoturmaDTO;
    }


    public AlunoTurma build(){
        return new AlunoTurma()
                .setId(this.id)
                .setTurma(
                        new Turma()
                                .setCodigo(this.turmaCodigo))
                .setAluno(
                        new Aluno()
                                .setMatricula(this.alunoMatricula));

    }



}
