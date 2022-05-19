package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/professor")

public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @Autowired
    ModelMapper modelMapper;


    ///////////////////////////////////CREATE
    ////////////////////////////////////////////////////////////////////
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Professor saveProfessor(@RequestBody Professor professor) {
        return professorService.save(professor);
    }


    ///////////////////////////////////GET
    ////////////////////////////////////////////////////////////////////
    @GetMapping
    public ResponseEntity getProfessor(@RequestParam(required = false, name = "cpf") Optional<String> cpf) {

        if (cpf.isEmpty()) {
            var profsList = professorService.getProfessores();
            return new ResponseEntity(profsList, HttpStatus.OK);
        }

        var profsList = professorService.findByCpf(cpf.get());
        return new ResponseEntity(profsList, HttpStatus.OK);
    }


    ///////////////////////////////////DELETE BY CPF
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity deleteById(@RequestParam("cpf") String cpf) {
        var prof = professorService.findByCpf(cpf);
        if (prof.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        var id = prof.get().getId();


        professorService.deleteDependency(id);
        professorService.deleteProfessorByCpf(cpf);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    ///////////////////////////////////MODIFY BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity updateProfessor(@RequestParam("cpf") String cpf, @RequestBody Professor incomingBody) {
        var prof = professorService.findByCpf(cpf);
        if (prof.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        modelMapper.map(incomingBody, prof.get());
        professorService.save(prof.get());
        return new ResponseEntity(HttpStatus.OK);
    }


}


