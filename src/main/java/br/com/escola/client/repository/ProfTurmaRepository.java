package br.com.escola.client.repository;

import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ProfTurmaRepository extends JpaRepository <ProfTurma, Long> {
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO PROF_TURMA (FK_CPF,FK_CODIGO) " +
                    "VALUES (:professor,:turma)",
            nativeQuery = true)
    void saveComposite(@Param("professor") Long cpf, @Param("turma") Long codigo);




    @Transactional
    @Query(
            value = "SELECT p FROM ProfTurma as p WHERE p.professor.cpf = :cpf AND p.turma.codigo = :codigo")
    ProfTurma find(@Param("cpf") String cpf, @Param("codigo") String codigo);



    @Transactional
    @Query(
            value = "SELECT * FROM PROF_TURMA WHERE FK_CPF = :idProf AND FK_CODIGO = :idTurma", nativeQuery = true)
    ProfTurma findById(@Param("idProf") Long idAluno, @Param("idTurma") Long idTurma);



    @Transactional
    @Modifying
    @Query(
            value = "UPDATE PROF_TURMA SET FK_CODIGO = :value WHERE " +
                    "FK_CPF = :cpf AND FK_CODIGO = :codigo", nativeQuery = true)
    void modify(@Param("cpf") Long idAluno, @Param("codigo") Long idTurma, @Param("value") Long value);



    @Transactional
    @Modifying
    @Query(
            value = "DELETE PROF_TURMA WHERE " +
                    "FK_CODIGO = :idTurma and " +
                    "FK_CPF= :idProf", nativeQuery = true)
    void deleteTurma(@Param("idProf") Long idProf, @Param("idTurma") Long idTurma);


}


