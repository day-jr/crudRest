package br.com.escola.client.http.controller;



import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.service.ProfTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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


//
//
//
//    ////////////////////////////////SHOW ALL////////////////////////////////////
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<ProfTurma> GetProfTurma(){
//
//        return profTurmaService.getProfTurma();
//    }
//
//
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public  ProfTurma FindProfTurma(@PathVariable("id") String id){
//        return profTurmaService.findProfTurma(id)
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//    }
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
