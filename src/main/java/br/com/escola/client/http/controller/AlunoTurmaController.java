package br.com.escola.client.http.controller;

import br.com.escola.client.entity.*;
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
    TurmaService turmaService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/codigo/{codigo}/matricula/{matricula}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompositeAluno(@PathVariable String matricula,@PathVariable String codigo) {
        AlunoTurma alunoTurma = new AlunoTurma();
        Aluno aluno = new Aluno();
        Turma turma = new Turma();

        aluno.setMatricula(matricula);
        turma.setCodigo(codigo);

        alunoTurma.setAluno(aluno);
        alunoTurma.setTurma(turma);
        alunoTurmaService.saveComposite(alunoTurma);
    }

    ////////////////////////////////SAVE////////////////////////////////////
    @GetMapping
    public ResponseEntity getAlunoTurma(@RequestParam(required = false, name = "matricula")
                                                   Optional<String> matricula,
                                       @RequestParam(required = false, name = "codigo")
                                               Optional<String> codigo) {

        if (matricula.isPresent() && codigo.isPresent()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        List<Aluno> foundAluno;
        List<Turma> foundTurma;
        List<AlunoTurma> found;


        if (matricula.isPresent()) {
            foundTurma = alunoTurmaService.getTurmas(matricula.get());
            if (foundTurma.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
            return new ResponseEntity(foundTurma, HttpStatus.OK);
        }


        if (codigo.isPresent()) {
            foundAluno = alunoTurmaService.getAlunos(codigo.get());
            if (foundAluno.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
            return new ResponseEntity(foundAluno, HttpStatus.OK);
        }

        found = alunoTurmaService.getAll();


        if (found.isEmpty()) return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(found, HttpStatus.OK);
    }




    ////////////////////////////////SEARCH X WHERE Y = Z////////////////////////////////////
//    @GetMapping("/search/{elemento}By{atributo}={value}")
//    @ResponseStatus(HttpStatus.OK)
//    public Optional<List<String>> findAlunoTurma(@PathVariable("elemento") String elemento,
//                                                 @PathVariable("atributo") String atributo,
//                                                 @PathVariable("value") String value) {
//        return alunoTurmaService.findAlunoTurma(elemento, atributo, value);
//    }


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
        alunoTurmaService.saveComposite(at.get());
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
