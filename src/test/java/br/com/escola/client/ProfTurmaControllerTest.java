package br.com.escola.client;


import br.com.escola.client.dto.profTurma.ProfTurmaDTO;
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

import static br.com.escola.client.tools.Json.indexClass.index;
import static br.com.escola.client.tools.Json.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ProfTurmaControllerTest {

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


    @Autowired
    ModelMapper modelMapper;

    //POST MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return Created when successfully creating a ProfTurma in database")
    public void saveProfTurma_testsPostMapping(){
        ProfTurmaDTO profTurmaDTO = new ProfTurmaDTO();
        modelMapper.map(profTurma1_2,profTurmaDTO);

        mockMvc.perform(post("/turmaProf")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(profTurmaDTO)))
                .andExpect(status().isCreated());

    }



    @Test
    @SneakyThrows
    @DisplayName("Should return Created when successfully creating a ProfTurma in database")
    public void getProfTurma_testsPostMapping(){
        List<ProfTurmaDTO> profTurmaDTOList = new ArrayList<>();
        profTurmaDTOList.add(new ProfTurmaDTO(Optional.of(profTurma1_1)));
        profTurmaDTOList.add(new ProfTurmaDTO(Optional.of(profTurma1_2)));
        profTurmaDTOList.add(new ProfTurmaDTO(Optional.of(profTurma1_3)));

        mockMvc.perform(get("/turmaProf?numberMinOfClasses=3"))
                .andExpect(content().json(toJson(profTurmaDTOList)))
                .andExpect(status().isOk());
    }




}
