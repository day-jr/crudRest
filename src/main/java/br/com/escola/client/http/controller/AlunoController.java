package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.service.AlunoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public Aluno saveAluno(@RequestBody Aluno aluno, AlunoTurma alunoTurma) {
        return alunoService.save(aluno);
    }


    ///////////////////////////////////GET BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @GetMapping
    public ResponseEntity getAluno(@RequestParam(required = false, name = "matricula")
                                           Optional<String> matricula) {
        if (matricula.isPresent()) {
            var found = alunoService.findByMatricula(matricula.get());
            System.out.println(matricula);
            if (found.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
            return new ResponseEntity(found, HttpStatus.OK);


        } else {
            var found = alunoService.getAluno();
            if (found.isEmpty()) return new ResponseEntity(HttpStatus.NO_CONTENT);
            return new ResponseEntity(found, HttpStatus.OK);
        }

    }


    ///////////////////////////////////DELETE BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity deleteByMatricula(@RequestParam("matricula") String matricula){
        var found = alunoService.findByMatricula(matricula);
        if (found.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        alunoService.deleteDependency(found.get().getId());
        alunoService.deleteAlunoByMatricula(matricula);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }


    ///////////////////////////////////MODIFY BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity updateAluno(@RequestParam("matricula") String matricula, @RequestBody Aluno incomingBody) {

        var found = alunoService.findByMatricula(matricula);
        if (found.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        modelMapper.map(incomingBody, found);
        alunoService.save(found.get());

        return new ResponseEntity(HttpStatus.OK);
    }


}
