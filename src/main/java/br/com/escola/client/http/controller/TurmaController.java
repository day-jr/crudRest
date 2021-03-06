package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Turma;
import br.com.escola.client.service.TurmaService;
import br.com.escola.client.dto.turma.TurmaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    TurmaService turmaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turma saveTurma(@RequestBody TurmaDTO turma) {
        return turmaService.save(turma.build());
    }


    @GetMapping
    public ResponseEntity<List<TurmaDTO>> getTurmas(
            @RequestParam(required = false, name = "cpf") Optional<String> cpf,
            @RequestParam(required = false, name = "codigo") Optional<String> codigo,
            @RequestParam(required = false, name = "matricula") Optional<String> matricula,
            @RequestParam(required = false, name = "finishAfter") Optional<Time> finishAfter) {

        if (finishAfter.isPresent()) {

            var classes =
                    TurmaDTO.parseList(turmaService.filterClassesByFinishTime(finishAfter.get()));

            return new ResponseEntity<>(classes, HttpStatus.OK);
        }


        if (cpf.isPresent() && matricula.isPresent()) {
            var inCommon =
                    TurmaDTO.parseList
                            (turmaService.getTurmaWhereStudentIsTaughtByProfessor(matricula.get(), cpf.get()));
            return new ResponseEntity<>(inCommon, HttpStatus.OK);
        }


        //Search all classes assigned to a CPF
        if (cpf.isPresent() && matricula.isEmpty()) {
            var allClassesAssignedToCpf =
                    TurmaDTO.parseList(turmaService.allClassesAssignedToCpf(cpf.get()));

            return new ResponseEntity<>(
                    allClassesAssignedToCpf,
                    HttpStatus.OK);
        }

        if (codigo.isEmpty()) {
            var turmasList = TurmaDTO.parseList(turmaService.getTurmas());
            return new ResponseEntity<>(turmasList, HttpStatus.OK);
        }


        var classId = turmaService.findTurmaIdBycodigo(codigo.get());
        if (classId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var turma = new TurmaDTO(turmaService.findTurma(classId.get()));
        return new ResponseEntity<>(Collections.singletonList(turma), HttpStatus.OK);
    }

    ///////////////////////////////////DELETE BY CODIGO
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity<Void> deleteByCodigo(@RequestParam("codigo") String codigo) {
        var turma = turmaService.findTurmaIdBycodigo(codigo);
        if (turma.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        turmaService.deleteDependency(turma.get());
        turmaService.deleteTurmaById(turma.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    ///////////////////////////////////MODIFY BY CODIGO
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateTurma(@RequestParam("codigo") String codigo,
                                            @RequestBody TurmaDTO incomingBody) {
        if (turmaService.update(incomingBody,codigo).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

