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
            value = "DELETE ProfTurma WHERE professor.id = :id")
    void deleteDependency(@Param("id") Long id);

    @Transactional
    @Query(
            value = "SELECT p FROM ProfTurma as p WHERE p.professor.cpf = :cpf AND p.turma.codigo = :codigo")
   ProfTurma find(@Param("cpf") String cpf, @Param("codigo") String codigo);



    @Transactional
    @Query(
            value = "SELECT pt FROM ProfTurma as pt WHERE professor.id = :idProf AND turma.id = :idTurma")
    List<ProfTurma> findById(@Param("idProf") Long idProf, @Param("idTurma") Long idTurma);

    @Transactional
    @Query(
            value = "SELECT pt FROM ProfTurma as pt WHERE professor.id = :idProf")
    Optional<List<ProfTurma>> findByProf(@Param("idProf") Long idProf);


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE ProfTurma pt SET pt.turma.id = :value WHERE " +
                    "pt.professor.id = :cpf AND " +
                    "pt.turma.id = :codigo")
    void modify(@Param("cpf") Long idAluno, @Param("codigo") Long idTurma, @Param("value") Long value);


    @Transactional
    @Modifying
    @Query(
            value = "DELETE ProfTurma WHERE " +
                    "turma.id = :idTurma AND " +
                    "professor.id = :idProf")
    void deleteTurma(@Param("idProf") Long idProf, @Param("idTurma") Long idTurma);


}


