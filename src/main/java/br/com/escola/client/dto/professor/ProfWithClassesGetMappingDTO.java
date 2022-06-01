package br.com.escola.client.dto.professor;

import br.com.escola.client.entity.ProfTurma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfWithClassesGetMappingDTO {
    private String cpf;
    private String nome;
    private String email;

    private List<String> turmaCodigo = new ArrayList<>();



    public ProfWithClassesGetMappingDTO(Optional<ProfTurma> profTurma){
        if(profTurma.isPresent()) {
            this.cpf = profTurma.get().getProfessor().getCpf();
            this.nome = profTurma.get().getProfessor().getNome();
            this.email = profTurma.get().getProfessor().getEmail();
            this.turmaCodigo = Collections.singletonList(profTurma.get().getTurma().getCodigo());
        }

    }

    public static ProfWithClassesGetMappingDTO parseList(Optional<List<ProfTurma>> profTurma){
        var profWithClassesGetMappingDTO = new ProfWithClassesGetMappingDTO();

        if(profTurma.isPresent()) {
            profWithClassesGetMappingDTO.cpf = profTurma.get().get(0).getProfessor().getCpf();
            profWithClassesGetMappingDTO.nome = profTurma.get().get(0).getProfessor().getNome();
            profWithClassesGetMappingDTO.email = profTurma.get().get(0).getProfessor().getEmail();


            for(ProfTurma element: profTurma.get()) {
                profWithClassesGetMappingDTO.turmaCodigo.add(element.getTurma().getCodigo());
            }
        }

        return profWithClassesGetMappingDTO;
    }

}
