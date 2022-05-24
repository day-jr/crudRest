package br.com.escola.client;


import br.com.escola.client.entity.*;


import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static br.com.escola.client.tools.Json.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

//    @MockBean
//    TurmaService turmaService;

    private final Turma turma1 = new Turma(1L, "10", null, null, null, null, null);
    private final Turma turma2 = new Turma(2L, "20", null, null, null, null, null);
    private final Turma turma3 = new Turma(3L, "30", null, null, null, null, null);
    private final Turma turma4 = new Turma(4L, "40", null, null, null, null, null);

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

    private final ProfTurma profTurma1_1 = new ProfTurma(prof1, turma1);
    private final ProfTurma profTurma1_2 = new ProfTurma(prof1, turma2);
    private final ProfTurma profTurma1_3 = new ProfTurma(prof1, turma3);
    private final ProfTurma profTurma2_3 = new ProfTurma(prof2, turma3);




    @Test
    public void saveTurma_shouldReturnCreated() throws Exception {

        mockMvc.perform(post("/turma")
                        .content(toJson(turma1, false))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }


    @SneakyThrows
    @Test
    public void getTurmas_shouldReturnListOfClasses() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(turma4);
        session.save(turma3);
        session.save(turma2);
        session.save(turma1);
        tx.commit();
        session.close();


        mockMvc.perform(get("/turma"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        toJson(turma4, false) + "," +
                        toJson(turma3, false) + "," +
                        toJson(turma2, false) + "," +
                        toJson(turma1, false) + "]"));

    }


    @Test
    @SneakyThrows
    public void getTurmas_TestsCpfParam() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(prof1);
        session.save(prof2);
        session.save(prof3);
        session.save(turma1);
        session.save(turma3);
        session.save(profTurma1_1);
        session.save(profTurma1_3);
        session.save(profTurma2_3);
        tx.commit();
        session.close();



        mockMvc.perform(get("/turma?cpf=100"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        toJson(turma1, false) + "," +
                        toJson(turma3, false) + "]"));


        mockMvc.perform(get("/turma?cpf=200"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(turma3, true)));

        mockMvc.perform(get("/turma?cpf=300"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));


    }



}
