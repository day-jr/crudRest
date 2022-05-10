package br.com.escola.client.repository;

import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProfTurmaRepository extends JpaRepository <ProfTurma, Long> {

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO PROF_TURMA (FK_CPF) VALUES (:cpf)",
            nativeQuery = true)
    void saveCpf(@Param("cpf") String cpf);

}
