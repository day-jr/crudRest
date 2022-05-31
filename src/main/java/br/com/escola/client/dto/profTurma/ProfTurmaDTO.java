package br.com.escola.client.dto.profTurma;
import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfTurmaDTO {
    private Long id;
    private String professorCpf;
    private String turmaCodigo;



    public ProfTurmaDTO(Optional<ProfTurma> profTurma){
        if(profTurma.isPresent()) {
            this.id = profTurma.get().getId();
            this.professorCpf = profTurma.get().getProfessor().getCpf();
            this.turmaCodigo = profTurma.get().getTurma().getCodigo();
        }

    }

    public static List<ProfTurmaDTO> parseList(Optional<List<ProfTurma>> profTurma){
        var profTurmaDTO = new ArrayList<ProfTurmaDTO>();


        if(profTurma.isPresent()) {
            for(ProfTurma element: profTurma.get()) {
                var parsingProfTurma = new ProfTurmaDTO();
                parsingProfTurma.id = element.getId();
                parsingProfTurma.turmaCodigo = element.getTurma().getCodigo();
                parsingProfTurma.professorCpf = element.getProfessor().getCpf();
                profTurmaDTO.add(parsingProfTurma);
            }
        }

        return profTurmaDTO;
    }


    public ProfTurma build(){
        return new ProfTurma()
                .setId(this.id)
                .setTurma(
                        new Turma()
                                .setCodigo(this.turmaCodigo))
                .setProfessor(
                        new Professor()
                                .setCpf(this.professorCpf));

    }



}
