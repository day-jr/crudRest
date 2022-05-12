package br.com.escola.client.repository;

import br.com.escola.client.entity.ProfTurma;
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

}


