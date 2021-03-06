package br.com.escola.client;


import br.com.escola.client.dto.aluno.AlunoDTO;
import br.com.escola.client.dto.turma.TurmaDTO;
import br.com.escola.client.entity.*;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.escola.client.tools.Json.indexClass.index;
import static br.com.escola.client.tools.Json.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(controllers = TurmaController.class)
public class TurmaControllerTest {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    MockMvc mockMvc;


    @Before
    public void setupH2() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(turma4);
        session.save(turma3);
        session.save(turma2);
        session.save(turma1);

        session.save(prof1);
        session.save(prof2);
        session.save(prof3);

        session.save(aluno1);
        session.save(aluno2);
        session.save(aluno3);
        session.save(aluno4);

        session.save(profTurma1_1);
        session.save(profTurma1_3);
        session.save(profTurma2_3);

        session.save(alunoTurma1_1);
        session.save(alunoTurma2_1);
        session.save(alunoTurma4_4);

        tx.commit();
        session.close();

    }

    @After
    public void resetH2() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("DELETE FROM ALUNO_TURMA").executeUpdate();
        session.createSQLQuery("DELETE FROM PROF_TURMA").executeUpdate();
        session.createSQLQuery("DELETE FROM ALUNO").executeUpdate();
        session.createSQLQuery("DELETE FROM PROFESSOR").executeUpdate();
        session.createSQLQuery("DELETE FROM TURMA").executeUpdate();
        tx.commit();
        session.close();
    }


//    @MockBean
//    TurmaService turmaService;

    private final Turma turma1 = new Turma(1L, "10", null, Time.valueOf("1:00:00"), Time.valueOf("1:00:00"), null, null);
    private final Turma turma2 = new Turma(2L, "20", null, Time.valueOf("2:00:00"), Time.valueOf("1:00:00"), null, null);
    private final Turma turma3 = new Turma(3L, "30", null, Time.valueOf("3:00:00"), Time.valueOf("1:00:00"), null, null);
    private final Turma turma4 = new Turma(4L, "40", null, Time.valueOf("4:00:00"), Time.valueOf("1:00:00"), null, null);
    private final Turma turma5 = new Turma(5L, "60", null, Time.valueOf("4:00:00"), Time.valueOf("1:00:00"), null, null);


    private final Aluno aluno1 = new Aluno(1L, "100", "Junin", null, null);
    private final Aluno aluno2 = new Aluno(2L, "200", "Marquin", null, null);
    private final Aluno aluno3 = new Aluno(3L, "300", "Marcin", null, null);
    private final Aluno aluno4 = new Aluno(4L, "400", "Marlin", null, null);


    private final Professor prof1 = new Professor(1L, "100", "Junior", null, null);
    private final Professor prof2 = new Professor(2L, "200", "Marcos", null, null);
    private final Professor prof3 = new Professor(3L, "300", "Marcio", null, null);
    private final Professor prof4 = new Professor(4L, "400", "Marlon", null, null);

    private final AlunoTurma alunoTurma1_1 = new AlunoTurma(1L,aluno1, turma1);
    private final AlunoTurma alunoTurma1_2 = new AlunoTurma(1L,aluno1, turma2);
    private final AlunoTurma alunoTurma2_1 = new AlunoTurma(1L,aluno2, turma1);
    private final AlunoTurma alunoTurma4_4 = new AlunoTurma(1L,aluno4, turma4);

    private final ProfTurma profTurma1_1 = new ProfTurma(1L,prof1, turma1);
    private final ProfTurma profTurma1_2 = new ProfTurma(2L,prof1, turma2);
    private final ProfTurma profTurma1_3 = new ProfTurma(3L,prof1, turma3);
    private final ProfTurma profTurma2_3 = new ProfTurma(4L,prof2, turma3);


    //POST MAPPING
    @Test
    @DisplayName("Should return Created when successfully creating a class in database")
    public void saveTurma_testsPostMapping() throws Exception {

        mockMvc.perform(post("/turma")
                        .content(toJson(turma5))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK an empty list when there is no classes in database")
    public void getTurmas_testsNoParamWithoutData(){

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("DELETE FROM ALUNO_TURMA").executeUpdate();
        session.createSQLQuery("DELETE FROM PROF_TURMA").executeUpdate();
        session.createSQLQuery("DELETE FROM TURMA").executeUpdate();
        tx.commit();
        session.close();


        mockMvc.perform(get("/turma")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"))
                .andExpect(status().isOk());

    }

    @Autowired
    ModelMapper modelMapper;

    //GET MAPPING
    @SneakyThrows
    @Test
    @DisplayName("Should return OK and a list of classes(turma1,turma2,turma3,turma4)")
    public void getTurmas_testsEmptyCodigoParam() {
        List<Turma> expectedClasses = new ArrayList<>();
        List<TurmaDTO> expectedClassesParsed = new ArrayList<>();
        expectedClasses.add(turma4);
        expectedClasses.add(turma3);
        expectedClasses.add(turma2);
        expectedClasses.add(turma1);

        for(Turma entity: expectedClasses){
            TurmaDTO turmaParsed = new TurmaDTO();
            modelMapper.map(entity,turmaParsed);
            expectedClassesParsed.add(turmaParsed);

        }

        mockMvc.perform(get("/turma"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(expectedClassesParsed)));
    }


    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK and a list (turma1,turma3")
    public void getTurmas_testsCpfParamWithMultipleResults() {

        var parsedTurma1 = new TurmaDTO();
        modelMapper.map(turma1,parsedTurma1);

        var parsedTurma3 = new TurmaDTO();
        modelMapper.map(turma3,parsedTurma3);


        mockMvc.perform(get("/turma?cpf=100"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        toJson(parsedTurma1, index.begin) +
                                toJson(parsedTurma3, index.end)));


    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK and turma3")
    public void getTurmas_testsCpfParamWithSingleResult() {

        var parsedTurma3 = new TurmaDTO();
        modelMapper.map(turma3,parsedTurma3);

        mockMvc.perform(get("/turma?cpf=200"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(parsedTurma3, index.singleArray)));

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK and an empty list when cpf value does not exist in database")
    public void getTurmas_testsCpfParamWithEmptyResult() {


        mockMvc.perform(get("/turma?cpf=300"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK and a single class when codigo value exists in database ")
    public void getTurmas_testsCodigoParam() {

        var parsedTurma1 = new TurmaDTO();
        modelMapper.map(turma1,parsedTurma1);

        mockMvc.perform(get("/turma?codigo=10"))
                .andExpect(content().json(toJson(parsedTurma1,index.singleArray)))
                .andExpect(status().isOk());

    }


    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return NotFound when codigo value does not exist in database ")
    public void getTurmas_testsNonexistentCodigoParam() {


        mockMvc.perform(get("/turma?codigo=50000"))
                .andExpect(status().isNotFound());

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should always return Ok and, when assigned, return this class. Else return an Empty List")
    public void getTurmas_testsMatriculaAndCpfParam_shouldReturnOk() {
        var parsedTurma1 = new TurmaDTO();
        modelMapper.map(turma1,parsedTurma1);
        mockMvc.perform(get("/turma?cpf=100&matricula=100"))
                .andExpect(content().json(toJson(parsedTurma1, index.singleArray)))
                .andExpect(status().isOk());


        mockMvc.perform(get("/turma?cpf=100&matricula=200"))
                .andExpect(content().json(toJson(parsedTurma1, index.singleArray)))
                .andExpect(status().isOk());


        mockMvc.perform(get("/turma?cpf=100&matricula=400"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

    }



    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should always return Ok and all classes that finish it class after the time inputted")
    public void getTurmas_testsFinishTimeParam_shouldReturnOk() {
        List<TurmaDTO> turmasExpectedTest1 = new ArrayList<>();
        turmasExpectedTest1.add(new TurmaDTO(Optional.of(turma4)));
        turmasExpectedTest1.add(new TurmaDTO(Optional.of(turma3)));
        turmasExpectedTest1.add(new TurmaDTO(Optional.of(turma2)));
        turmasExpectedTest1.add(new TurmaDTO(Optional.of(turma1)));

        List<TurmaDTO> turmasExpectedTest2 = new ArrayList<>();
        turmasExpectedTest2.add(new TurmaDTO(Optional.of(turma3)));
        turmasExpectedTest2.add(new TurmaDTO(Optional.of(turma4)));

        List<TurmaDTO> turmasExpectedTest3 = new ArrayList<>();
        turmasExpectedTest3.add(new TurmaDTO(Optional.of(turma4)));
        turmasExpectedTest3.add(new TurmaDTO(Optional.of(turma3)));
        turmasExpectedTest3.add(new TurmaDTO(Optional.of(turma2)));


        mockMvc.perform(get("/turma?finishAfter=20:00:00"))
                .andExpect(content().string("[]"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/turma?finishAfter=4:00:00"))
                .andExpect(content().json(toJson(turmasExpectedTest2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/turma?finishAfter=3:00:00"))
                .andExpect(content().json(toJson(turmasExpectedTest3)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/turma?finishAfter=1:00:00"))
                .andExpect(content().json(toJson(turmasExpectedTest1)))
                .andExpect(status().isOk());


    }


    //DELETE MAPPING
    @SneakyThrows
    @Test
    @DisplayName("Should return no content when successfully deleting a class")
    public void deleteByCodigo_existingClass() {

        mockMvc.perform(delete("/turma?codigo=10"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/turma?codigo=50000"))
                .andExpect(status().isNotFound());

    }

    //DELETE MAPPING
    @SneakyThrows
    @Test
    @DisplayName("Should return notFound")
    public void deleteByCodigo_nonexistingClass() {

        mockMvc.perform(delete("/turma?codigo=50000"))
                .andExpect(status().isNotFound());

    }


    //PUT MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return Ok when successfully modifying a class and notFound if its not there")
    public void updateTurma_testsUpdatingClass() {
        TurmaDTO turmaMod = new TurmaDTO();
        turmaMod.setCodigo("50");
        turmaMod.setTurno("noite");


        mockMvc.perform(put("/turma?codigo=50")
                        .content(toJson(turmaMod))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        mockMvc.perform(put("/turma?codigo=10")
                        .content(toJson(turmaMod))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(put("/turma?codigo=50")
                        .content(toJson(turmaMod))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


}
