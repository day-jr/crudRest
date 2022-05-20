package br.com.escola.client.http.controller;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfTurmaService;
import br.com.escola.client.service.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Professor saveProfessor(@RequestBody Professor professor) {
        return professorService.save(professor);
    }


    ///////////////////////////////////GET
    ////////////////////////////////////////////////////////////////////
    @GetMapping
    public ResponseEntity<Optional<List<Professor>>> getProfessor(
            @RequestParam(required = false, name = "codigo") Optional<String> codigo,
            @RequestParam(required = false, name = "cpf") Optional<String> cpf) {

        if (cpf.isEmpty()) {
            var profsList =
                    Optional.ofNullable(
                            professorService.getProfessores());

            return new ResponseEntity<>(profsList, HttpStatus.OK);
        }

        //Search all professors assigned to a class
        if (codigo.isPresent()) {
            var allProfessorsAssigned =
                    profTurmaService.allProfessorsAssigned(codigo);

            return new ResponseEntity<>(
                    allProfessorsAssigned,
                    HttpStatus.OK);
        }

        var prof = professorService.findByCpf(cpf.get());
        return new ResponseEntity(prof, HttpStatus.OK);
    }

    ////////////////////////////////////Classes unassigned
    @GetMapping("/semTurma")
    public ResponseEntity<Optional<List<Professor>>> noClass() {
        var professors = professorService.getProfessores();
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
        return new ResponseEntity<>(unassignedProfessors, HttpStatus.OK);
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
                                                      @RequestBody Professor incomingBody) {
        var prof = professorService.findByCpf(cpf);
        if (prof.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        modelMapper.map(incomingBody, prof.get());
        professorService.save(prof.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}


