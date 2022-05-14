package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfTurmaService;
import br.com.escola.client.service.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteById(@PathVariable("cpf") String cpf){
        professorService.deleteProfessorByCpf(cpf);
    }




    ///////////////////////////////////MODIFY BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping("/{cpf}/{element}={value}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Professor updateProfessor(@PathVariable("cpf") String cpf, @PathVariable("element") String element,
                                @PathVariable("value") String value){
        Professor p = professorService.findByCpf(cpf);
        switch(element){
            case "cpf":
                p.setCpf(value);
                break;

            case "nome":
                p.setNome(value);
                break;

            case "email":
                p.setEmail(value);
                break;

        }

            return professorService.save(p);
        }



}


