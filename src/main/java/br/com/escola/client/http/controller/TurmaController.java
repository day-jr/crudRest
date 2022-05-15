package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Turma;

import br.com.escola.client.service.TurmaService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    TurmaService turmaService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turma saveTurma(@RequestBody Turma turma){

        return turmaService.save(turma);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Turma> getTurma(){
        return turmaService.getTurma();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Turma> findTurma(@PathVariable("id") Long id){
        return turmaService.findTurma(id);

    }

    ///////////////////////////////////DELETE BY CPF
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCodigo(@RequestParam("codigo") String codigo){
        var t = turmaService.findTurmaBycodigo(codigo);


        turmaService.deleteDependency(t);

        turmaService.deleteTurmaById(t);
    }




    ///////////////////////////////////MODIFY BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTurma(@RequestParam("codigo") String codigo, @RequestBody Turma incomingBody){

        var t = turmaService.returnTurma(codigo);
        modelMapper.map(incomingBody, t);
        turmaService.save(t);

    }

}

