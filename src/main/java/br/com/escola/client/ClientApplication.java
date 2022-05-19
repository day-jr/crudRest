package br.com.escola.client;


import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.FileNotFoundException;


@SpringBootApplication
@RestController
public class ClientApplication {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);


    }

//FEITO - criar, listar, alterar e deletar alunos
//
//FEITO - criar, listar, alterar e deletar turmas
//
//FEITO - criar, listar, alterar e deletar professores
//
//FEITO - listar todos os alunos da turma de código 2002
//
//FEITO - listar todas as turmas em que o professor Alberto Roberto está designado para dar aula
//
//FEITO - listar as turmas sem professor designado
//
//FEITO - listar os professores designados para mais de 2 turmas
//
//FEITO - listar as turmas que têm mais de 1 professor designado
//
//- listar turmas com menos de 3 alunos
//
//- listar a turma em que o aluno Boto Rosa tem aula com o professor Chicanel
//
//- listar as turmas que terminam a aula após as 23:00h

}
