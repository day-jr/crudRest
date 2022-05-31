package br.com.escola.client;


import br.com.escola.client.dto.aluno.AlunoDTO;
import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.tools.Json.indexClass.index;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static br.com.escola.client.tools.Json.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setupH2() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(aluno1);
        session.save(aluno2);
        session.save(aluno3);
        session.save(aluno4);

        session.save(turma1);
        session.save(turma2);
        session.save(turma3);
        session.save(turma4);
        session.save(turma5);

        session.save(alunoTurma1_1);
        session.save(alunoTurma1_2);
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
        session.createSQLQuery("DELETE FROM ALUNO").executeUpdate();
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


    private final AlunoTurma alunoTurma1_1 = new AlunoTurma(1L,aluno1, turma1);
    private final AlunoTurma alunoTurma1_2 = new AlunoTurma(1L,aluno1, turma2);
    private final AlunoTurma alunoTurma2_1 = new AlunoTurma(1L,aluno2, turma1);
    private final AlunoTurma alunoTurma4_4 = new AlunoTurma(1L,aluno4, turma4);

    @Autowired
    ModelMapper modelMapper;

    //GET MAPPING
    @Test
    @SneakyThrows
    public void getAluno_testsNoParameter_shouldReturnAllStudendsAndReturnOk() {
        List<Aluno> students = new ArrayList<>();
        List<AlunoDTO> studentsParsed = new ArrayList<>();
        students.add(aluno1);
        students.add(aluno2);
        students.add(aluno3);
        students.add(aluno4);


        for(Aluno entity: students){
            AlunoDTO alunoParsed = new AlunoDTO();
            modelMapper.map(entity,alunoParsed);
            studentsParsed.add(alunoParsed);

        }

        mockMvc.perform(get("/aluno"))
                .andExpect(content().json(toJson(studentsParsed)))
                .andExpect(status().isOk());
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    public void getAluno_testsMatriculaParameter_shouldReturnAlunoAndOK_NotFound() {
        var parsedStudent = new AlunoDTO();
        modelMapper.map(aluno1,parsedStudent);



        mockMvc.perform(get("/aluno?matricula=100"))
                .andExpect(content().json(toJson(parsedStudent,index.singleArray)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/aluno?matricula=123"))
                .andExpect(content().string(""))
                .andExpect(status().isNotFound());
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    public void getAluno_testsCodigoParameter_shouldReturnAlunoAndOK_NotFound() {

        var parsedStudent1 = new AlunoDTO();
        modelMapper.map(aluno1,parsedStudent1);

        var parsedStudent2 = new AlunoDTO();
        modelMapper.map(aluno2,parsedStudent2);

        var parsedStudent4 = new AlunoDTO();
        modelMapper.map(aluno4,parsedStudent4);



        mockMvc.perform(get("/aluno?codigo=10"))
                .andExpect(content().json(
                        toJson(parsedStudent1, index.begin)
                                + toJson(parsedStudent2, index.end)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/aluno?codigo=40"))
                .andExpect(content().json(toJson(parsedStudent4, index.singleArray)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/aluno?codigo=50"))
                .andExpect(content().json("[]"))
                .andExpect(status().isOk());

    }

    //POST MAPPING
    @Test
    @SneakyThrows
    public void saveAluno_shouldReturnCreated() {
        //parsedId must be ignored by postMapping
        final Long passedId = 5123512346L;
        final String passedMatricula = "500";
        final String passedNome = "AlunoTeste";
        final String passedEmail = "@teste";


        final Aluno alunoToCreateTest = new Aluno(passedId, passedMatricula, passedNome, passedEmail, null);
        mockMvc.perform(post("/aluno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJson(alunoToCreateTest)))
                .andExpect(status().isCreated());


        Session session = sessionFactory.openSession();
        Transaction fx = session.beginTransaction();
        var alunoId =
                session.createSQLQuery("SELECT ID FROM ALUNO WHERE MATRICULA = :matricula")
                        .setParameter("matricula", passedMatricula).getResultList();

        var alunoMatricula =
                session.createSQLQuery("SELECT MATRICULA FROM ALUNO WHERE MATRICULA = :matricula")
                        .setParameter("matricula", passedMatricula).getResultList();

        var alunoNome =
                session.createSQLQuery("SELECT NOME FROM ALUNO WHERE MATRICULA = :matricula")
                        .setParameter("matricula", passedMatricula).getResultList();

        var alunoEmail =
                session.createSQLQuery("SELECT EMAIL FROM ALUNO WHERE MATRICULA = :matricula")
                        .setParameter("matricula", passedMatricula).getResultList();


        fx.commit();
        session.close();


        boolean error = false;

        //Entity was just created, this list should not be empty
        if (alunoId.isEmpty()) {
            error = true;

        } else {

            //Matricula should be unique, so that list must contain just one element
            if (alunoMatricula.size() > 1) {
                error = true;

            } else {
                //User should not be able to change Id, so Id that was parsed here should not match with his actual Id
                if (alunoId.get(0).equals(passedId.toString())) {
                    error = true;
                }

                //Aluno matricula should match with value passed
                if (!alunoMatricula.get(0).equals(passedMatricula)) {
                    error = true;
                }

                //Aluno nome should match with value passed
                if (!alunoNome.get(0).equals(passedNome)) {
                    error = true;
                }

                //Aluno email should match with value passed
                if (!alunoEmail.get(0).equals(passedEmail)) {
                    error = true;
                }


            }
        }

        Assertions.assertFalse(error);
    }

    //DELETE MAPPING
    @Test
    @SneakyThrows
    public void deleteByMatricula_shouldReturnNoContentAndNotFound() {

        mockMvc.perform(delete("/aluno?matricula=100"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/aluno?matricula=150"))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/aluno?matricula=100"))
                .andExpect(status().isNotFound());


        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        var shouldBeEmpty =
                session.createSQLQuery("SELECT * FROM ALUNO WHERE MATRICULA = :matricula")
                        .setParameter("matricula", "100").getResultList();
        tx.commit();
        session.close();

        final ArrayList<Object> empty = new ArrayList<>();

        Assertions.assertEquals(empty, shouldBeEmpty);


    }

    //PUT MAPPING
    @Test
    @SneakyThrows
    public void updateAluno_shouldReturnOk_NotFound_NotFound() {
        final var studentModified = new Aluno();
        final var studentModifiedDTO = new AlunoDTO();
        final var idPassed = 23610349678341907L;
        final var namePassed = "pedro";
        final var emailPassed = "pedro@gmail";
        final var matriculaPassed = "600";

        studentModified.setId(idPassed);
        studentModified.setNome(namePassed);
        studentModified.setEmail(emailPassed);
        studentModified.setMatricula(matriculaPassed);

        modelMapper.map(studentModified,studentModifiedDTO);

        mockMvc.perform(put("/aluno?matricula=100")
                        .content(toJson(studentModifiedDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(put("/aluno?matricula=100")
                        .content(toJson(studentModifiedDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        var actualId = session.createSQLQuery("SELECT ID FROM ALUNO WHERE MATRICULA = 600")
                .getSingleResult().toString();
        var actualName = session.createSQLQuery("SELECT NOME FROM ALUNO WHERE MATRICULA = 600")
                .getSingleResult().toString();
        var actualEmail = session.createSQLQuery("SELECT EMAIL FROM ALUNO WHERE MATRICULA = 600")
                .getSingleResult().toString();
        tx.commit();
        session.close();

        var error = false;

        //Should not be able to modify Id
        if (actualId.equals(String.valueOf(idPassed))) {
            error = true;
        }

        //Name should have been modified
        if (!actualName.equals(namePassed)) {
            error = true;
        }

        //Email should have been modified
        if (!actualEmail.equals(emailPassed)) {
            error = true;
        }

        Assertions.assertFalse(error);


    }

}
