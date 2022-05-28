package br.com.escola.client;


import br.com.escola.client.entity.*;

import br.com.escola.client.dto.response.*;
import br.com.escola.client.dto.request.*;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

    private final AlunoTurma alunoTurma1_1 = new AlunoTurma(aluno1, turma1);
    private final AlunoTurma alunoTurma1_2 = new AlunoTurma(aluno1, turma2);
    private final AlunoTurma alunoTurma2_1 = new AlunoTurma(aluno2, turma1);
    private final AlunoTurma alunoTurma4_4 = new AlunoTurma(aluno4, turma4);

    private final ProfTurma profTurma1_1 = new ProfTurma(prof1, turma1);
    private final ProfTurma profTurma1_2 = new ProfTurma(prof1, turma2);
    private final ProfTurma profTurma1_3 = new ProfTurma(prof1, turma3);
    private final ProfTurma profTurma2_3 = new ProfTurma(prof2, turma3);


    //POST MAPPING
    @Test
    public void saveTurma_shouldReturnCreated() throws Exception {

        mockMvc.perform(post("/turma")
                        .content(toJson(turma5))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Autowired
    ModelMapper modelMapper;

    //GET MAPPING
    @SneakyThrows
    @Test
    public void getTurmas_testsEmptyCodigoParam_shouldReturnListOfClassesAndOk() {
        List<Turma> expectedClasses = new ArrayList<>();
        List<dtoGetTurma> expectedClassesParsed = new ArrayList<>();
        expectedClasses.add(turma4);
        expectedClasses.add(turma3);
        expectedClasses.add(turma2);
        expectedClasses.add(turma1);

        for(Turma entity: expectedClasses){
            dtoGetTurma turmaParsed = new dtoGetTurma();
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
    public void getTurmas_testsCpfParam_shouldReturnOk() {

        var parsedTurma1 = new dtoGetTurma();
        modelMapper.map(turma1,parsedTurma1);

        var parsedTurma3 = new dtoGetTurma();
        modelMapper.map(turma3,parsedTurma3);


        mockMvc.perform(get("/turma?cpf=100"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        toJson(parsedTurma1, index.begin) +
                                toJson(parsedTurma3, index.end)));

        mockMvc.perform(get("/turma?cpf=200"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(parsedTurma3, index.singleArray)));

        mockMvc.perform(get("/turma?cpf=300"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    public void getTurmas_testsCodigoParam_shouldReturnOKandNotFound() {

        var parsedTurma1 = new dtoGetTurma();
        modelMapper.map(turma1,parsedTurma1);

        mockMvc.perform(get("/turma?codigo=10"))
                .andExpect(content().json(toJson(parsedTurma1)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/turma?codigo=50000"))
                .andExpect(status().isNotFound());

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    public void getTurmas_testsCpfAndCodigoParam_shouldReturnOkAndNotFound() {
        var parsedTurma1 = new dtoGetTurma();
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
    public void getTurmas_testsFinishTimeParam_shouldReturnOk() {
        List<dtoGetTurma> turmasExpectedTest1 = new ArrayList<>();

        turmasExpectedTest1.add(new dtoGetTurma(Optional.of(turma4)));
        turmasExpectedTest1.add(new dtoGetTurma(Optional.of(turma3)));
        turmasExpectedTest1.add(new dtoGetTurma(Optional.of(turma2)));
        turmasExpectedTest1.add(new dtoGetTurma(Optional.of(turma1)));

        List<dtoGetTurma> turmasExpectedTest2 = new ArrayList<>();
        turmasExpectedTest2.add(new dtoGetTurma(Optional.of(turma3)));
        turmasExpectedTest2.add(new dtoGetTurma(Optional.of(turma2)));
        turmasExpectedTest2.add(new dtoGetTurma(Optional.of(turma1)));

        var parsedTurma1 = new dtoGetTurma(Optional.of(turma1));

        mockMvc.perform(get("/turma?finishAfter=20:00:00"))
                .andExpect(content().json(toJson(turmasExpectedTest1)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/turma?finishAfter=4:00:00"))
                .andExpect(content().json(toJson(turmasExpectedTest2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/turma?finishAfter=2:00:00"))
                .andExpect(content().json(toJson(parsedTurma1, index.singleArray)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/turma?finishAfter=1:00:00"))
                .andExpect(content().json("[]"))
                .andExpect(status().isOk());


    }


    //DELETE MAPPING
    @SneakyThrows
    @Test
    public void deleteByCodigo_shouldReturnNoContentAndNotFound() {

        mockMvc.perform(delete("/turma?codigo=10"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/turma?codigo=50000"))
                .andExpect(status().isNotFound());

    }


    //PUT MAPPING
    @Test
    @SneakyThrows
    public void updateTurma_shouldReturnNotFound_Ok_Ok_Ok() {
        dtoGetTurma turmaMod = new dtoGetTurma();
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
