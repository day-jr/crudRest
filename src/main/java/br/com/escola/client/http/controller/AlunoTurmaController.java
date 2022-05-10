package br.com.escola.client.http.controller;

import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.service.AlunoTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/turma/aluno")
public class AlunoTurmaController {

    @Autowired
    AlunoTurmaService alunoTurmaService;

    @Autowired
    ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoTurma SaveAlunoTurma(@RequestBody AlunoTurma alunoTurma){
        return alunoTurmaService.Save(alunoTurma);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlunoTurma> GetAlunoTurma(){
        return alunoTurmaService.GetAlunoTurma();
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  AlunoTurma FindAlunoTurma(@PathVariable("id") Long id){
        return alunoTurmaService.FindAlunoTurma(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteById(@PathVariable("id") Long id){
        alunoTurmaService.FindAlunoTurma(id)
                .map(alunoTurma -> {
                    alunoTurmaService.DeleteAlunoTurmaById(alunoTurma.getId());
                    return Void.TYPE;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateAlunoTurma(@PathVariable("id") Long id, @RequestBody AlunoTurma alunoTurma){
        alunoTurmaService.FindAlunoTurma(id)
                .map(alunoTurmaBase-> {
                    modelMapper.map(alunoTurma, alunoTurmaBase);
                    alunoTurmaService.Save(alunoTurmaBase);
                    return Void.TYPE;

                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
