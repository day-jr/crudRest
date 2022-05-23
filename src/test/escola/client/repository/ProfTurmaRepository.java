package br.com.escola.client.repository;


import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProfTurmaRepository extends JpaRepository<ProfTurma, Long> {
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO PROF_TURMA (FK_CPF,FK_CODIGO) " +
                    "VALUES (:professor,:turma)",
            nativeQuery = true)
    void saveComposite(@Param("professor") Long cpf, @Param("turma") Long codigo);


    @Transactional
    @Query(
            value =  "SELECT p.professor FROM ProfTurma as p " +
            "WHERE  p.turma.codigo  = :codigo")
    Optional<List<Professor>> findByTurma(@Param("codigo") String codigo);

    @Transactional
    @Query(
            value = "SELECT t FROM ProfTurma as t " +
                    "WHERE t.professor.cpf = :cpf ")
    Optional<List<ProfTurma>> getProfessorsAssignedByCpf(@Param("cpf") String cpf);

    @Transactional
    @Query(
            value = "SELECT t.turma FROM ProfTurma as t " +
                    "WHERE t.professor.cpf = :cpf ")
    Optional<List<Turma>> getClassesAssignedToCpf(@Param("cpf") String cpf);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE PROF_TURMA WHERE PROF_TURMA.FK_CPF = :cpf", nativeQuery = true)
    void deleteDependency(@Param("cpf") Long cpf);

    @Transactional
    @Query(
            value = "SELECT p FROM ProfTurma as p WHERE p.professor.cpf = :cpf AND p.turma.codigo = :codigo")
   ProfTurma find(@Param("cpf") String cpf, @Param("codigo") String codigo);



    @Transactional
    @Query(
            value = "SELECT * FROM PROF_TURMA WHERE FK_CPF = :idProf AND FK_CODIGO = :idTurma", nativeQuery = true)
    List<ProfTurma> findById(@Param("idProf") Long idProf, @Param("idTurma") Long idTurma);

    @Transactional
    @Query(
            value = "SELECT * FROM PROF_TURMA WHERE FK_CPF = :idProf", nativeQuery = true)
    Optional<List<ProfTurma>> findByProf(@Param("idProf") Long idProf);


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


