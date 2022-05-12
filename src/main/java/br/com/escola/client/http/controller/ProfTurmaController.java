package br.com.escola.client.http.controller;



import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.service.ProfTurmaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
    public List<String> GetProfTurma(@PathVariable("elemento") String elemento){
        var profTurma = profTurmaService.getProfTurma();
        List<String> elementos = new ArrayList<>();
        var cont =0;


        for (ProfTurma pt:profTurma) {
            switch(elemento){
                case "nome":
                    elementos.add(pt.getCpf().getNome());
                    cont++;
                    break;


                case "cpf":
                    elementos.add(pt.getCpf().getCpf());
                    cont++;
                    break;


                case "codigo":
                    elementos.add(pt.getCodigo().getCodigo());
                    cont++;
                    break;

            }
        }

        return elementos;
    }


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
