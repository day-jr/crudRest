package br.com.escola.client.repository;


import br.com.escola.client.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    @Transactional
    @Query(
            value = "SELECT t FROM Turma as t WHERE t.codigo = :codigo")
    Optional<Turma> findByCodigo(@Param("codigo") String codigo);



    @Transactional
    @Modifying
    @Query(value = "DELETE ProfTurma WHERE turma.id = :id ")
    void deleteDependencyProfturma(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE AlunoTurma WHERE turma.id = :id")
    void deleteDependencyAlunoTurma(@Param("id") Long id);

    @Transactional
    @Query(
            value = "SELECT t FROM Turma as t WHERE ( t.inicio + t.duracao ) >= :limitTime ")
    Optional<List<Turma>> limitByTime(@Param("limitTime") Time limitTime);

}
