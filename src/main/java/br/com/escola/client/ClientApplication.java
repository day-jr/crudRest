package br.com.escola.client;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;



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


//
//- listar a turma em que o aluno Boto Rosa tem aula com o professor Chicanel
//
//- listar as turmas que terminam a aula após as 23:00h

}
