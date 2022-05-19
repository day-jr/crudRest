package br.com.escola.client.http.controller;
import br.com.escola.client.entity.*;
import br.com.escola.client.service.AlunoService;
import br.com.escola.client.service.AlunoTurmaService;
import br.com.escola.client.service.TurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmaAluno")
public class AlunoTurmaController {

    @Autowired
    AlunoTurmaService alunoTurmaService;

    @Autowired
    AlunoService alunoService;

    @Autowired
    TurmaService turmaService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/codigo/{codigo}/matricula/{matricula}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveComposite(@PathVariable String matricula,@PathVariable String codigo) {
        alunoTurmaService.saveComposite(matricula,codigo);
    }

    ////////////////////////////////SAVE////////////////////////////////////
    @GetMapping
    public ResponseEntity getAlunoTurma(
            @RequestParam(required = false, name = "matricula") Optional<String> matricula,
            @RequestParam(required = false, name = "codigo") Optional<String> codigo,
            @RequestParam(required = false, name = "numberMinOfClasses") Optional<Long> amountMin,
            @RequestParam(required = false, name = "numberMaxOfClasses") Optional<Long> amountMax){


        if (matricula.isPresent() && codigo.isPresent()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        List<AlunoTurma> alunoTurmaList;

        //Search by min/max classes assigned to a professor
        if (amountMin.isPresent()||amountMax.isPresent()) return new ResponseEntity(
                alunoTurmaService.filterByNumberOfStudents(amountMin, amountMax),
                HttpStatus.OK);

        //Search all classes assigned to a registration
        if (matricula.isPresent()) return new ResponseEntity(
                turmaService.allClassesAssignedToRegistration(matricula),
                HttpStatus.OK);

        //Search all students assigned to a class code
        if (codigo.isPresent()) return new ResponseEntity(
                alunoService.allStudentsAssigned(codigo.get()),
                HttpStatus.OK);


        alunoTurmaList = alunoTurmaService.getAll();
        return new ResponseEntity(alunoTurmaList, HttpStatus.OK);
    }


    ///////////////////////////////////MODIFY CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity updateAlunoTurma(@RequestParam("matricula") String matricula,
                                           @RequestParam("codigo") String codigo,
                                           @RequestBody AlunoTurma incomingBody) {

        var at = alunoTurmaService.find(matricula, codigo);
        if(at.isEmpty())return new ResponseEntity(HttpStatus.NOT_FOUND);

        var alunoId = at.get().getAluno().getId();
        var turmaId = at.get().getTurma().getId();

        alunoTurmaService.deleteAlunoTurma(alunoId,turmaId);
        modelMapper.map(incomingBody, at.get());

        alunoTurmaService.saveComposite(matricula,codigo);
        return new ResponseEntity(HttpStatus.OK);
    }


    ///////////////////////////////////DELETE CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity deleteAlunoTurma(@RequestParam("matricula") String matricula,
                                           @RequestParam("codigo") String codigo) {
        var at = alunoTurmaService.find(matricula, codigo);
        if(at.isEmpty())return new ResponseEntity(HttpStatus.NOT_FOUND);

        var alunoId = at.get().getAluno().getId();
        var turmaId = at.get().getTurma().getId();

        alunoTurmaService.deleteAlunoTurma(alunoId, turmaId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
