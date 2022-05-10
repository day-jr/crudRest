package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Turma;

import br.com.escola.client.service.TurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    TurmaService turmaService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turma SaveTurma(@RequestBody Turma turma){

        return turmaService.Save(turma);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Turma> GetTurma(){
        return turmaService.GetTurma();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  Turma FindTurma(@PathVariable("id") Long id){
        return turmaService.FindTurma(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteById(@PathVariable("id") Long id){
        turmaService.FindTurma(id)
                .map(turma -> {
                    turmaService.DeleteTurmaById(turma.getId());
                    return Void.TYPE;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateTurma(@PathVariable("id") Long id, @RequestBody Turma turma){
        turmaService.FindTurma(id)
                .map(turmaBase-> {
                    modelMapper.map(turma, turmaBase);
                    turmaService.Save(turmaBase);
                    return Void.TYPE;

                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}

