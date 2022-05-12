package br.com.escola.client.http.controller;

import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.service.AlunoTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turma/aluno")
public class AlunoTurmaController {

    @Autowired
    AlunoTurmaService alunoTurmaService;

    @Autowired
    ModelMapper modelMapper;

    

    ////////////////////////////////SAVE////////////////////////////////////
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompositeAluno(@RequestBody AlunoTurma alunoTurma){

        alunoTurmaService.saveComposite(alunoTurma);
    }



    ////////////////////////////////SHOW ALL////////////////////////////////////
    @GetMapping("/filter/{elemento}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAlunoTurma(@PathVariable("elemento") String elemento){

        return alunoTurmaService.getAlunoTurma(elemento);
    }


}
