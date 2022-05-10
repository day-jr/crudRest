package br.com.escola.client.http.controller;



import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/turma/prof")
public class ProfTurmaController {

    @Autowired
    ProfTurmaService profTurmaService;

    @Autowired
    ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfTurma SaveProfTurma(@RequestBody ProfTurma profTurma){
        return profTurmaService.Save(profTurma);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProfTurma> GetProfTurma(){
        return profTurmaService.GetProfTurma();
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  ProfTurma FindProfTurma(@PathVariable("id") Long id){
        return profTurmaService.FindProfTurma(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteById(@PathVariable("id") Long id){
        profTurmaService.FindProfTurma(id)
                .map(profTurma -> {
                    profTurmaService.DeleteProfTurmaById(profTurma.getId());
                    return Void.TYPE;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateProfTurma(@PathVariable("id") Long id, @RequestBody ProfTurma profTurma){
        profTurmaService.FindProfTurma(id)
                .map(profTurmaBase-> {
                    modelMapper.map(profTurma, profTurmaBase);
                    profTurmaService.Save(profTurmaBase);
                    return Void.TYPE;

                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
