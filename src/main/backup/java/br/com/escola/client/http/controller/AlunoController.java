package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Aluno;
import br.com.escola.client.service.AlunoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {


    @Autowired
    AlunoService alunoService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno SaveAluno(@RequestBody Aluno aluno) {
        return alunoService.Save(aluno);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> GetAluno() {
        return alunoService.GetAluno();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Aluno FindAluno(@PathVariable("id") Long id) {
        return alunoService.FindAluno(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteById(@PathVariable("id") Long id) {
        alunoService.FindAluno(id)
                .map(aluno -> {
                    alunoService.DeleteById(aluno.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UpdateAluno(@PathVariable("id") Long id, @RequestBody Aluno aluno) {
        alunoService.FindAluno(id)
                .map(alunoBase -> {
                    modelMapper.map(aluno, alunoBase);
                    alunoService.Save(alunoBase);
                    return Void.TYPE;

                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
