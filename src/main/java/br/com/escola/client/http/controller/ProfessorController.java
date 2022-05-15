package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfessorService;
import org.hibernate.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Professor saveProfessor(@RequestBody Professor professor){



        return professorService.save(professor);
    }


    ///////////////////////////////////GET ALL
    ////////////////////////////////////////////////////////////////////
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> getProfessor(){
        return professorService.getProfessor();
    }



    ///////////////////////////////////GET BY CPF
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/search/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Professor findProfessor(@PathVariable("cpf") String cpf){

        return professorService.findByCpf(cpf);

    }




    ///////////////////////////////////DELETE BY CPF
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestParam("cpf") String cpf){
        var p = professorService.findByCpf(cpf).getId();
        professorService.deleteDependency(p);

        professorService.deleteProfessorByCpf(cpf);
    }




    ///////////////////////////////////MODIFY BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfessor(@RequestParam("cpf") String cpf, @RequestBody Professor incomingBody){

        Professor p = professorService.findByCpf(cpf);
        modelMapper.map(incomingBody, p);
        professorService.save(p);

        }



}


