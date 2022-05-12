package br.com.escola.client.http.controller;



import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.service.ProfTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/turma/prof")
public class ProfTurmaController {

    @Autowired
    ProfTurmaService profTurmaService;

    @Autowired
    ModelMapper modelMapper;



    ////////////////////////////////SAVE////////////////////////////////////
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveComposite(@RequestBody ProfTurma profTurma){


         profTurmaService.saveComposite(profTurma);
    }





    ////////////////////////////////SHOW ALL////////////////////////////////////
    @GetMapping("/filter/{elemento}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getProfTurma(@PathVariable("elemento") String elemento){



        return profTurmaService.getProfTurma(elemento);
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  ProfTurma findProfTurma(@PathVariable("id"+"id") Long id1, Long id2){// "id1,id2"
        return profTurmaService.findProfTurma(id1,id2)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }
//
//
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void UpdateProfTurma(@PathVariable("id") String id, @RequestBody ProfTurma profTurma){
//        profTurmaService.findProfTurma(id)
//                .map(profTurmaBase-> {
//                    modelMapper.map(profTurma, profTurmaBase);
//                    profTurmaService.saveP(profTurmaBase);
//                    return Void.TYPE;
//
//                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

}
