package br.com.escola.client;


import br.com.escola.client.dto.professor.ProfessorDTO;
import br.com.escola.client.entity.*;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.escola.client.tools.Json.*;
import static br.com.escola.client.tools.Json.indexClass.index;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ProfessorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setupH2() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(prof1);
        session.save(prof2);
        session.save(prof3);
        session.save(prof4);

        session.save(turma1);
        session.save(turma2);
        session.save(turma3);
        session.save(turma4);
        session.save(turma5);

        session.save(profTurma1_1);
        session.save(profTurma1_2);
        session.save(profTurma2_1);
        session.save(profTurma4_4);


        tx.commit();
        session.close();
    }

    @After
    public void resetH2() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("DELETE FROM PROF_TURMA").executeUpdate();
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

    private final Professor prof1 = new Professor(1L, "100", "Junin", null, null);
    private final Professor prof2 = new Professor(2L, "200", "Marquin", null, null);
    private final Professor prof3 = new Professor(3L, "300", "Marcin", null, null);
    private final Professor prof4 = new Professor(4L, "400", "Marlin", null, null);


    private final ProfTurma profTurma1_1 = new ProfTurma(1L, prof1, turma1);
    private final ProfTurma profTurma1_2 = new ProfTurma(1L, prof1, turma2);
    private final ProfTurma profTurma2_1 = new ProfTurma(1L, prof2, turma1);
    private final ProfTurma profTurma4_4 = new ProfTurma(1L, prof4, turma4);

    @Autowired
    ModelMapper modelMapper;

    //POST MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return create and dont change its id")
    public void saveProfessor_shouldReturnCreated() {
        //parsedId must be ignored by postMapping
        final Long passedId = 5123512346L;
        final String passedCpf = "500";
        final String passedNome = "AlunoTeste";
        final String passedEmail = "@teste";


        final Professor professorToCreateTest = new Professor(passedId, passedCpf, passedNome, passedEmail, null);
        mockMvc.perform(post("/professor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJson(professorToCreateTest)))
                .andExpect(status().isCreated());


        Session session = sessionFactory.openSession();
        Transaction fx = session.beginTransaction();
        var professorId =
                session.createSQLQuery("SELECT ID FROM PROFESSOR WHERE CPF = :cpf")
                        .setParameter("cpf", passedCpf).getResultList();

        var professorCpf =
                session.createSQLQuery("SELECT CPF FROM PROFESSOR WHERE CPF = :cpf")
                        .setParameter("cpf", passedCpf).getResultList();

        var professorNome =
                session.createSQLQuery("SELECT NOME FROM PROFESSOR WHERE CPF = :cpf")
                        .setParameter("cpf", passedCpf).getResultList();

        var professorEmail =
                session.createSQLQuery("SELECT EMAIL FROM PROFESSOR WHERE CPF = :cpf")
                        .setParameter("cpf", passedCpf).getResultList();

        fx.commit();
        session.close();


        boolean error = false;

        //Entity was just created, this list should not be empty
        if (professorId.isEmpty()) {
            error = true;

        } else {

            //Matricula should be unique, so that list must contain just one element
            if (professorCpf.size() > 1) {
                error = true;

            } else {
                //User should not be able to change Id, so Id that was parsed here should not match with his actual Id
                if (professorId.get(0).equals(passedId.toString())) {
                    error = true;
                }

                //Aluno matricula should match with value passed
                if (!professorCpf.get(0).equals(passedCpf)) {
                    error = true;
                }

                //Aluno nome should match with value passed
                if (!professorNome.get(0).equals(passedNome)) {
                    error = true;
                }

                //Aluno email should match with value passed
                if (!professorEmail.get(0).equals(passedEmail)) {
                    error = true;
                }


            }
        }

        Assertions.assertFalse(error);
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK and all professors registered in database")
    public void getProfessor_testsNoParam() {
        ArrayList<ProfessorDTO> professorDTOArrayList = new ArrayList<>();
        professorDTOArrayList.add(new ProfessorDTO(Optional.of(prof1)));
        professorDTOArrayList.add(new ProfessorDTO(Optional.of(prof2)));
        professorDTOArrayList.add(new ProfessorDTO(Optional.of(prof3)));
        professorDTOArrayList.add(new ProfessorDTO(Optional.of(prof4)));

        mockMvc.perform(get("/professor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJson(professorDTOArrayList)))
                .andExpect(status().isOk());

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK an empty list when there is no professors in database")
    public void getProfessor_testsNoParamWithoutData() {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("DELETE FROM PROF_TURMA").executeUpdate();
        session.createSQLQuery("DELETE FROM PROFESSOR").executeUpdate();
        tx.commit();
        session.close();


        mockMvc.perform(get("/professor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"))
                .andExpect(status().isOk());

    }


    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK a single professor if exists one in database")
    public void getProfessor_testsCpfParam() {
        ProfessorDTO professorDTO = new ProfessorDTO();
        modelMapper.map(prof1, professorDTO);

        mockMvc.perform(get("/professor?cpf=100"))
                .andExpect(content().json(toJson(professorDTO, index.singleArray)))
                .andExpect(status().isOk());

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return NotFound if does not match with none of database")
    public void getProfessor_testsCpfNonexistentParam() {
        mockMvc.perform(get("/professor?cpf=123"))
                .andExpect(status().isNotFound());

    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return all professors assigned to codigo param")
    public void getProfessor_testsCodigoParam() {
                mockMvc.perform(get("/professor?codigo=10"))
                .andExpect(content().json(
                        toJson(new ProfessorDTO(Optional.of(prof1)), index.begin)
                                + toJson(new ProfessorDTO(Optional.of(prof2)), index.end)))
                .andExpect(status().isOk());
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return Ok and empty list if codigo does not match with none of professors assigned")
    public void getProfessor_testsNonexistentCodigoParam() {
        mockMvc.perform(get("/professor?codigo=1123"))
                .andExpect(content().string("[]"))
                .andExpect(status().isOk());
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    public void getProfessor_CodigoPlusToCpfParam_ShouldReturnBadGatway() {
        mockMvc.perform(get("/professor?codigo=10&cpf=100"))
                .andExpect(content().string(""))
                .andExpect(status().isBadRequest());
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return Ok and all professors unassigned to a class if there is any")
    public void noClass_shouldAlwaysReturnOk(){
        mockMvc.perform(get("/professor/semTurma"))
                .andExpect(content().json(toJson(new ProfessorDTO(Optional.of(prof3)),index.singleArray)))
                .andExpect(status().isOk());
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return Ok and an empty list if all professors are asigned")
    public void noClass_AllProfessorsAssigned_shouldAlwaysReturnOk(){
        final ProfTurma profTurmaTest = new ProfTurma(1L, prof3, turma1);
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(profTurmaTest);
        tx.commit();
        session.close();

        mockMvc.perform(get("/professor/semTurma"))
                .andExpect(content().string("[]"))
                .andExpect(status().isOk());
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return NoContent if successfully delete from database")
    public void delete_validCpf(){
        mockMvc.perform(delete("/professor?cpf=100"))
                .andExpect(status().isNoContent());

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        var shouldBeEmpty =
                session.createSQLQuery("SELECT * FROM PROFESSOR WHERE CPF = 100").getResultList();
        tx.commit();
        session.close();
        Assertions.assertEquals(new ArrayList<>(), shouldBeEmpty);
    }

    //GET MAPPING
    @Test
    @SneakyThrows
    public void delete_nonexistentCpf_shouldReturnNotFound() {
        mockMvc.perform(delete("/professor?cpf=123"))
                .andExpect(status().isNotFound());
    }


    //PUT MAPPING
    @Test
    @SneakyThrows
    @DisplayName("Should return OK if successfully modify professor and notFound if it is not there")
    public void update() {
        final var professorModified = new Professor();
        final var professorModifiedDTO = new ProfessorDTO();
        final var idPassed = 23610349678341907L;
        final var namePassed = "pedro";
        final var emailPassed = "pedro@gmail";
        final var cpfPassed = "600";

        professorModified.setId(idPassed);
        professorModified.setNome(namePassed);
        professorModified.setEmail(emailPassed);
        professorModified.setCpf(cpfPassed);

        modelMapper.map(professorModified,professorModifiedDTO);

        mockMvc.perform(put("/professor?cpf=100")
                        .content(toJson(professorModifiedDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(put("/professor?cpf=100")
                        .content(toJson(professorModifiedDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        var actualId = session.createSQLQuery("SELECT ID FROM PROFESSOR WHERE CPF = 600")
                .getSingleResult().toString();
        var actualName = session.createSQLQuery("SELECT NOME FROM PROFESSOR WHERE CPF = 600")
                .getSingleResult().toString();
        var actualEmail = session.createSQLQuery("SELECT EMAIL FROM PROFESSOR WHERE CPF = 600")
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
