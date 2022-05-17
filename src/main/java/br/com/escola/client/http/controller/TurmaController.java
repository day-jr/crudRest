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
    public ResponseEntity getTurma(@RequestParam(required = false, name = "codigo")
                                            Optional<String> codigo) {

        if (codigo.isEmpty()) {
            var found = turmaService.getTurma();
            if (found.isEmpty()) return new ResponseEntity(HttpStatus.NO_CONTENT);
            return new ResponseEntity(found, HttpStatus.OK);
        }

        var t = turmaService.findTurmaBycodigo(codigo.get());
        if (t.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        var found = turmaService.findTurma(t.get());
        return new ResponseEntity(found, HttpStatus.OK);
    }

    ///////////////////////////////////DELETE BY CODIGO
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity deleteByCodigo(@RequestParam("codigo") String codigo) throws Exception {
        var t = turmaService.findTurmaBycodigo(codigo);

        if (t.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        turmaService.deleteDependency(t.get());
        turmaService.deleteTurmaById(t.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }


    ///////////////////////////////////MODIFY BY CODIGO
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateTurma(@RequestParam("codigo") String codigo,
                                      @RequestBody Turma incomingBody) throws Exception {

        var found = turmaService.returnTurma(codigo);
        if (found == null) return new ResponseEntity(HttpStatus.NOT_FOUND);

        modelMapper.map(incomingBody, found);
        turmaService.save(found);
        return new ResponseEntity(HttpStatus.OK);
    }

}

