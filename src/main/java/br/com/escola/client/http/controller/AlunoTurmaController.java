package br.com.escola.client.http.controller;

import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.service.AlunoTurmaService;
import br.com.escola.client.service.TurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turma/aluno")
public class AlunoTurmaController {

    /////////////////////////////////////////////////////
    ///////DELETE E FAZER AS FUNÇÕES NO PROF_TURMA TAMBEM
    //////////////////////////////////////////////////////


    @Autowired
    AlunoTurmaService alunoTurmaService;

    @Autowired
    TurmaService turmaService;

    @Autowired
    ModelMapper modelMapper;




    ////////////////////////////////SAVE////////////////////////////////////
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompositeAluno(@RequestBody AlunoTurma alunoTurma){

        alunoTurmaService.saveComposite(alunoTurma);
    }



    ////////////////////////////////FILTER ALL BY////////////////////////////////////
    @GetMapping("/filter/{elemento}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAlunoTurma(@PathVariable("elemento") String elemento){



        return alunoTurmaService.getAlunoTurmaBy(elemento);
    }


    ////////////////////////////////SEARCH X WHERE Y = Z////////////////////////////////////
    @GetMapping("/search/{elemento}By{atributo}={value}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<List<String>> findAlunoTurma(@PathVariable("elemento") String elemento,
                                                @PathVariable("atributo") String atributo,
                                                @PathVariable("value") String value)
    {return alunoTurmaService.findAlunoTurma(elemento,atributo,value);}




    ///////////////////////////////////MODIFY CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlunoTurma(@RequestParam("matricula") String matricula,@RequestParam("codigo") String codigo ,
                                @RequestBody ProfTurma incomingBody){

        AlunoTurma at = alunoTurmaService.find(matricula,codigo);
        alunoTurmaService.deleteAlunoTurma(at.getAluno().getId(), at.getTurma().getId());
        modelMapper.map(incomingBody, at);
        alunoTurmaService.saveComposite(at);

    }



    ///////////////////////////////////DELETE CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlunoTurma(@RequestParam("matricula") String matricula,
                                 @RequestParam("codigo") String codigo){

        var at = alunoTurmaService.find(matricula,codigo);
        var alunoId= at.getAluno().getId();
        var turmaId = at.getTurma().getId();
        alunoTurmaService.deleteAlunoTurma(alunoId,turmaId);


    }

}
