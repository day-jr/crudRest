package br.com.escola.client.repository;

import br.com.escola.client.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Transactional
    @Query(
            value = "SELECT a FROM Aluno a WHERE a.matricula = :matricula ")
    Optional<Aluno> findByMatricula(@Param("matricula") String matricula);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE Aluno WHERE matricula = :matricula")
    void deleteByMatricula(@Param("matricula") String matricula);




}
