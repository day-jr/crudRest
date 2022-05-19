package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Turma;
import br.com.escola.client.service.TurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    TurmaService turmaService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turma saveTurma(@RequestBody Turma turma) {
        return turmaService.save(turma);
    }


    @GetMapping
    public ResponseEntity getTurma(@RequestParam(required = false, name = "codigo") Optional<String> codigo) {

        if (codigo.isEmpty()) {
            var turmasList = turmaService.getTurmas();
            return new ResponseEntity(turmasList, HttpStatus.OK);
        }

        var t = turmaService.findTurmaIdBycodigo(codigo.get());
        if (t.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);


        var turmasList = turmaService.findTurma(t.get());
        return new ResponseEntity(turmasList, HttpStatus.OK);
    }

    ///////////////////////////////////DELETE BY CODIGO
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity deleteByCodigo(@RequestParam("codigo") String codigo) {
        var turma = turmaService.findTurmaIdBycodigo(codigo);
        if (turma.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        turmaService.deleteDependency(turma.get());
        turmaService.deleteTurmaById(turma.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    ///////////////////////////////////MODIFY BY CODIGO
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateTurma(@RequestParam("codigo") String codigo,
                                      @RequestBody Turma incomingBody){

        var turmaId = turmaService.findTurmaIdBycodigo(codigo);
        if(turmaId.isEmpty())return new ResponseEntity(HttpStatus.NOT_FOUND);
        var turma = turmaService.findTurma(turmaId.get());

        modelMapper.map(incomingBody, turma.get());
        turmaService.save(turma.get());
        return new ResponseEntity(HttpStatus.OK);
    }

}

