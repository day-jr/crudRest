package br.com.escola.client.http.controller;



import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfTurmaService;
import br.com.escola.client.service.TurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/turma/prof")
public class ProfTurmaController {



    @Autowired
    ProfTurmaService profTurmaService;

    @Autowired
    TurmaService turmaService;

    @Autowired
    ModelMapper modelMapper;



    ////////////////////////////////SAVE////////////////////////////////////
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompositeProf(@RequestBody ProfTurma profTurma){
         profTurmaService.saveComposite(profTurma);

    }





    ////////////////////////////////SHOW ALL////////////////////////////////////
    @GetMapping("/filter/{elemento}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getProfTurma(@PathVariable("elemento") String elemento){



        return profTurmaService.getProfTurma(elemento);
    }



    ////////////////////////////////SEARCH X WHERE Y = Z////////////////////////////////////
    @GetMapping("/search/{elemento}By{atributo}={value}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<List<String>> findProfTurma(@PathVariable("elemento") String elemento,
                                                @PathVariable("atributo") String atributo,
                                                @PathVariable("value") String value)
    {return profTurmaService.findProfTurma(elemento,atributo,value);}


    ///////////////////////////////////MODIFY CODIGO BY CPF
    ////////////////////////////////////////////////////////////////////
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfTurma(@RequestParam("cpf") String cpf,@RequestParam("codigo") String codigo ,
                                @RequestBody ProfTurma incomingBody){

        ProfTurma pt = profTurmaService.find(cpf,codigo);
        profTurmaService.deleteProfTurma(pt.getProfessor().getId(), pt.getTurma().getId());
        modelMapper.map(incomingBody, pt);
        profTurmaService.saveComposite(pt);

    }




    ///////////////////////////////////DELETE CODIGO BY MATRICULA
    ////////////////////////////////////////////////////////////////////
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfTurma(@RequestParam("cpf") String cpf, @RequestParam("codigo") String codigo){

        var profTurma = profTurmaService.find(cpf,codigo);
        var profId= profTurma.getProfessor().getId();
        var turmaId = profTurma.getTurma().getId();
        profTurmaService.deleteProfTurma(profId,turmaId);


    }





}
