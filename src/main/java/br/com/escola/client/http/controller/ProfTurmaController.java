package br.com.escola.client.http.controller;


import br.com.escola.client.dto.profTurma.*;
import br.com.escola.client.dto.professor.ProfWithClassesGetMappingDTO;
import br.com.escola.client.entity.*;
import br.com.escola.client.service.ProfTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping("/turmaProf")
public class ProfTurmaController {

    @Autowired
    ProfTurmaService profTurmaService;


    @Autowired
    ModelMapper modelMapper;


    ////////////////////////////////SAVE////////////////////////////////////
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompositeAluno(@RequestBody ProfTurmaDTO turma) {
        profTurmaService.saveComposite(turma.getProfessorCpf(), turma.getTurmaCodigo());
    }


    ////////////////////////////////SHOW ALL////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<ProfTurmaDTO>> getProfTurma() {


        var profTurmasFound = profTurmaService.getAll();
        if (profTurmasFound.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(ProfTurmaDTO.parseList(Optional.of(profTurmasFound)), HttpStatus.OK);
    }


    ///////////////////////////////////MODIFY CODIGO BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    public ResponseEntity<Void> updateProfTurma(@RequestParam("cpf") String cpf,
                                                @RequestParam("codigo") String codigo,
                                                @RequestBody ProfTurma incomingBody) {

        var pt = profTurmaService.find(cpf, codigo);
        if (pt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<Void> deleteProfTurma(@RequestParam("cpf") String cpf,
                                                @RequestParam("codigo") String codigo) {

        var profTurma = profTurmaService.find(cpf, codigo);
        if (profTurma.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var profId = profTurma.get().getProfessor().getId();
        var classId = profTurma.get().getTurma().getId();
        profTurmaService.deleteProfTurma(profId, classId);


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
