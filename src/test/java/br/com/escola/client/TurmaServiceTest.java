package br.com.escola.client;

import br.com.escola.client.entity.*;
import br.com.escola.client.repository.AlunoTurmaRepository;
import br.com.escola.client.repository.ProfTurmaRepository;
import br.com.escola.client.repository.TurmaRepository;
import br.com.escola.client.service.TurmaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
public class TurmaServiceTest {
    @Autowired
    TurmaService turmaService;

    @MockBean
    AlunoTurmaRepository alunoTurmaRepository;

    @MockBean
    ProfTurmaRepository profTurmaRepository;

    @MockBean
    TurmaRepository turmaRepository;


    @Before
    public void setup() {

        //Set entities
        Turma turma = new Turma(1L, "50",
                "tarde", null, null, null, null);

        Professor professor = new Professor(1L, "102", "marcio", null, null);
        Aluno aluno = new Aluno(1L, "102", "marcin", null, null);


        //Set relationship
        AlunoTurma alunoTurma = new AlunoTurma(aluno, turma);
        ProfTurma profTurma = new ProfTurma(professor, turma);




        //////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////Configure repositories/////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////

        Mockito.when(alunoTurmaRepository.getAllAlunoTurmaByMatricula("102"))
                .thenReturn(Optional.of(Collections.singletonList(alunoTurma)));

        Mockito.when(profTurmaRepository.getProfessorsAssignedByCpf("102"))
                .thenReturn(Optional.of(Collections.singletonList(profTurma)));

        Mockito.when(turmaRepository.findByCodigo("50"))
                .thenReturn(Optional.of(turma));

        Mockito.when(profTurmaRepository.getClassesAssignedToCpf("102")).
                thenReturn(Optional.of(Collections.singletonList(turma)));

    }

    @TestConfiguration
    static class TurmaServiceTestConfiguration {

        @Bean
        public TurmaService turmaService() {
            return new TurmaService();
        }
    }


    //Tests function that get a class code whose professor and student are both in it
    @Test
    public void testGetTurmaWhereStudentIsTaughtByProfessor() {
        String matricula = "102";
        String cpf = "102";


        var result =
                turmaService.getTurmaWhereStudentIsTaughtByProfessor(matricula, cpf);

        Assertions.assertEquals(result, Optional.of(Collections.singletonList(new Turma(1L, "50",
                "tarde", null, null, null, null))));
    }


    //Tests getting all classes assigned to a CPF
    @Test
    public void TestAllClassesAssignedToCpf() {
        //Cpf not assigned
        Mockito.when(profTurmaRepository.getClassesAssignedToCpf("1")).
                thenReturn(Optional.empty());

        //NOT NULL
        Optional<String> cpf = Optional.of("102");

        Turma turma = new Turma(1L, "50",
                "tarde", null, null, null, null);

        var turmaFound =  turmaService.allClassesAssignedToCpf(cpf);

        Assertions.assertEquals(turmaFound,Optional.of(Collections.singletonList(turma)));



        //Not assigned cpf
        Optional<String> cpfNotAssigned = Optional.of("1");
        var turmaFoundNull =  turmaService.allClassesAssignedToCpf(cpfNotAssigned);
        Assertions.assertEquals(turmaFoundNull,Optional.empty());

    }


    @Test
    public void TestFindTurmaIdBycodigo(){
        String codigo = "50";

        turmaService.findTurmaIdBycodigo(codigo);
    }



}
