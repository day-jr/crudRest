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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @Autowired
    ProfTurmaService profTurmaService;

    @Autowired
    ModelMapper modelMapper;


    ///////////////////////////////////CREATE
    ////////////////////////////////////////////////////////////////////
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Professor SaveProfessor(@RequestBody Professor professor){


        return professorService.Save(professor);
    }


    ///////////////////////////////////GET ALL
    ////////////////////////////////////////////////////////////////////
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> GetProfessor(){
        return professorService.GetProfessor();
    }


    ///////////////////////////////////GET TURMAS
    ////////////////////////////////////////////////////////////////////




    ///////////////////////////////////GET BY ID
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Professor FindProfessor(@PathVariable("id") Long id){
        return professorService.FindProfessor(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }




    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> FindProfessorByCpf(@RequestParam("cpf") String cpf){

        return this.professorService.findByCpfContains(cpf).
                stream().
                map(Professor::profConverter).
                collect(Collectors.toList());
    }




    ///////////////////////////////////DELETE BY ID
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteById(@PathVariable("id") Long id){
        professorService.FindProfessor(id)
                .map(professor -> {
                    professorService.DeleteProfessorById(professor.getId());
                return Void.TYPE;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }




    ///////////////////////////////////MODIFY BY ID
    ////////////////////////////////////////////////////////////////////
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateProfessor(@PathVariable("cpf") Long id, @RequestBody Professor professor){
        professorService.FindProfessor(id)
                .map(professorBase-> {
                    modelMapper.map(professor, professorBase);
                    professorService.Save(professorBase);
                    return Void.TYPE;

                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
