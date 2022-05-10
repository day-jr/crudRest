package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Professor SaveProfessor(@RequestBody Professor professor){

        return professorService.Save(professor);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> GetProfessor(){
        return professorService.GetProfessor();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  Professor FindProfessor(@PathVariable("id") Long id){
        return professorService.FindProfessor(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteById(@PathVariable("id") Long id){
        professorService.FindProfessor(id)
                .map(professor -> {
                    professorService.DeleteById(professor.getId());
                return Void.TYPE;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateProfessor(@PathVariable("id") Long id, @RequestBody Professor professor){
        professorService.FindProfessor(id)
                .map(professorBase-> {
                    modelMapper.map(professor, professorBase);
                    professorService.Save(professorBase);
                    return Void.TYPE;

                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
