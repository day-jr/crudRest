package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Aluno;
import br.com.escola.client.service.AlunoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/aluno")
public class AlunoController {


    @Autowired
    AlunoService alunoService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno saveAluno(@RequestBody Aluno aluno) {
        return alunoService.save(aluno);
    }


    ///////////////////////////////////GET BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @GetMapping
    public ResponseEntity<Optional<List<Aluno>>> getAluno(@RequestParam(required = false, name = "matricula")
                                                                  Optional<String> matricula,
                                                          @RequestParam(required = false, name = "codigo")
                                                                  Optional<String> codigo) {

        //Search all students assigned to a class code
        if (codigo.isPresent()) {
            var allStudentsAssigned = alunoService.allStudentsAssigned(codigo.get());
            return new ResponseEntity<>(
                    allStudentsAssigned,
                    HttpStatus.OK);
        }
        if (matricula.isPresent()) {
            var alunoFound = alunoService.findByMatricula(matricula.get());
            if (alunoFound.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(alunoFound, HttpStatus.OK);


        } else {
            var alunosFound = Optional.ofNullable(alunoService.getAlunos());
            if (alunosFound.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(alunosFound, HttpStatus.OK);
        }

    }

    ///////////////////////////////////DELETE BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity<Void> deleteByMatricula(@RequestParam("matricula") String matricula) {
        var studentFound = alunoService.findByMatricula(matricula);
        if (studentFound.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        alunoService.deleteDependency(studentFound.get().getId());
        alunoService.deleteAlunoByMatricula(matricula);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    ///////////////////////////////////MODIFY BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity<Void> updateAluno(@RequestParam("matricula") String matricula, @RequestBody Aluno incomingBody) {

        var studentFound = alunoService.findByMatricula(matricula);
        if (studentFound.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        modelMapper.map(incomingBody, studentFound);
        alunoService.save(studentFound.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
