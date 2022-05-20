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
    ModelMapper modelMapper;


    ////////////////////////////////SAVE////////////////////////////////////
    @PostMapping("/codigo/{codigo}/cpf/{cpf}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompositeAluno(@PathVariable String cpf, @PathVariable String codigo) {
        profTurmaService.saveComposite(cpf, codigo);
    }


    ////////////////////////////////SHOW ALL////////////////////////////////////
    @GetMapping
    public ResponseEntity<Optional<List<ProfTurma>>> getProfTurma(
            @RequestParam(required = false, name = "numberMinOfClasses") Optional<Long> amountMin,
            @RequestParam(required = false, name = "numberMaxOfClasses") Optional<Long> amountMax) {

        //Prevents exceptions

        if (amountMin.isEmpty() && amountMax.isPresent()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        //Search by min/max classes assigned to a professor
        if (amountMin.isPresent()) {
            var professorsFiltered =
                    Optional.ofNullable(
                            profTurmaService.filterByNumberOfProfessors(amountMin, amountMax));

            return new ResponseEntity<>(
                    professorsFiltered,
                    HttpStatus.OK);
        }


        var profTurmasFound = Optional.ofNullable(profTurmaService.getAll());
        if (profTurmasFound.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(profTurmasFound, HttpStatus.OK);
    }



    ///////////////////////////////////MODIFY CODIGO BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity <HttpStatus >updateProfTurma(@RequestParam("cpf") String cpf,
                                          @RequestParam("codigo") String codigo,
                                          @RequestBody ProfTurma incomingBody) {

        var pt = profTurmaService.find(cpf, codigo);
        if (pt.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var idProf = pt.get().getProfessor().getId();
        var idClass = pt.get().getTurma().getId();

        profTurmaService.deleteProfTurma(idProf, idClass);
        modelMapper.map(incomingBody, pt.get());
        profTurmaService.saveComposite(cpf, codigo);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    ///////////////////////////////////DELETE CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity<HttpStatus>deleteProfTurma(@RequestParam("cpf") String cpf,
                                          @RequestParam("codigo") String codigo) {

        var profTurma = profTurmaService.find(cpf, codigo);
        if (profTurma.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var profId = profTurma.get().getProfessor().getId();
        var classId = profTurma.get().getTurma().getId();
        profTurmaService.deleteProfTurma(profId, classId);


        return new ResponseEntity<>(HttpStatus.OK);
    }

}
