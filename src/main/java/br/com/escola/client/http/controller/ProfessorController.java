package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfessorService;
import org.hibernate.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            var found = professorService.getProfessor();
            if (found.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
            return new ResponseEntity(found, HttpStatus.OK);
        }

        var found = professorService.findByCpf(cpf.get());
        if (found.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(found, HttpStatus.OK);
    }


    ///////////////////////////////////DELETE BY CPF
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity deleteById(@RequestParam("cpf") String cpf) {
        var p = professorService.findByCpf(cpf);
        if (p.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        var id = p.get().getId();


        professorService.deleteDependency(id);
        professorService.deleteProfessorByCpf(cpf);
        return new ResponseEntity(HttpStatus.OK);
    }


    ///////////////////////////////////MODIFY BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity updateProfessor(@RequestParam("cpf") String cpf, @RequestBody Professor incomingBody) {
        var p = professorService.findByCpf(cpf);
        if (p.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        modelMapper.map(incomingBody, p.get());
        professorService.save(p.get());
        return new ResponseEntity(HttpStatus.OK);
    }


}


