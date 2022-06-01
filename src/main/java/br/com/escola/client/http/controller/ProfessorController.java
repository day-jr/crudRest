package br.com.escola.client.http.controller;


import br.com.escola.client.dto.professor.ProfWithClassesGetMappingDTO;
import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfTurmaService;
import br.com.escola.client.service.ProfessorService;
import br.com.escola.client.dto.professor.ProfessorDTO;
import br.com.escola.client.dto.profTurma.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professor")

public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @Autowired
    ProfTurmaService profTurmaService;


    ///////////////////////////////////CREATE
    ////////////////////////////////////////////////////////////////////
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Professor saveProfessor(@RequestBody ProfessorDTO professorDTO) {

        return professorService.save(professorDTO.build());
    }


    ///////////////////////////////////GET
    ////////////////////////////////////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getProfessor (
            @RequestParam(required = false, name = "codigo") Optional<String> codigo,
            @RequestParam(required = false, name = "cpf") Optional<String> cpf,
            @RequestParam(required = false, name = "numberMinOfClasses") Optional<Long> amountMin,
            @RequestParam(required = false, name = "numberMaxOfClasses") Optional<Long> amountMax,
            @RequestParam(required = false, name = "classes") Optional<Long> exactClass
    ) {


        if (cpf.isPresent() && codigo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if ((cpf.isPresent() || codigo.isPresent()) && (amountMin.isPresent() || amountMax.isPresent())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if ((cpf.isPresent() || codigo.isPresent()) && exactClass.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



        if(amountMin.isPresent() || amountMax.isPresent() || exactClass.isPresent()){
            var professorList =
                            profTurmaService.filterByNumberOfClasses(amountMin, amountMax, exactClass);

            return new ResponseEntity(professorList, HttpStatus.OK);

        }


        if (cpf.isEmpty() && codigo.isEmpty()) {
            var profsList = ProfessorDTO.parseList(Optional.of(professorService.getAllProfessores()));
            return new ResponseEntity<>(profsList, HttpStatus.OK);
        }

        //Search all professors assigned to a class
        if (codigo.isPresent()) {
            var allProfessorsAssigned =
                    ProfessorDTO.parseList(profTurmaService.allProfessorsAssigned(codigo));

            return new ResponseEntity<>(
                    allProfessorsAssigned,
                    HttpStatus.OK);
        }

        if (professorService.findByCpf(cpf.get()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var prof = new ProfessorDTO(professorService.findByCpf(cpf.get()));
        return new ResponseEntity<>(Collections.singletonList(prof), HttpStatus.OK);
    }

    ////////////////////////////////////Classes unassigned
    @GetMapping("/semTurma")
    public ResponseEntity<List<ProfessorDTO>> noClass() {
        var professors = professorService.getAllProfessores();
        var classesAssigned = profTurmaService.getAll();
        List<Professor> professorsAssigned = new ArrayList<>();

        for (ProfTurma entidade : classesAssigned) professorsAssigned.add(entidade.getProfessor());

        //Search for unassigned professors
        var unassignedProfessors =
                Optional.ofNullable(
                        professors.stream()
                                .filter(object -> !professorsAssigned.contains(object))
                                .toList());

        if (unassignedProfessors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var parsedUnassignedProfessors = ProfessorDTO.parseList(unassignedProfessors);

        return new ResponseEntity<>(parsedUnassignedProfessors, HttpStatus.OK);
    }


    ///////////////////////////////////DELETE BY CPF
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam("cpf") String cpf) {
        var prof = professorService.findByCpf(cpf);
        if (prof.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var id = prof.get().getId();


        professorService.deleteDependency(id);
        professorService.deleteProfessorByCpf(cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    ///////////////////////////////////MODIFY BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity<Void> updateProfessor(@RequestParam("cpf") String cpf,
                                                @RequestBody ProfessorDTO incomingBody) {

        if (professorService.update(incomingBody, cpf).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}