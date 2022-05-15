package br.com.escola.client.http.controller;


import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.AlunoService;
import br.com.escola.client.service.AlunoTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    AlunoTurmaService alunoTurmaService;

    @Autowired
    AlunoService alunoService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno saveAluno(@RequestBody Aluno aluno, AlunoTurma alunoTurma){

        return alunoService.save(aluno);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> getAluno(){
        return alunoService.getAluno();
    }



    ///////////////////////////////////GET BY CPF
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/search/{matricula}")
    @ResponseStatus(HttpStatus.OK)
    public Aluno findAluno(@PathVariable("matricula") String matricula){

        return alunoService.findByMatricula(matricula);

    }


    ///////////////////////////////////DELETE BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByMatricula(@RequestParam("matricula") String matricula){
        var a = alunoService.findByMatricula(matricula).getId();

        alunoService.deleteDependency(a);
        alunoService.deleteAlunoByMatricula(matricula);
    }



    ///////////////////////////////////MODIFY BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAluno(@RequestParam("matricula") String matricula, @RequestBody Aluno incomingBody){

        Aluno a = alunoService.findByMatricula(matricula);
        modelMapper.map(incomingBody, a);
        alunoService.save(a);

    }


}
