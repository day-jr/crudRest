package br.com.escola.client.http.controller;


import br.com.escola.client.entity.*;
import br.com.escola.client.service.ProfTurmaService;
import br.com.escola.client.service.TurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/turma/prof")
public class ProfTurmaController {


    @Autowired
    ProfTurmaService profTurmaService;


    @Autowired
    ModelMapper modelMapper;


    ////////////////////////////////SAVE////////////////////////////////////
    @PostMapping("/codigo/{codigo}/cpf/{cpf}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompositeAluno(@PathVariable String cpf, @PathVariable String codigo) {
        ProfTurma profTurma = new ProfTurma();
        Professor professor = new Professor();
        Turma turma = new Turma();
        professor.setCpf(cpf);
        turma.setCodigo(codigo);
        profTurma.setProfessor(professor);
        profTurma.setTurma(turma);

        profTurmaService.saveComposite(profTurma);
    }


    ////////////////////////////////SHOW ALL////////////////////////////////////
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getProfTurma(@RequestParam(required = false, name = "cpf") Optional<String> cpf,
                                       @RequestParam(required = false, name = "codigo") Optional<String> codigo) {
        if (cpf.isPresent() && codigo.isPresent()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        List<Professor> foundProf;
        List<Turma> foundTurma;
        List<ProfTurma> found;


        if (cpf.isPresent()) {
            foundTurma = profTurmaService.getTurmas(cpf.get());
            if (foundTurma.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
            return new ResponseEntity(foundTurma, HttpStatus.OK);
        }


        if (codigo.isPresent()) {
            foundProf = profTurmaService.getProfessores(codigo.get());
            if (foundProf.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);
            return new ResponseEntity(foundProf, HttpStatus.OK);
        }

        found = profTurmaService.getAll();


        if (found.isEmpty()) return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(found, HttpStatus.OK);
    }


    ////////////////////////////////SEARCH X WHERE Y = Z////////////////////////////////////
//    @GetMapping("/search/{elemento}By{atributo}={value}")
//    @ResponseStatus(HttpStatus.OK)
//    public Optional<List<String>> findProfTurma(@PathVariable("elemento") String elemento,
//                                                @PathVariable("atributo") String atributo,
//                                                @PathVariable("value") String value) {
//        return profTurmaService.findProfTurma(elemento, atributo, value);
//    }


    ///////////////////////////////////MODIFY CODIGO BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping

    public ResponseEntity updateProfTurma(@RequestParam("cpf") String cpf,
                                          @RequestParam("codigo") String codigo,
                                          @RequestBody ProfTurma incomingBody) {

        var pt = profTurmaService.find(cpf, codigo);
        if (pt.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        profTurmaService.deleteProfTurma(pt.get().getProfessor().getId(), pt.get().getTurma().getId());
        modelMapper.map(incomingBody, pt.get());
        profTurmaService.saveComposite(pt.get());

        return new ResponseEntity(HttpStatus.OK);
    }


    ///////////////////////////////////DELETE CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity deleteProfTurma(@RequestParam("cpf") String cpf, @RequestParam("codigo") String codigo) {

        var profTurma = profTurmaService.find(cpf, codigo);
        if (profTurma.isEmpty()) return new ResponseEntity(HttpStatus.NOT_FOUND);

        var profId = profTurma.get().getProfessor().getId();
        var turmaId = profTurma.get().getTurma().getId();
        profTurmaService.deleteProfTurma(profId, turmaId);


        return new ResponseEntity(HttpStatus.OK);
    }


}
