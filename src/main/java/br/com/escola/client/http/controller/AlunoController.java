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
import java.util.Optional;

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





    ///////////////////////////////////DELETE BY CPF
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping("/{matricula}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteById(@PathVariable("matricula") String matricula){
        alunoService.deleteAlunoByMatricula(matricula);
    }




    ///////////////////////////////////MODIFY BY ID
    ////////////////////////////////////////////////////////////////////
    @PutMapping("/{matricula}/{element}={value}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Aluno updateAluno(@PathVariable("matricula") String matricula, @PathVariable("element") String element,
                                     @PathVariable("value") String value, @RequestBody Aluno aluno){
        Aluno a = alunoService.findByMatricula(matricula);
        switch(element){
            case "matricula":
                a.setMatricula(value);
                break;

            case "nome":
                a.setNome(value);
                break;

            case "email":
                a.setEmail(value);
                break;

        }

        return alunoService.save(a);
    }


}
