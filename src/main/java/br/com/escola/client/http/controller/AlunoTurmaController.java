package br.com.escola.client.http.controller;

import br.com.escola.client.entity.*;
import br.com.escola.client.service.AlunoTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.escola.client.dto.alunoTurma.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmaAluno")
public class AlunoTurmaController {

    @Autowired
    AlunoTurmaService alunoTurmaService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveComposite(@RequestBody AlunoTurmaDTO alunoTurmaDTO ) {
        alunoTurmaService.saveComposite(alunoTurmaDTO.getAlunoMatricula(), alunoTurmaDTO.getTurmaCodigo());
    }

    ////////////////////////////////SAVE////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<AlunoTurmaDTO>> getAlunoTurma(
            @RequestParam(required = false, name = "numberMinOfClasses") Optional<Long> amountMin,
            @RequestParam(required = false, name = "numberMaxOfClasses") Optional<Long> amountMax) {


        //Search  student by min/max classes assigned to
        if (amountMin.isPresent() || amountMax.isPresent()) {
            var studentsAssigned =
                    AlunoTurmaDTO.parseList(alunoTurmaService.filterByNumberOfStudents(amountMin, amountMax));

            return new ResponseEntity<>(
                    studentsAssigned,
                    HttpStatus.OK);
        }



        var alunoTurmaList = AlunoTurmaDTO.parseList(Optional.of(alunoTurmaService.getAll()));
        return new ResponseEntity<>(alunoTurmaList, HttpStatus.OK);
    }


    ///////////////////////////////////MODIFY CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity<Void> updateAlunoTurma(@RequestParam("matricula") String matricula,
                                                       @RequestParam("codigo") String codigo,
                                                       @RequestBody AlunoTurmaDTO incomingBody) {

        var at = alunoTurmaService.find(matricula, codigo);
        if (at.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var alunoId = at.get().getAluno().getId();
        var turmaId = at.get().getTurma().getId();

        alunoTurmaService.deleteAlunoTurma(alunoId, turmaId);
        modelMapper.map(incomingBody, at.get());

        alunoTurmaService.saveComposite(matricula, codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    ///////////////////////////////////DELETE CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    public ResponseEntity<Void> deleteAlunoTurma(@RequestParam("matricula") String matricula,
                                                       @RequestParam("codigo") String codigo) {
        var at = alunoTurmaService.find(matricula, codigo);
        if (at.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var alunoId = at.get().getAluno().getId();
        var turmaId = at.get().getTurma().getId();

        alunoTurmaService.deleteAlunoTurma(alunoId, turmaId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
