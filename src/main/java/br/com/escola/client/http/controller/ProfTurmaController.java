package br.com.escola.client.http.controller;


import br.com.escola.client.entity.*;
import br.com.escola.client.service.ProfTurmaService;
import br.com.escola.client.service.ProfessorService;
import br.com.escola.client.service.TurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping("/turma/prof")
public class ProfTurmaController {

    @Autowired
    ProfTurmaService profTurmaService;

    @Autowired
    TurmaService turmaService;

    @Autowired
    ProfessorService professorService;

    @Autowired
    ModelMapper modelMapper;


    ////////////////////////////////SAVE////////////////////////////////////
    @PostMapping("/codigo/{codigo}/cpf/{cpf}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompositeAluno(@PathVariable String cpf, @PathVariable String codigo) {
        profTurmaService.saveComposite(cpf, codigo);
    }


    ////////////////////////////////SHOW ALL////////////////////////////////////
    @GetMapping
    public ResponseEntity getProfTurma(
            @RequestParam(required = false, name = "cpf") Optional<String> cpf,
            @RequestParam(required = false, name = "codigo") Optional<String> codigo,
            @RequestParam(required = false, name = "numberMinOfClasses") Optional<Long> amountMin,
            @RequestParam(required = false, name = "numberMaxOfClasses") Optional<Long> amountMax) {

        //Prevents exceptions
        if (cpf.isPresent() && codigo.isPresent()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (amountMin.isEmpty() && amountMax.isPresent()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        //Search by min/max classes assigned to a professor
        if (amountMin.isPresent()) return new ResponseEntity(
                profTurmaService.filterByNumberOfProfessors(amountMin, amountMax),
                HttpStatus.OK);

        //Search all classes assigned to a CPF
        if (cpf.isPresent()) return new ResponseEntity(
                turmaService.allClassesAssignedToCpf(cpf),
                HttpStatus.OK);

        //Search all professors assigned to a class
        if (codigo.isPresent()) return new ResponseEntity(
                profTurmaService.allProfessorsAssigned(codigo),
                HttpStatus.OK);

        List<ProfTurma> found = profTurmaService.getAll();
        if (found.isEmpty()) return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(found, HttpStatus.OK);
    }


    ////////////////////////////////////Classes unassigned
    @GetMapping("/semTurma")
    public ResponseEntity noClass() {
        var professors = professorService.getProfessores();
        var classesAssigned = profTurmaService.getAll();
        List<Professor> professorsAssigned = new ArrayList<>();

        for (ProfTurma entidade : classesAssigned) professorsAssigned.add(entidade.getProfessor());

        //Search for unassigned professors
        List<Professor> unassignedProfessors = professors.stream()
                .filter(object -> !professorsAssigned.contains(object))
                .toList();

        if (unassignedProfessors.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity(unassignedProfessors, HttpStatus.OK);
    }


    ///////////////////////////////////MODIFY CODIGO BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity updateProfTurma(@RequestParam("cpf") String cpf,
                                          @RequestParam("codigo") String codigo,
                                          @RequestBody ProfTurma incomingBody) {

        var pt = profTurmaService.find(cpf, codigo);
        if (pt.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        var idProf = pt.get().getProfessor().getId();
        var idClass = pt.get().getTurma().getId();

        profTurmaService.deleteProfTurma(idProf, idClass);
        modelMapper.map(incomingBody, pt.get());
        profTurmaService.saveComposite(cpf, codigo);

        return new ResponseEntity(HttpStatus.OK);
    }


    ///////////////////////////////////DELETE CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity deleteProfTurma(@RequestParam("cpf") String cpf,
                                          @RequestParam("codigo") String codigo) {

        var profTurma = profTurmaService.find(cpf, codigo);
        if (profTurma.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        var profId = profTurma.get().getProfessor().getId();
        var classId = profTurma.get().getTurma().getId();
        profTurmaService.deleteProfTurma(profId, classId);


        return new ResponseEntity(HttpStatus.OK);
    }

}
